package sistem_tehnicki_pregled.service.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Results of cross-checking vehicle data against the ABS/MUP database.
 * Populated by the service layer before rules evaluation.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleIdentificationDTO {
    @NotNull(message = "ID pregleda vozila je obavezan")
    private Long inspectionId;

    @NotNull(message = "Morate označiti da li je VIN broj čitljiv")
    private Boolean vinReadable;

    @NotNull(message = "Morate označiti da li se VIN broj poklapa sa dokumentima")
    private Boolean vinMatchesDocument;

    @NotNull(message = "Morate označiti da li se kod motora poklapa sa bazom podataka")
    private Boolean engineCodeMatchesDatabase;

    @NotNull(message = "Morate označiti da li se marka vozila poklapa sa bazom podataka")
    private Boolean brandMatchesDatabase;

    @NotNull(message = "Morate označiti da li se godina proizvodnje poklapa sa bazom podataka")
    private Boolean productionYearMatchesDatabase;

    @NotNull(message = "Morate označiti da li vozilo već postoji u evidenciji/bazi podataka")
    private Boolean existsInDatabase;
}
