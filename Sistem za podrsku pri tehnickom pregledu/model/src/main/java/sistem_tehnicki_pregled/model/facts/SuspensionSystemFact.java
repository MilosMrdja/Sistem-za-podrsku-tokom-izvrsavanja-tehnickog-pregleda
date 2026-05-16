package sistem_tehnicki_pregled.model.facts;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Mechanical integrity of all suspension and steering components.
 * A true value means the component is intact (no damage).
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SuspensionSystemFact {

    /** Control arms / wishbones (poluge) — no mechanical damage. */
    private boolean armsIntact;

    /** Fork / strut assembly (viljuška) — no mechanical damage. */
    private boolean forksIntact;

    /** Anti-roll / sway bar (stabilizator) — no mechanical damage. */
    private boolean stabilizersIntact;

    /** Ball joints / tie-rod ends (zglobovi) — no mechanical damage. */
    private boolean jointsIntact;

    /** Shock absorbers (amortizeri) — no mechanical damage. */
    private boolean shockAbsorbersIntact;

    /** Springs (opruge) — no mechanical damage. */
    private boolean springsIntact;
}