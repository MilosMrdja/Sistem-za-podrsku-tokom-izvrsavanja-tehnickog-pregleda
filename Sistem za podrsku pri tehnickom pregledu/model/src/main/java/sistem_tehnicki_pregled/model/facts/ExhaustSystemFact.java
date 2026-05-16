package sistem_tehnicki_pregled.model.facts;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Exhaust gas emission measurements.
 * Only the field relevant to the vehicle's fuel type needs to be populated.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExhaustSystemFact {

    /**
     * Diesel: mean light absorption coefficient of exhaust gas (m⁻¹ or %).
     * Threshold depends on production year and engine power — see rules.
     */
    private Double dieselAbsorptionCoefficient;

    /**
     * Petrol (OTO): volumetric carbon-monoxide content in exhaust gas (%).
     * Threshold depends on production date (before / after 01.01.2015).
     */
    private Double petrolCoPercent;
}