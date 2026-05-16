package sistem_tehnicki_pregled.service.dto.facts;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SuspensionSystemDTO {

    @NotNull(message = "ID pregleda vozila je obavezan")
    private Long inspectionId;

    @NotNull(message = "Morate označiti status ispravnosti poluga")
    private Boolean armsIntact;

    @NotNull(message = "Morate označiti status ispravnosti viljuški")
    private Boolean forksIntact;

    @NotNull(message = "Morate označiti status ispravnosti stabilizatora")
    private Boolean stabilizersIntact;

    @NotNull(message = "Morate označiti status ispravnosti zglobova")
    private Boolean jointsIntact;

    @NotNull(message = "Morate označiti status ispravnosti amortizera")
    private Boolean shockAbsorbersIntact;

    @NotNull(message = "Morate označiti status ispravnosti opruga")
    private Boolean springsIntact;
}