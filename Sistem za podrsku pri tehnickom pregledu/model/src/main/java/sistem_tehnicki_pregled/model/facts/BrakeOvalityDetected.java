package sistem_tehnicki_pregled.model.facts;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sistem_tehnicki_pregled.model.enums.WheelId;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BrakeOvalityDetected {

    private Long inspectionId;

    private WheelId wheelId;

    private double ovalityPercent;
}
