package sistem_tehnicki_pregled.model.facts;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BrakeFailureFact {

    private Long inspectionId;

    private String reason;

    public static BrakeFailureFact of(Long id, String reason) {
        return BrakeFailureFact.builder().inspectionId(id).reason(reason).build();
    }
}
