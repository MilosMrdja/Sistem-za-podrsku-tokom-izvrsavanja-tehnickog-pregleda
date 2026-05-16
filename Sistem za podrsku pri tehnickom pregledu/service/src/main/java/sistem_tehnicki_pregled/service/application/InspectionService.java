package sistem_tehnicki_pregled.service.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Service;
import sistem_tehnicki_pregled.model.entities.Inspection;
import sistem_tehnicki_pregled.model.entities.Vehicle;
import sistem_tehnicki_pregled.model.enums.InspectionResult;
import sistem_tehnicki_pregled.model.facts.*;
import sistem_tehnicki_pregled.model.models.FinalDecision;
import sistem_tehnicki_pregled.model.models.InspectionConditions;
import sistem_tehnicki_pregled.model.models.SystemStatus;
import sistem_tehnicki_pregled.model.models.VehicleIdentification;
import sistem_tehnicki_pregled.service.dto.InspectionConditionsDTO;
import sistem_tehnicki_pregled.service.dto.InspectionResponseDTO;
import sistem_tehnicki_pregled.service.dto.VehicleDTO;
import sistem_tehnicki_pregled.service.dto.VehicleIdentificationDTO;
import sistem_tehnicki_pregled.service.dto.facts.*;
import sistem_tehnicki_pregled.service.exceptions.BadRequestError;
import sistem_tehnicki_pregled.service.factories.DroolsSessionFactory;
import sistem_tehnicki_pregled.service.factories.InspectionResponseFactory;
import sistem_tehnicki_pregled.service.mapper.InspectionMapperFacade;
import sistem_tehnicki_pregled.service.repositories.InspectionRepository;
import sistem_tehnicki_pregled.service.repositories.VehicleRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.function.Consumer;

@Slf4j
@Service
@RequiredArgsConstructor
public class InspectionService {

    private final DroolsSessionFactory sessionFactory;
    private final InspectionMapperFacade mapper;
    private final InspectionResponseFactory responseFactory;
    private final VehicleRepository vehicleRepository;
    private final InspectionRepository inspectionRepository;

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

        FinalDecision decision = executeDroolsRules(InspectionResult.PRECONDITIONS_PASSED, session -> {
            session.insert(conditions);
        });

        inspection.setResult(decision.getResult());
        if (decision.isResolved() && decision.getResult() == InspectionResult.NIJE_ZAPOCET) {
            inspection.setResolved(true);
            inspection.setFinishedAt(LocalDateTime.now());
        }
        inspectionRepository.save(inspection);

