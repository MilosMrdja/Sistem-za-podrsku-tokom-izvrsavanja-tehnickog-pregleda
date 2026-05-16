package sistem_tehnicki_pregled.model.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleIdentification {
    // ── DB cross-check results (resolved by service before Drools) ────────────
    /** True if VIN on vehicle is readable. */
    private boolean vinReadable;

    /** True if VIN on vehicle matches the registration document. */
    private boolean vinMatchesDocument;

    /** True if engine code matches the ABS/MUP database record. */
    private boolean engineCodeMatchesDatabase;

    /** True if brand matches the ABS/MUP database record. */
    private boolean brandMatchesDatabase;

    /** True if production year matches the ABS/MUP database record. */
    private boolean productionYearMatchesDatabase;

    /** True if vehicle record exists in ABS database at all. */
    private boolean existsInDatabase;
}
