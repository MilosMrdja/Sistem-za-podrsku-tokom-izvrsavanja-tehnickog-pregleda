package sistem_tehnicki_pregled.service.dto.facts;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ElectricalSystemDTO {
    @NotNull(message = "ID pregleda vozila je obavezan")
    private Long inspectionId;

    @NotNull(message = "Morate označiti da li su vodovi zaštićeni od mehaničkog habanja")
    private Boolean wiringProtected;

    @NotNull(message = "Morate označiti da li je akumulator dobro pričvršćen")
    private Boolean batterySecured;
}
