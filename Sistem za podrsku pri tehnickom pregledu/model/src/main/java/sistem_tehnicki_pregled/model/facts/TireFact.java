package sistem_tehnicki_pregled.model.facts;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sistem_tehnicki_pregled.model.enums.AxleId;
import sistem_tehnicki_pregled.model.enums.TireConstructionType;
import sistem_tehnicki_pregled.model.enums.WheelId;

/**
 * Technical data for a single tire/wheel.
 * Four instances are inserted into the working memory (FL, FR, RL, RR).
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TireFact {

    /** Which wheel position this instance represents. */
    private WheelId wheelId;

    /** Which axle this wheel belongs to. */
    private AxleId axleId;

    // ── Tyre dimensions & classification ──────────────────────────────────────
    /** Section width of the tyre in mm (e.g. 205). */
    private int widthMm;

    /** Aspect ratio — sidewall height as % of section width (e.g. 55). */
    private int aspectRatioPercent;

    /** Rim diameter in inches (e.g. 16). */
    private int rimDiameterInches;

    /** Load index of the tyre (e.g. 91). */
    private int loadIndex;

    /** Tyre construction type (radial or diagonal). */
    private TireConstructionType constructionType;

    /** Speed rating symbol (e.g. "V", "H"). */
    private String speedRating;

    // ── Approval / condition ───────────────────────────────────────────────────
    /** Tyre carries a valid homologation mark (e-mark). */
    private boolean homologated;

    /** Wheel/tyre assembly has no clearance issues or visible damage. */
    private boolean noPlayOrDamage;
}
