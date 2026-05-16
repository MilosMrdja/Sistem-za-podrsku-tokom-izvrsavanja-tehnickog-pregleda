package sistem_tehnicki_pregled.service.dto.facts;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import sistem_tehnicki_pregled.model.enums.LightBulbType;

@Data
public class LightingSystemDTO {

    @NotNull(message = "ID pregleda vozila je obavezan")
    private Long inspectionId;

    @NotNull(message = "Jačina kratkog svetla je obavezno polje")
    @Positive(message = "Jačina kratkog svetla mora biti veća od 0")
    private Double lowBeamIntensityM;

    @NotNull(message = "Jačina dugog svetla je obavezno polje")
    @Positive(message = "Jačina dugog svetla mora biti veća od 0")
    private Double highBeamIntensityM;

    @NotNull(message = "Jačina prednjeg svetla za maglu je obavezno polje")
    @Min(value = 0, message = "Jačina prednjeg svetla za maglu ne može biti negativna")
    private Double frontFogLightIntensityM;

    @NotNull(message = "Jačina zadnjeg svetla za maglu je obavezno polje")
    @Min(value = 0, message = "Jačina zadnjeg svetla za maglu ne može biti negativna")
    private Double rearFogLightIntensityM;

    @NotNull(message = "Jačina prednjeg pozicionog svetla je obavezno polje")
    @Positive(message = "Jačina prednjeg pozicionog svetla mora biti veća od 0")
    private Double frontPositionLightIntensityM;

    @NotNull(message = "Jačina zadnjeg pozicionog svetla je obavezno polje")
    @Positive(message = "Jačina zadnjeg pozicionog svetla mora biti veća od 0")
    private Double rearPositionLightIntensityM;

    @NotNull(message = "Jačina parkirnog svetla je obavezno polje")
    @Positive(message = "Jačina parkirnog svetla mora biti veća od 0")
    private Double parkingLightIntensityM;

    @NotNull(message = "Jačina stop svetla je obavezno polje")
    @Positive(message = "Jačina stop svetla mora biti veća od 0")
    private Double stopLightIntensityM;

    @NotNull(message = "Tip sijalice glavnog fara je obavezan")
    private LightBulbType headlightBulbType;

    @NotNull(message = "Usmerenost dugih svetala je obavezno polje")
    @Min(value = 0, message = "Usmerenost dugih svetala ne može biti negativna")
    private Double highBeamOrientationLx;

    @NotNull(message = "Usmerenost kratkih svetala je obavezno polje")
    @Min(value = 0, message = "Usmerenost kratkih svetala ne može biti negativna")
    private Double lowBeamOrientationLx;

    @NotNull(message = "Morate označiti da li svetla za vožnju unazad svetle belom bojom")
    private Boolean reversingLightsWhite;

    @NotNull(message = "Morate označiti da li svetli svetlo zadnje registarske tablice")
    private Boolean plateLightOperational;

    @NotNull(message = "Morate označiti da li vozilo poseduje treće stop svetlo")
    private Boolean thirdStopLightPresent;

    @NotNull(message = "Morate označiti da li su pokazivači pravca žute boje")
    private Boolean turnIndicatorsAmber;

    @NotNull(message = "Učestalost treperanja pokazivača pravca je obavezno polje")
    @Positive(message = "Učestalost treperanja mora biti broj veći od 0")
    private Integer turnIndicatorFrequencyPerMin;

    @NotNull(message = "Morate označiti da li rade uređaji za istovremeno uključivanje pokazivača")
    private Boolean hazardSwitchFunctional;

    @NotNull(message = "Morate označiti da li su svi pokazivači pravca funkcionalni")
    private Boolean turnIndicatorsFunctional;

    @NotNull(message = "Broj prednjih bočnih pokazivača pravca je obavezan")
    @Min(value = 0, message = "Broj pokazivača ne može biti negativan")
    private Integer frontSideTurnIndicatorCount;

    @NotNull(message = "Broj prednjih pokazivača pravca je obavezan")
    @Min(value = 0, message = "Broj pokazivača ne može biti negativan")
    private Integer frontTurnIndicatorCount;

    @NotNull(message = "Broj zadnjih pokazivača pravca je obavezan")
    @Min(value = 0, message = "Broj pokazivača ne može biti negativan")
    private Integer rearTurnIndicatorCount;

    @NotNull(message = "Broj bočnih pokazivača pravca je obavezan")
    @Min(value = 0, message = "Broj pokazivača ne može biti negativan")
    private Integer sideTurnIndicatorCount;
}
