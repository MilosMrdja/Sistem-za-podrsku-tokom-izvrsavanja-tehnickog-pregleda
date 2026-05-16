package sistem_tehnicki_pregled.service.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import sistem_tehnicki_pregled.model.entities.Vehicle;
import sistem_tehnicki_pregled.model.facts.*;
import sistem_tehnicki_pregled.model.models.VehicleIdentification;
import sistem_tehnicki_pregled.model.models.InspectionConditions;
import sistem_tehnicki_pregled.service.dto.InspectionConditionsDTO;
import sistem_tehnicki_pregled.service.dto.VehicleIdentificationDTO;
import sistem_tehnicki_pregled.service.dto.VehicleDTO;
import sistem_tehnicki_pregled.service.dto.facts.*;

@Component
@RequiredArgsConstructor
public class InspectionMapperFacade {

    private final VehicleMapper vehicleMapper;
    private final VehicleIdentificationMapper vehicleIdentificationMapper;
    private final InspectionConditionsMapper inspectionConditionsMapper;
    private final BrakeFluidMapper brakeFluidMapper;
    private final ExhaustSystemMapper exhaustSystemMapper;
    private final WheelTireMapper wheelTireMapper;
    private final EngineSystemMapper engineSystemMapper;
    private final LightingSystemMapper lightingSystemMapper;
    private final ElectricalSystemMapper electricalSystemMapper;
    private final MandatoryEquipmentMapper mandatoryEquipmentMapper;
    private final SuspensionSystemMapper suspensionSystemMapper;

    public Vehicle toVehicle(VehicleDTO dto) {
        return vehicleMapper.toVehicle(dto);
    }

    public VehicleIdentification toVehicleIdentification(VehicleIdentificationDTO dto){
        return vehicleIdentificationMapper.toVehicleIdentification(dto);
    }

    public InspectionConditions toInspectionConditions(InspectionConditionsDTO dto) {
        return inspectionConditionsMapper.toInspectionConditions(dto);
    }

    public TireFact toWheelTire(WheelTireDTO dto){return wheelTireMapper.toWheelTire(dto);}
    public DriveSystemFact toEngineSystem(EngineSystemDTO dto){return engineSystemMapper.toEngineSystem(dto);}
    public BrakeFluidFact toBrakeFluid(BrakeFluidDTO dto) {
        return brakeFluidMapper.toBrakeFluid(dto);
    }
    public ExhaustSystemFact toExhaustSystem(ExhaustMeasurementDTO dto) {return exhaustSystemMapper.toExhaustFact(dto);}
    public SuspensionSystemFact toSuspensionSystem(SuspensionSystemDTO dto){return suspensionSystemMapper.toSuspensionSystem(dto);}
    public ElectricalInstallationFact toElectricalInstallation(ElectricalSystemDTO dto){return electricalSystemMapper.toElectricalInstallation(dto);}
    public LightingSystemFact toLightingSystem(LightingSystemDTO dto){return lightingSystemMapper.toLightingSystem(dto);}
    public MandatoryEquipmentFact toMandatoryEquipment(MandatoryEquipmentDTO dto){return mandatoryEquipmentMapper.toMandatoryEquipment(dto);}
}
