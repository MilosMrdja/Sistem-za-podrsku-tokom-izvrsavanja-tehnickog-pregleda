package sistem_tehnicki_pregled.service.dto.facts;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import sistem_tehnicki_pregled.model.enums.AxleId;
import sistem_tehnicki_pregled.model.enums.TireConstructionType;
import sistem_tehnicki_pregled.model.enums.WheelId;

@Data
public class WheelTireDTO {


    @NotNull(message = "Identifikator pozicije točka je obavezan")
    private WheelId wheelId;

    @NotNull(message = "Identifikator osovine je obavezan")
    private AxleId axleId;

    @NotNull(message = "Širina gazećeg sloja (mm) je obavezno polje")
    @Positive(message = "Širina mora biti veća od 0")
    private Integer widthMm;

    @NotNull(message = "Odnos širine i visine (%) je obavezno polje")
    @Positive(message = "Odnos širine i visine mora biti veći od 0")
    private Integer aspectRatioPercent;

    @NotNull(message = "Prečnik felne u colima je obavezno polje")
    @Positive(message = "Prečnik u colima mora biti veći od 0")
    private Integer rimDiameterInches;

    @NotNull(message = "Indeks nosivosti pneumatika je obavezno polje")
    @Positive(message = "Indeks nosivosti mora biti veći od 0")
    private Integer loadIndex;

    @NotNull(message = "Vrsta i konstrukcija pneumatika je obavezno polje")
    private TireConstructionType constructionType;

    @NotBlank(message = "Indeks brzine pneumatika je obavezno polje")
    private String speedRating;

    @NotNull(message = "Morate označiti da li su pneumatici homologovani")
    private Boolean homologated;

    @NotNull(message = "Morate označiti da li točkovi imaju oštećenja ili zazore")
    private Boolean noPlayOrDamage;
}
