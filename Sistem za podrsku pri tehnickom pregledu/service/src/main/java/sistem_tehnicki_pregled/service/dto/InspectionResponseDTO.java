package sistem_tehnicki_pregled.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import sistem_tehnicki_pregled.model.enums.InspectionResult;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InspectionResponseDTO {
    /** The vehicle registration plate for reference. */
    private String registrationPlate;

    /** The VIN that was inspected. */
    private String vin;

    private Long inspectionId;

    /** Timestamp when the decision was made. */
    private LocalDateTime decidedAt;

    // ── Final result ──────────────────────────────────────────────────────────

    /** Overall outcome of the inspection. */
    private InspectionResult result;

    /**
     * Human-readable translation of the result.
     *   PROSAO        → "Prošao"
     *   NIJE_PROSAO   → "Nije prošao"
     *   PREKINUT      → "Odmah prekinut"
     *   NIJE_ZAPOCET  → "Nije započet"
     */
    private String resultLabel;

    /** Primary reason for a non-passing result. */
    private String primaryReason;

    // ── System-level breakdown ────────────────────────────────────────────────

    /**
     * Map of system name → PASS / FAIL and detailed failure reasons.
     * Key: system name constant (e.g. "BRAKE_SYSTEM", "LIGHTING", "ENGINE", "OTHER")
     */
    private Map<String, SystemResult> systems;

    // ── Nested type ───────────────────────────────────────────────────────────

    @Data
    @Builder
    public static class SystemResult {
        private String systemName;
        private boolean passed;
        private List<String> failureReasons;
    }
}
