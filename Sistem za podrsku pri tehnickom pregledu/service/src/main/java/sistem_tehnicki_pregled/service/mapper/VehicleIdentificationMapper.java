package sistem_tehnicki_pregled.service.mapper;

import org.mapstruct.Mapper;
import sistem_tehnicki_pregled.model.models.VehicleIdentification;
import sistem_tehnicki_pregled.service.dto.VehicleIdentificationDTO;

@Mapper(config = InspectionMapperConfig.class)
public interface VehicleIdentificationMapper {

    VehicleIdentification toVehicleIdentification(VehicleIdentificationDTO dto);
}
