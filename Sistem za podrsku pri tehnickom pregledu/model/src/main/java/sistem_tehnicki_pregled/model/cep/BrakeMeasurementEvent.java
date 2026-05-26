package sistem_tehnicki_pregled.model.cep;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Role.Type;
import org.kie.api.definition.type.Timestamp;
import sistem_tehnicki_pregled.model.enums.AxleId;
import sistem_tehnicki_pregled.model.enums.WheelId;

/**
 * Single brake-force measurement event emitted by the brake-test roller device.
 * Multiple events per wheel are generated during the 5-second test window.
 * Used both as a raw DTO from the frontend and as a Drools fact (CEP stream).
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Role(Type.EVENT)
@Timestamp("timestamp")
public class BrakeMeasurementEvent {

    private Long inspectionId;

    /** UTC timestamp of the measurement. */
    private long timestamp;

    /** Which wheel produced this reading. */
    private WheelId wheelId;

    /** Which axle this wheel belongs to. */
    private AxleId axleId;

    /** Measured braking force in decanewtons (daN). */
    private Double brakeForce;
}
