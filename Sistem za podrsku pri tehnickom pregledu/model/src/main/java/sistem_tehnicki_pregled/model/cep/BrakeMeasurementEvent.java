package sistem_tehnicki_pregled.model.cep;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sistem_tehnicki_pregled.model.enums.AxleId;
import sistem_tehnicki_pregled.model.enums.WheelId;

import java.time.Instant;

/**
 * Single brake-force measurement event emitted by the brake-test roller device.
 * Multiple events per wheel are generated during the 5-second test window.
 * Used both as a raw DTO from the frontend and as a Drools fact (CEP stream).
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BrakeMeasurementEvent {

    /** UTC timestamp of the measurement. */
    private Instant timestamp;

    /** Which wheel produced this reading. */
    private WheelId wheelId;

    /** Which axle this wheel belongs to. */
    private AxleId axleId;

    /** Measured braking force in decanewtons (daN). */
    private double brakeForce;
}