        decision.setDecidedAt(LocalDateTime.now());
        return responseFactory.build(inspection.getVehicle(), decision);
    }

    public InspectionResponseDTO vehicleIdentification(VehicleIdentificationDTO request) {
        Inspection inspection = getActiveInspection(request.getInspectionId(), InspectionResult.PRECONDITIONS_PASSED,
                "Uslovi potrebni za tehnički pregled nisu ispunjeni");

        VehicleIdentification vehicleIdentification = mapper.toVehicleIdentification(request);

        FinalDecision decision = executeDroolsRules(InspectionResult.IDENTIFICATION_PASSED, session -> {
            session.insert(vehicleIdentification);
        });

        inspection.setResult(decision.getResult());
        if (decision.isResolved() && decision.getResult() == InspectionResult.PREKINUT) {
            inspection.setResolved(true);
            inspection.setFinishedAt(LocalDateTime.now());
        }
        inspectionRepository.save(inspection);

        decision.setDecidedAt(LocalDateTime.now());
        return responseFactory.build(inspection.getVehicle(), decision);
    }

    public InspectionResponseDTO checkWheelTire(WheelTireDTO request) {
        Inspection inspection = getActiveInspection(request.getInspectionId(), InspectionResult.IDENTIFICATION_PASSED,
                "Uslovi potrebni za proveru točkova i guma nisu ispunjeni, vozilo mora biti uspešno identifikovano");

        Vehicle vehicle = inspection.getVehicle();
        TireFact wheelTire = mapper.toWheelTire(request);
        SystemStatus wheelTireSystem = new SystemStatus(SystemStatus.WHEELS_TYRES, false, new ArrayList<>());


        FinalDecision decision = executeDroolsRules(InspectionResult.WHEELS_TYRES_PASSED, session -> {
            session.insert(vehicle);
            session.insert(wheelTire);
            session.insert(wheelTireSystem);
        });
        updateInspectionState(inspection, decision);

        return responseFactory.build(vehicle, decision, wheelTireSystem);
    }

    public InspectionResponseDTO checkEngineSystem(EngineSystemDTO request) {
        Inspection inspection = getActiveInspection(request.getInspectionId(), InspectionResult.WHEELS_TYRES_PASSED,
                "Uslovi potrebni za proveru sistema mototra nisu ispunjeni, gume i točkovi se mroaju proveriti");

        Vehicle vehicle = inspection.getVehicle();
        DriveSystemFact engine = mapper.toEngineSystem(request);
        SystemStatus engineSystem = new SystemStatus(SystemStatus.ENGINE, false, new ArrayList<>());

        FinalDecision decision = executeDroolsRules(InspectionResult.ENGINE_SYSTEM_PASSED, session -> {
            session.insert(vehicle);
            session.insert(engine);
            session.insert(engineSystem);
        });
        updateInspectionState(inspection, decision);

        return responseFactory.build(vehicle, decision, engineSystem);
    }

    public InspectionResponseDTO checkBrakeFluid(BrakeFluidDTO request) {
        Inspection inspection = getActiveInspection(request.getInspectionId(), InspectionResult.ENGINE_SYSTEM_PASSED,
                "Uslovi potrebni za proveru kočione tečnosti nisu ispunjeni, sistem motora se mora proveriti");

        Vehicle vehicle = inspection.getVehicle();
        BrakeFluidFact brakeFluidFact = mapper.toBrakeFluid(request);
        SystemStatus brakeSystem = new SystemStatus(SystemStatus.BRAKE_SYSTEM, false, new ArrayList<>());


        FinalDecision decision = executeDroolsRules(InspectionResult.BRAKE_FLUID_PASSED, session -> {
            session.insert(brakeFluidFact);
            session.insert(brakeSystem);
        });
        updateInspectionState(inspection, decision);

        return responseFactory.build(vehicle, decision, brakeSystem);
    }

    public InspectionResponseDTO checkExhaustSystem(ExhaustMeasurementDTO request) {
        Inspection inspection = getActiveInspection(request.getInspectionId(), InspectionResult.BRAKE_FLUID_PASSED,
                "Uslovi potrebni za proveru izduvnih gasova nisu ispunjeni, kočiona tečnost se mora proveriti");

        Vehicle vehicle = inspection.getVehicle();
        ExhaustSystemFact exhaustSystemFact = mapper.toExhaustSystem(request);
        SystemStatus brakeSystem = new SystemStatus(SystemStatus.EXHAUST_SYSTEM, false, new ArrayList<>());


        FinalDecision decision = executeDroolsRules(InspectionResult.EXHAUST_SYSTEM_PASSED, session -> {
            session.insert(vehicle);
            session.insert(exhaustSystemFact);
            session.insert(brakeSystem);
        });
        updateInspectionState(inspection, decision);

        return responseFactory.build(vehicle, decision, brakeSystem);
    }

    public InspectionResponseDTO checkSuspensionSystem(SuspensionSystemDTO request) {
        Inspection inspection = getActiveInspection(request.getInspectionId(), InspectionResult.EXHAUST_SYSTEM_PASSED,
                "Uslovi potrebni za proveru mehaničkih delova nisu ispunjeni, izduvni gasovi se moraju proveriti");

        Vehicle vehicle = inspection.getVehicle();
        SuspensionSystemFact suspensionSystemFact = mapper.toSuspensionSystem(request);
        SystemStatus suspensionSystem = new SystemStatus(SystemStatus.CHASSIS_SUSPENSION, false, new ArrayList<>());


        FinalDecision decision = executeDroolsRules(InspectionResult.CHASSIS_SUSPENSION_PASSED, session -> {
            session.insert(vehicle);
            session.insert(suspensionSystemFact);
            session.insert(suspensionSystem);
        });
        updateInspectionState(inspection, decision);

        return responseFactory.build(vehicle, decision, suspensionSystem);
    }

    public InspectionResponseDTO checkElectricalSystem(ElectricalSystemDTO request) {
        Inspection inspection = getActiveInspection(request.getInspectionId(), InspectionResult.CHASSIS_SUSPENSION_PASSED,
                "Uslovi potrebni za proveru elektroinstalacije nisu ispunjeni, mehanički delovi se moraju proveriti");

        Vehicle vehicle = inspection.getVehicle();
        ElectricalInstallationFact electricalInstallationFact = mapper.toElectricalInstallation(request);
        SystemStatus electricalSystem = new SystemStatus(SystemStatus.ELECTRICAL_SYSTEM, false, new ArrayList<>());


        FinalDecision decision = executeDroolsRules(InspectionResult.ELECTRICAL_SYSTEM_PASSED, session -> {
            session.insert(vehicle);
            session.insert(electricalInstallationFact);
            session.insert(electricalSystem);
        });
        updateInspectionState(inspection, decision);

        return responseFactory.build(vehicle, decision, electricalSystem);
    }
    public InspectionResponseDTO checkLightingSystem(LightingSystemDTO request) {
        Inspection inspection = getActiveInspection(request.getInspectionId(), InspectionResult.ELECTRICAL_SYSTEM_PASSED,
                "Uslovi potrebni za proveru svetala nisu ispunjeni, elektroinstalacija se mora proveriti");

        Vehicle vehicle = inspection.getVehicle();
        LightingSystemFact lightingSystemFact = mapper.toLightingSystem(request);
        SystemStatus lightingSystem = new SystemStatus(SystemStatus.LIGHTING, false, new ArrayList<>());


        FinalDecision decision = executeDroolsRules(InspectionResult.LIGHTING_SYSTEM_PASSED, session -> {
            session.insert(vehicle);
            session.insert(lightingSystemFact);
            session.insert(lightingSystem);
        });
        updateInspectionState(inspection, decision);

        return responseFactory.build(vehicle, decision, lightingSystem);
    }

    public InspectionResponseDTO checkMandatoryEquipment(MandatoryEquipmentDTO request) {
        Inspection inspection = getActiveInspection(request.getInspectionId(), InspectionResult.LIGHTING_SYSTEM_PASSED,
                "Uslovi potrebni za proveru dodatne opreme nisu ispunjeni, svetla se moraju proveriti");

        Vehicle vehicle = inspection.getVehicle();
        MandatoryEquipmentFact mandatoryEquipmentFact = mapper.toMandatoryEquipment(request);
        SystemStatus mandatoryEquipment = new SystemStatus(SystemStatus.MANDATORY_EQUIPMENT, false, new ArrayList<>());


        FinalDecision decision = executeDroolsRules(InspectionResult.MANDATORY_EQUIPMENT_PASSED, session -> {
            session.insert(vehicle);
            session.insert(mandatoryEquipmentFact);
            session.insert(mandatoryEquipment);
        });
        updateInspectionState(inspection, decision);

        return responseFactory.build(vehicle, decision, mandatoryEquipment);
    }





    private FinalDecision executeDroolsRules(InspectionResult initialResult, Consumer<KieSession> factsInserter) {
        FinalDecision decision = FinalDecision.builder()
                .result(initialResult)
                .resolved(false)
                .build();

        KieSession session = sessionFactory.createSession();
        try {
           factsInserter.accept(session);

            session.insert(decision);

            int fired = session.fireAllRules();
            log.info("Rules fired: {}", fired);
        } finally {
            session.dispose();
        }

        return decision;
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

    private void updateInspectionState(Inspection inspection, FinalDecision decision) {
        if (decision.getResult() == InspectionResult.NIJE_PROSAO) {
            inspection.setResult(InspectionResult.NIJE_PROSAO);
            inspection.setResolved(true);
            inspection.setFinishedAt(LocalDateTime.now());
        } else {
            inspection.setResult(decision.getResult());
        }
        inspectionRepository.save(inspection);
        decision.setDecidedAt(LocalDateTime.now());
    }


}
