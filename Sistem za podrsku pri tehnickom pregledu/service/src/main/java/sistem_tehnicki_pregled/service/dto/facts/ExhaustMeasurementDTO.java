package sistem_tehnicki_pregled.service.dto.facts;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ExhaustMeasurementDTO {

    @NotNull(message = "ID tehničkog pregleda je obavezan.")
    private Long inspectionId;

    // Za dizel motore
    private Double dieselAbsorptionCoefficient;

    // Za OTO (benzinske) motore
    private Double petrolCoPercent;
}
