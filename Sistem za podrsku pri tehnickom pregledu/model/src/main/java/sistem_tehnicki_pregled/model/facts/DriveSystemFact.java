package sistem_tehnicki_pregled.model.facts;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Engine, ignition, and fuel-supply inspection results.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DriveSystemFact {

    /** Engine mounts (nosači motora) are in good condition. */
    private boolean engineMountsIntact;

    /** No oil or other fluid leaking from the engine. */
    private boolean noEngineFluidLeak;

    /** Ignition system has no mechanical damage. */
    private boolean ignitionSystemIntact;

    /** Fuel-supply lines and components are correctly secured. */
    private boolean fuelSupplySecured;

    /** Fuel-supply system has no leaks. */
    private boolean noFuelLeak;

    private boolean transmissionSafeForSingleHandOperation;
}
