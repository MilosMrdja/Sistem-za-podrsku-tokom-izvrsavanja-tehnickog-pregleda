package sistem_tehnicki_pregled.service.mapper;

import org.mapstruct.Mapper;
import sistem_tehnicki_pregled.model.facts.DriveSystemFact;
import sistem_tehnicki_pregled.service.dto.facts.EngineSystemDTO;

@Mapper(config = InspectionMapperConfig.class)
public interface EngineSystemMapper {
    DriveSystemFact toEngineSystem(EngineSystemDTO dto);
}
