package sistem_tehnicki_pregled.model.facts;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Electrical wiring and battery inspection results.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ElectricalInstallationFact {

    /**
     * All wiring is routed and protected so that cables cannot be damaged
     * by mechanical abrasion, cutting, or severing.
     */
    private boolean wiringProtected;

    /** Battery is securely fastened in its mounting location. */
    private boolean batterySecured;
}
