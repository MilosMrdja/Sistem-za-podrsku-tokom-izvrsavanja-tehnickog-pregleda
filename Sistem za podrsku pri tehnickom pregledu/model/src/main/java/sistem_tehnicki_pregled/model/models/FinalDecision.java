package sistem_tehnicki_pregled.model.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sistem_tehnicki_pregled.model.enums.InspectionResult;

import java.time.LocalDateTime;

/**
 * Final inspection decision — produced by Level-3 rules.
 * Exactly one instance is inserted at the start; the Level-3 rule that fires
 * sets the result field.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FinalDecision {

    @Builder.Default
    private InspectionResult result = InspectionResult.PROSAO;

    @Builder.Default
    private LocalDateTime decidedAt = LocalDateTime.now();

    /** Primary reason for a non-passing result (populated by the matching rule). */
    private String primaryReason;

    /** True once any Level-3 rule has set the result, prevents double-firing. */
    private boolean resolved;
}
