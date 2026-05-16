package sistem_tehnicki_pregled.service.mapper;

import org.mapstruct.Mapper;
import sistem_tehnicki_pregled.model.entities.Vehicle;
import sistem_tehnicki_pregled.service.dto.VehicleDTO;

@Mapper(config = InspectionMapperConfig.class)
public interface VehicleMapper {

    Vehicle toVehicle(VehicleDTO dto);
}
