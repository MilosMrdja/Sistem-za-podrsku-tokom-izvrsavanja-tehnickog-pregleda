package sistem_tehnicki_pregled.model.facts;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sistem_tehnicki_pregled.model.enums.LightBulbType;

/**
 * All lighting-related measurements and functional checks.
 * Intensities are measured in metres (visibility distance) unless otherwise noted.
 * Orientation (usmerenost) is measured in lux (lx).
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LightingSystemFact {

    // ── Main headlights ────────────────────────────────────────────────────────
    /** Low-beam (kratko svetlo) intensity in metres. Required: 40–80 m. */
    private double lowBeamIntensityM;

    /** High-beam (dugo svetlo) intensity in metres. Required: >= 100 m. */
    private double highBeamIntensityM;

    // ── Fog lights ─────────────────────────────────────────────────────────────
    /** Front fog-light intensity in metres. Required: <= 35 m. */
    private double frontFogLightIntensityM;

    /** Rear fog-light intensity in metres. Required: 0.25–1 m. */
    private double rearFogLightIntensityM;

    // ── Position / parking / stop lights ──────────────────────────────────────
    /** Front position-light (prednje poziciono) intensity in metres. Required: 0.25–1.5 m. */
    private double frontPositionLightIntensityM;

    /** Rear position-light (zadnje poziciono) intensity in metres. Required: 0.35–1 m. */
    private double rearPositionLightIntensityM;

    /** Parking-light intensity in metres. Required: 0.35–1.9 m. */
    private double parkingLightIntensityM;

    /** Stop-light intensity in metres. Required: 0.35–1.5 m. */
    private double stopLightIntensityM;

    // ── Orientation (usmerenost) — only for vehicles first registered after 01.07.2012 ──
    /** Bulb type of the headlight — determines the high-beam orientation threshold. */
    private LightBulbType headlightBulbType;

    /** High-beam orientation value in lux. */
    private double highBeamOrientationLx;

    /** Low-beam (kratko svetlo) orientation value in lux. Required: <= 4 lx. */
    private double lowBeamOrientationLx;

    // ── Functional / colour checks ─────────────────────────────────────────────
    /** Reversing lights emit white light. */
    private boolean reversingLightsWhite;

    /** Licence-plate light is operational. */
    private boolean plateLightOperational;

    /**
     * Third (centre) stop light is present.
     * Mandatory for vehicles first registered after 01.03.2011.
     */
    private boolean thirdStopLightPresent;

    /** Turn indicators (pokazivači pravca) are amber / yellow. */
    private boolean turnIndicatorsAmber;

    /** Flashing frequency of turn indicators in flashes per minute. Required: 60–120. */
    private int turnIndicatorFrequencyPerMin;

    /** Hazard-light switch (istovremeno uključivanje) is functional. */
    private boolean hazardSwitchFunctional;

    /** All individual turn indicators are functional. */
    private boolean turnIndicatorsFunctional;

    /**
     * Number of front side-turn indicators fitted.
     * Used together with vehicle length to verify correct indicator configuration.
     */
    private int frontSideTurnIndicatorCount;

    /** Number of front turn indicators. */
    private int frontTurnIndicatorCount;

    /** Number of rear turn indicators. */
    private int rearTurnIndicatorCount;

    /** Number of side turn indicators (bočni pokazivači — typically for vehicles > 6 m). */
    private int sideTurnIndicatorCount;
}
