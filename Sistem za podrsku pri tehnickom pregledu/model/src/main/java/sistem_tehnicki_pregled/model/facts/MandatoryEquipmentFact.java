package sistem_tehnicki_pregled.model.facts;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Mandatory safety equipment presence check.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MandatoryEquipmentFact {

    /** Spare wheel present and usable. */
    private boolean spareWheelPresent;

    /** Warning triangle (sigurnosni trougao) present. */
    private boolean warningTrianglePresent;

    /** First-aid kit (prva pomoć) present. */
    private boolean firstAidKitPresent;

    /** Tow rope or tow bar (uže/poluga za vuču) present. */
    private boolean towEquipmentPresent;

    /** Reflective safety vest (svetloodbojni prsluk) present. */
    private boolean reflectiveVestPresent;
}
