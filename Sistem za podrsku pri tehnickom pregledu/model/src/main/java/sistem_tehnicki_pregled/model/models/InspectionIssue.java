package sistem_tehnicki_pregled.model.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generic inspection issue produced by Level-1 (detection) rules and consumed
 * by Level-2 (system status) rules.  Matches the Drools template structure:
 *
 *   rule "Fail - @{system} - @{component} - @{issue}"
 *   when
 *       $i : InspectionIssue(system == "@{system}", component == "@{component}", issue == "@{issue}")
 *   then
 *       $i.setFailed(true); $i.setPassed(false);
 *   end
 *
 * Level-1 rules insert InspectionIssue instances; Level-2 reads them to set SystemStatus.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InspectionIssue {

    // ── Template fields (match the DRL template parameters) ───────────────────
    /** High-level system name, e.g. "BRAKE", "LIGHTS", "ENGINE". */
    private String system;

    /** Sub-component within the system, e.g. "FLUID", "SENSOR", "FUEL_SUPPLY". */
    private String component;

    /** Nature of the fault, e.g. "LEAK", "DAMAGED", "NOT_FUNCTIONAL", "OUT_OF_RANGE". */
    private String issue;

    // ── Rule-engine state ─────────────────────────────────────────────────────
    //private boolean failed;
    private boolean passed;

    /** Human-readable description inserted by the detecting rule. */
    private String description;

    // ── Convenience factory methods ───────────────────────────────────────────

    public static InspectionIssue of(String system, String component, String issue, String description) {
        return InspectionIssue.builder()
                .system(system)
                .component(component)
                .issue(issue)
                .description(description)
                .passed(true)
                .build();
    }
}
