package sistem_tehnicki_pregled.service.mapper;

import org.mapstruct.Mapper;
import sistem_tehnicki_pregled.model.facts.LightingSystemFact;
import sistem_tehnicki_pregled.service.dto.facts.LightingSystemDTO;

@Mapper(config = InspectionMapperConfig.class)
public interface LightingSystemMapper {
    LightingSystemFact toLightingSystem(LightingSystemDTO dto);
}
