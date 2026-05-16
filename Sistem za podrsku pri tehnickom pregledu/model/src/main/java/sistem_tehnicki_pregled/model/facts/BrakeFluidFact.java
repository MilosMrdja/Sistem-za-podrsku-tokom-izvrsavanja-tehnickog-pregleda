package sistem_tehnicki_pregled.model.facts;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Brake fluid measurements and leakage status.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BrakeFluidFact {

    /** Moisture content in brake fluid, expressed as a percentage (%). */
    private Double moisturePercent;

    /** Measured boiling point of the brake fluid in degrees Celsius. */
    private Double boilingPointCelsius;

    /** True if there is any visible leakage in the brake system. */
    private Boolean systemLeaking;
}
