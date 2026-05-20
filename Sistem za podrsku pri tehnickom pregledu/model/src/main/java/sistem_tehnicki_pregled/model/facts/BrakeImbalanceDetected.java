package sistem_tehnicki_pregled.model.facts;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sistem_tehnicki_pregled.model.enums.AxleId;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BrakeImbalanceDetected {

    private Long inspectionId;

    private AxleId axleId;

    private double imbalancePercent;
}
