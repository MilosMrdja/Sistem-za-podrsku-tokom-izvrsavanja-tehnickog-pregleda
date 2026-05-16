package sistem_tehnicki_pregled.service.mapper;

import org.mapstruct.Mapper;
import sistem_tehnicki_pregled.model.facts.MandatoryEquipmentFact;
import sistem_tehnicki_pregled.service.dto.facts.MandatoryEquipmentDTO;

@Mapper(config = InspectionMapperConfig.class)
public interface MandatoryEquipmentMapper {
    MandatoryEquipmentFact toMandatoryEquipment(MandatoryEquipmentDTO dto);
}
