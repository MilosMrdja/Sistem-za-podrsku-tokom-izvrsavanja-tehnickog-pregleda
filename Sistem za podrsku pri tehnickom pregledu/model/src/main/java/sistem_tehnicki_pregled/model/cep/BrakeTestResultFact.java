package sistem_tehnicki_pregled.model.cep;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sistem_tehnicki_pregled.model.enums.AxleId;
import sistem_tehnicki_pregled.model.enums.WheelId;

/**
 * Pre-computed summary for a single wheel produced by the BrakeCepProcessor.
 * One instance is inserted per wheel into the Drools working memory.
 *
 * Calculations:
 *   maxForce    = max(brakeForce_i)   over the measurement window
 *   ovality (%) = (max(F) - min(F)) / max(F) * 100   over the window
 *
 * Imbalance is computed per axle (left vs right) by the processor and stored in
 * the AxleBrakeResultFact. Braking efficiency is also computed at axle level.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BrakeTestResultFact {
    private Long inspectionId;

    /** Which wheel this result belongs to. */
    private WheelId wheelId;

    /** Which axle this wheel belongs to. */
    private AxleId axleId;

    /** Maximum braking force recorded during the test window (daN). */
    private Double maxForce;

    /**
     * Disc / drum ovality expressed as a percentage.
     * Formula: (max(F) - min(F)) / max(F) * 100
     * Limit: must be < 20 %.
     */
    private Double ovalityPercent;

    public static BrakeTestResultFact of(Long id, WheelId wheelId, AxleId axleId, Double maxForce, Double ovalityPercent) {
        return BrakeTestResultFact.builder().inspectionId(id).wheelId(wheelId).axleId(axleId).maxForce(maxForce).ovalityPercent(ovalityPercent).build();
    }

}
