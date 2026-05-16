package sistem_tehnicki_pregled.service.mapper;

import org.mapstruct.Mapper;
import sistem_tehnicki_pregled.model.facts.TireFact;
import sistem_tehnicki_pregled.service.dto.facts.WheelTireDTO;

@Mapper(config = InspectionMapperConfig.class)
public interface WheelTireMapper {
    TireFact toWheelTire(WheelTireDTO dto);
}
