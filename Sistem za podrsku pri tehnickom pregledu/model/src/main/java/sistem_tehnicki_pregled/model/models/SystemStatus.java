package sistem_tehnicki_pregled.model.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Aggregated PASS/FAIL status for one vehicle system.
 * One instance per system is inserted by Level-1 init rules and updated by Level-2 rules.
 *
 * Systems (by name constant):
 *   BRAKE_SYSTEM   — kočioni sistem
 *   LIGHTING       — svetlosni uređaji
 *   ENGINE         — pogonski sistem + dovod goriva
 *   OTHER          — oslanjanje, oprema, menjač, instalacija, kretanje, ispušni
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SystemStatus {

    public static final String BRAKE_SYSTEM = "BRAKE_SYSTEM";
    public static final String LIGHTING     = "LIGHTING";
    public static final String ENGINE       = "ENGINE";
    public static final String MANDATORY_EQUIPMENT        = "MANDATORY_EQUIPMENT";
    public static final String ELECTRICAL_SYSTEM        = "ELECTRICAL_SYSTEM";
    public static final String WHEELS_TYRES        = "WHEELS_TYRES";
    public static final String CHASSIS_SUSPENSION        = "CHASSIS_SUSPENSION";
    public static final String EXHAUST_SYSTEM        = "EXHAUST_SYSTEM";

    /** Logical name of this system (use constants above). */
    private String systemName;

    /** True if at least one issue was detected for this system. */
    private boolean failed;

    /** Detailed descriptions of all individual faults collected during evaluation. */
    @Builder.Default
    private List<String> failureReasons = new ArrayList<>();

    public void addFailureReason(String reason) {
        this.failureReasons.add(reason);
        this.failed = true;
    }
}
