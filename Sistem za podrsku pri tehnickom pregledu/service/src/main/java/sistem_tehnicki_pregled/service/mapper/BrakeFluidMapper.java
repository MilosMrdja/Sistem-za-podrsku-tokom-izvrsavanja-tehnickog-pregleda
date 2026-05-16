package sistem_tehnicki_pregled.service.mapper;

import org.mapstruct.Mapper;
import sistem_tehnicki_pregled.model.facts.BrakeFluidFact;
import sistem_tehnicki_pregled.service.dto.facts.BrakeFluidDTO;

@Mapper(config = InspectionMapperConfig.class)
public interface BrakeFluidMapper {

    BrakeFluidFact toBrakeFluid(BrakeFluidDTO dto);
}
