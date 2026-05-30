package sistem_tehnicki_pregled.service.application;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import sistem_tehnicki_pregled.model.entities.Inspection;
import sistem_tehnicki_pregled.model.entities.Vehicle;
import sistem_tehnicki_pregled.model.enums.InspectionResult;
import sistem_tehnicki_pregled.model.models.FinalDecision;
import sistem_tehnicki_pregled.model.models.InspectionConditions;
import sistem_tehnicki_pregled.model.models.SystemStatus;
import sistem_tehnicki_pregled.model.models.VehicleIdentification;
import sistem_tehnicki_pregled.service.dto.*;
import sistem_tehnicki_pregled.service.dto.facts.*;
import sistem_tehnicki_pregled.service.exceptions.BadRequestError;
import sistem_tehnicki_pregled.service.factories.InspectionResponseFactory;
import sistem_tehnicki_pregled.service.mapper.InspectionMapperFacade;
import sistem_tehnicki_pregled.service.mapper.WheelTireMapper;
import sistem_tehnicki_pregled.service.repositories.InspectionRepository;
import sistem_tehnicki_pregled.service.repositories.VehicleRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InspectionService {
    private final WheelTireMapper wheelTireMapper;

    private final InspectionDroolsExecutor droolsExecutor;
    private final InspectionMapperFacade mapper;
    private final InspectionResponseFactory responseFactory;
    private final VehicleRepository vehicleRepository;
    private final InspectionRepository inspectionRepository;
    private final InspectionStateService inspectionStateService;
    private final KafkaTemplate<String, Integer> kafkaTemplate;

    public InspectionResponseDTO createInspection(VehicleDTO vehicleInfoRequest) {
        if (vehicleRepository.existsByVin(vehicleInfoRequest.getVin())) {
            throw new BadRequestError("Vozilo sa tim VIN brojem već postoji u bazi.");
        }
        if (vehicleRepository.existsByRegistrationPlate(vehicleInfoRequest.getRegistrationPlate())) {
            throw new BadRequestError("Registarska oznaka je već zauzeta.");
        }

        Vehicle vehicleInfo = vehicleRepository.save(mapper.toVehicle(vehicleInfoRequest));

        Inspection inspection = Inspection.builder()
                .vehicle(vehicleInfo)
                .result(InspectionResult.CREATED)
                .startedAt(LocalDateTime.now())
                .resolved(false)
                .build();

        inspection = inspectionRepository.save(inspection);

        return responseFactory.build(vehicleInfo, InspectionResult.CREATED, vehicleInfo.getCreatedAt(), inspection.getId());
    }

    public InspectionResponseDTO checkInspectionCondition(InspectionConditionsDTO request) {
        Inspection inspection = getActiveInspection(request.getInspectionId(), InspectionResult.CREATED,
                "Podaci potrebni za tehnički pregled nisu uneti");

        InspectionConditions conditions = mapper.toInspectionConditions(request);

        FinalDecision decision = droolsExecutor.executeRules(InspectionResult.PRECONDITIONS_PASSED, conditions);
        inspectionStateService.applyDecision(inspection, decision);
        return responseFactory.build(inspection.getVehicle(), decision);
    }

    public InspectionResponseDTO vehicleIdentification(VehicleIdentificationDTO request) {
        Inspection inspection = getActiveInspection(request.getInspectionId(), InspectionResult.PRECONDITIONS_PASSED,
                "Uslovi potrebni za tehnički pregled nisu ispunjeni");

        VehicleIdentification vehicleIdentification = mapper.toVehicleIdentification(request);

        FinalDecision decision = droolsExecutor.executeRules(InspectionResult.IDENTIFICATION_PASSED, vehicleIdentification);
        inspectionStateService.applyDecision(inspection, decision);
        return responseFactory.build(inspection.getVehicle(), decision);
    }

    public InspectionResponseDTO checkWheelTire(WheelsInspectionRequestDto request) {
        Inspection inspection = getActiveInspection(request.getInspectionId(), InspectionResult.IDENTIFICATION_PASSED,
                "Uslovi potrebni za proveru točkova i guma nisu ispunjeni, vozilo mora biti uspešno identifikovano");
        if (request.getWheels().size() != 4) {
            throw new BadRequestError(
                    "Vozilo mora imati tačno 4 točka."
            );
        }
        SystemStatus wheelTireSystem = new SystemStatus(SystemStatus.WHEELS_TYRES, false, new ArrayList<>());
        List<Object> wheelFacts = request.getWheels().stream()
                .map(wheelTireMapper::toWheelTire)
                .map(Object.class::cast)
                .toList();

        return droolsExecutor.executeSystemCheck(
                inspection,
                InspectionResult.WHEELS_TYRES_PASSED,
                wheelTireSystem,
                wheelFacts.toArray()
        );
    }

    public InspectionResponseDTO checkEngineSystem(EngineSystemDTO request) {
        Inspection inspection = getActiveInspection(request.getInspectionId(), InspectionResult.WHEELS_TYRES_PASSED,
                "Uslovi potrebni za proveru sistema mototra nisu ispunjeni, gume i točkovi se mroaju proveriti");

        return droolsExecutor.executeVehicleSystemCheck(
                inspection,
                InspectionResult.ENGINE_SYSTEM_PASSED,
                SystemStatus.ENGINE,
                mapper.toEngineSystem(request)
        );
    }

    public InspectionResponseDTO checkBrakeFluid(BrakeFluidDTO request) {
        Inspection inspection = getActiveInspection(request.getInspectionId(), InspectionResult.ENGINE_SYSTEM_PASSED,
                "Uslovi potrebni za proveru kočione tečnosti nisu ispunjeni, sistem motora se mora proveriti");

        return droolsExecutor.executeSystemCheck(
                inspection,
                InspectionResult.BRAKE_FLUID_PASSED,
                SystemStatus.BRAKE_SYSTEM,
                mapper.toBrakeFluid(request)
        );
    }

    public InspectionResponseDTO checkExhaustSystem(ExhaustMeasurementDTO request) {
        Inspection inspection = getActiveInspection(request.getInspectionId(), InspectionResult.BRAKE_FLUID_PASSED,
                "Uslovi potrebni za proveru izduvnih gasova nisu ispunjeni, kočiona tečnost se mora proveriti");

        return droolsExecutor.executeVehicleSystemCheck(
                inspection,
                InspectionResult.EXHAUST_SYSTEM_PASSED,
                SystemStatus.EXHAUST_SYSTEM,
                mapper.toExhaustSystem(request)
        );
    }

    public InspectionResponseDTO checkSuspensionSystem(SuspensionSystemDTO request) {
        Inspection inspection = getActiveInspection(request.getInspectionId(), InspectionResult.EXHAUST_SYSTEM_PASSED,
                "Uslovi potrebni za proveru mehaničkih delova nisu ispunjeni, izduvni gasovi se moraju proveriti");

        return droolsExecutor.executeVehicleSystemCheck(
                inspection,
                InspectionResult.CHASSIS_SUSPENSION_PASSED,
                SystemStatus.CHASSIS_SUSPENSION,
                mapper.toSuspensionSystem(request)
        );
    }

    public InspectionResponseDTO checkElectricalSystem(ElectricalSystemDTO request) {
        Inspection inspection = getActiveInspection(request.getInspectionId(), InspectionResult.CHASSIS_SUSPENSION_PASSED,
                "Uslovi potrebni za proveru elektroinstalacije nisu ispunjeni, mehanički delovi se moraju proveriti");

        return droolsExecutor.executeVehicleSystemCheck(
                inspection,
                InspectionResult.ELECTRICAL_SYSTEM_PASSED,
                SystemStatus.ELECTRICAL_SYSTEM,
                mapper.toElectricalInstallation(request)
        );
    }

    public InspectionResponseDTO startBrakeTest(Long inspectionId) {

        Inspection inspection = getActiveInspection(
                inspectionId,
                InspectionResult.ELECTRICAL_SYSTEM_PASSED,
                "Uslovi za proveru kočnica nisu ispunjeni, elektroinstalacija se mora proveriti"
        );

        inspection.setResult(InspectionResult.BRAKE_TEST_RUNNING);
        inspectionRepository.save(inspection);

        kafkaTemplate.send("start-brake-test", inspectionId.intValue());

        return responseFactory.build(
                inspection.getVehicle(),
                InspectionResult.BRAKE_TEST_RUNNING,
                LocalDateTime.now(),
                inspectionId
        );
    }

//    @KafkaListener(topics = "brake-test-finished")
//    public void finish(Long inspectionId) {
//
//        Inspection ins = inspectionRepository.findById(inspectionId).orElseThrow();
//
//        if(ins.getResult() != InspectionResult.NIJE_PROSAO){
//            ins.setResult(InspectionResult.BRAKE_TEST_PASSED);
//        }
//
//        inspectionRepository.save(ins);
//    }

    public InspectionResponseDTO getBrakeTestStatus(Long inspectionId) {
        Inspection inspection = inspectionRepository.findById(inspectionId)
                .orElseThrow(() -> new BadRequestError("Pregled nije pronađen"));



        return responseFactory.build(
                inspection.getVehicle(),
                inspection.getResult(),
                LocalDateTime.now(),
                inspectionId
        );
    }


    public InspectionResponseDTO checkLightingSystem(LightingSystemDTO request) {
        Inspection inspection = getActiveInspection(request.getInspectionId(), InspectionResult.BRAKE_TEST_PASSED,
                "Uslovi potrebni za proveru svetala nisu ispunjeni, kočnice se moraju proveriti");

        return droolsExecutor.executeVehicleSystemCheck(
                inspection,
                InspectionResult.LIGHTING_SYSTEM_PASSED,
                SystemStatus.LIGHTING,
                mapper.toLightingSystem(request)
        );
    }

    public InspectionResponseDTO checkMandatoryEquipment(MandatoryEquipmentDTO request) {
        Inspection inspection = getActiveInspection(request.getInspectionId(), InspectionResult.LIGHTING_SYSTEM_PASSED,
                "Uslovi potrebni za proveru dodatne opreme nisu ispunjeni, svetla se moraju proveriti");

        return droolsExecutor.executeVehicleSystemCheck(
                inspection,
                InspectionResult.MANDATORY_EQUIPMENT_PASSED,
                SystemStatus.MANDATORY_EQUIPMENT,
                mapper.toMandatoryEquipment(request)
        );
    }

    private Inspection getActiveInspection(Long inspectionId, InspectionResult expectedStatus, String statusErrorMessage) {
        Inspection inspection = inspectionRepository.findById(inspectionId)
                .orElseThrow(() -> new BadRequestError("Ne postoji tehnički pregled sa zadatim ID-em."));

        if (inspection.isResolved()) {
            throw new BadRequestError("Tehnički pregled je već procesuiran");
        }
        if (inspection.getResult() != expectedStatus) {
            throw new BadRequestError(statusErrorMessage);
        }
        return inspection;
    }

}
