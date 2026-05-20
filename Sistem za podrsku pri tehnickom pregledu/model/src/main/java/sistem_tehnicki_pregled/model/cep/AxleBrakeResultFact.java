package sistem_tehnicki_pregled.model.cep;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sistem_tehnicki_pregled.model.enums.AxleId;

/**
 * Aggregated braking metrics per axle, computed by BrakeCepProcessor.
 * One instance per axle (FRONT / REAR) is inserted into working memory.
 *
 * Imbalance formula:
 *   imbalance (%) = |F_left - F_right| / max(F_left, F_right) * 100
 *   Limit: must be < 20 %.
 *
 * Braking efficiency formula:
 *   efficiency (%) = (F_left + F_right) / (vehicle_mass_on_axle * g) * 100
 *   Limit (overall service brake): >= 50 % for vehicles registered before 01.01.2017
 *                                  >= 58 % for vehicles registered after  01.01.2017
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AxleBrakeResultFact {
    private Long inspectionId;

    private AxleId axleId;

    /** Peak force of the left wheel (daN). */
    private Double leftMaxForce;

    /** Peak force of the right wheel (daN). */
    private Double rightMaxForce;

    /**
     * Imbalance between left and right wheel braking forces (%).
     * Limit: < 20 %.
     */
    private Double imbalancePercent;

    /**
     * Overall service-brake efficiency for this axle (%).
     * Limits: >= 50 % (pre-2017), >= 58 % (post-2017).
     */
    private Double brakingEfficiencyPercent;
}
