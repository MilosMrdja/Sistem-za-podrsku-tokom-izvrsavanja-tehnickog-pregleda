package sistem_tehnicki_pregled.service.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import sistem_tehnicki_pregled.service.dto.facts.WheelTireDTO;

import java.util.List;

@Data
public class WheelsInspectionRequestDto {
    @NotNull(message = "ID pregleda vozila je obavezan")
    private Long inspectionId;

    @NotNull(message = "Informacije o točkovima su obavezne")
    private List<WheelTireDTO> wheels;

}
