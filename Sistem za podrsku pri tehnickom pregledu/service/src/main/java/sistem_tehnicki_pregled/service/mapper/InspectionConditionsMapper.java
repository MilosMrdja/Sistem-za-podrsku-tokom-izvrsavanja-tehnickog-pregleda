package sistem_tehnicki_pregled.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import sistem_tehnicki_pregled.model.models.InspectionConditions;
import sistem_tehnicki_pregled.service.dto.InspectionConditionsDTO;

@Mapper(config = InspectionMapperConfig.class)
public interface InspectionConditionsMapper {

    InspectionConditions toInspectionConditions(InspectionConditionsDTO dto);
}
