package sistem_tehnicki_pregled.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InspectionConditionsDTO {
    @NotNull(message = "ID pregleda vozila je obavezan")
    private Long inspectionId;

    @NotNull(message = "Morate označiti da li je vozilo čisto")
    private Boolean vehicleClean;

    @NotNull(message = "Morate označiti da li je vozilo u voznom stanju")
    private Boolean vehicleOperational;

    @NotNull(message = "Morate označiti da li je vozilo preopterećeno")
    private Boolean notOverloaded;

    @NotNull(message = "Morate označiti da li je vozilo registrovano u Republici Srbiji")
    private Boolean registeredInSerbia;

    @NotNull(message = "Morate označiti da li je registracija vozila aktivna")
    private Boolean registrationActive;

    @NotNull(message = "Morate označiti da li su prisutne obe registarske tablice")
    private Boolean bothPlatesPresent;

    @NotNull(message = "Morate označiti da li na vozilu postoje proboji od korozije")
    private Boolean noCorrosionHoles;
}
