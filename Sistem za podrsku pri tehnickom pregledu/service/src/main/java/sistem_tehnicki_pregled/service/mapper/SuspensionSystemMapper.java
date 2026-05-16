package sistem_tehnicki_pregled.service.mapper;

import org.mapstruct.Mapper;
import sistem_tehnicki_pregled.model.facts.SuspensionSystemFact;
import sistem_tehnicki_pregled.service.dto.facts.SuspensionSystemDTO;

@Mapper(config = InspectionMapperConfig.class)
public interface SuspensionSystemMapper {
    SuspensionSystemFact toSuspensionSystem(SuspensionSystemDTO dto);
}
