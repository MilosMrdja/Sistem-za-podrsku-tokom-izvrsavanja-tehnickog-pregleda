package sistem_tehnicki_pregled.service.dto.facts;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EngineSystemDTO {
    @NotNull(message = "ID pregleda vozila je obavezan")
    private Long inspectionId;

    @NotNull(message = "Morate označiti da li su nosači motora ispravni")
    private Boolean engineMountsIntact;

    @NotNull(message = "Morate označiti da li postoji curenje ulja ili drugih tečnosti iz motora")
    private Boolean noEngineFluidLeak;

    @NotNull(message = "Morate označiti da li sistem za paljenje ima mehanička oštećenja")
    private Boolean ignitionSystemIntact;

    @NotNull(message = "Morate označiti da li je sistem za dovod goriva pravilno pričvršćen")
    private Boolean fuelSupplySecured;

    @NotNull(message = "Morate označiti da li sistem za dovod goriva curi")
    private Boolean noFuelLeak;

    @NotNull(message = "Morate označiti da li se menjačem može bezbedno upravljati jednom rukom")
    private Boolean transmissionSafeForSingleHandOperation;
}
