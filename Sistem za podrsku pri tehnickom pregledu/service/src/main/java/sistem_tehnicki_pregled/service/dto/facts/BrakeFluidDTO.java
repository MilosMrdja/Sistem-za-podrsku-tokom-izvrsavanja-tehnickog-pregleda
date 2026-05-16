package sistem_tehnicki_pregled.service.dto.facts;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

// ── Brake fluid ───────────────────────────────────────────────────────────────

@Data
public class BrakeFluidDTO {

    @NotNull(message = "ID pregleda vozila je obavezan")
    private Long inspectionId;

    @NotNull(message = "Procenat vlage u kočionoj tečnosti je obavezno polje")
    @Positive(message = "Procenat vlage u kočionoj tečnosti mora biti veća od 0")
    private Double moisturePercent;

    @NotNull(message = "Temperatura ključanja kočione tečnosti je obavezno polje")
    @Positive(message = "Temperatura ključanja kočione tečnosti mora biti veća od 0")
    private Double boilingPointCelsius;

    @NotNull(message = "Morate označiti da li postoji curenje na kočionom sistemu")
    private boolean systemLeaking;
}
