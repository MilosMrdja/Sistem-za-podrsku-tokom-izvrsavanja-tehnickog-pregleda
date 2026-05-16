package sistem_tehnicki_pregled.service.mapper;

import org.mapstruct.Mapper;
import sistem_tehnicki_pregled.model.facts.ExhaustSystemFact;
import sistem_tehnicki_pregled.service.dto.facts.ExhaustMeasurementDTO;

@Mapper(config = InspectionMapperConfig.class)
public interface ExhaustSystemMapper {
    ExhaustSystemFact toExhaustFact(ExhaustMeasurementDTO dto);
}
