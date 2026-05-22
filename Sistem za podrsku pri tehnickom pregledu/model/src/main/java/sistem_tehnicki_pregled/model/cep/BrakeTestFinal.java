package sistem_tehnicki_pregled.model.cep;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BrakeTestFinal {
    private Long inspectionId;
    private boolean failed;
    private boolean resolved;

    public static BrakeTestFinal of(Long id) {
        return BrakeTestFinal.builder().inspectionId(id).failed(true).resolved(true).build();
    }
}
