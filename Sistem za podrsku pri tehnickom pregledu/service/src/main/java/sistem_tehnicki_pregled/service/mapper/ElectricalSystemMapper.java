package sistem_tehnicki_pregled.service.mapper;

import org.mapstruct.Mapper;
import sistem_tehnicki_pregled.model.facts.ElectricalInstallationFact;
import sistem_tehnicki_pregled.service.dto.facts.ElectricalSystemDTO;

@Mapper(config = InspectionMapperConfig.class)
public interface ElectricalSystemMapper {
    ElectricalInstallationFact toElectricalInstallation(ElectricalSystemDTO dto);
}
