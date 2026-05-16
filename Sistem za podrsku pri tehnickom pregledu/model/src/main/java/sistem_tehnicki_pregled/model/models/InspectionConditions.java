package sistem_tehnicki_pregled.model.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Conditions that must be satisfied before the technical inspection can begin.
 * Any violation results in NIJE_ZAPOCET (inspection not started).
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InspectionConditions {

    /** Vehicle is visibly clean and in a drivable state. */
    private boolean vehicleClean;

    /** Vehicle is in a drivable/operational state. */
    private boolean vehicleOperational;

    /** Vehicle is not overloaded with cargo or passengers. */
    private boolean notOverloaded;

    /** Vehicle is registered in the Republic of Serbia. */
    private boolean registeredInSerbia;

    /**
     * Vehicle is currently active (not deregistered / odjavljen).
     * Only relevant if registeredInSerbia == true.
     */
    private boolean registrationActive;

    /**
     * Vehicle has both registration plates attached.
     * Only checked if registeredInSerbia == true.
     */
    private boolean bothPlatesPresent;

    /** Bodywork does NOT have rust-through holes (corrosion perforations). */
    private boolean noCorrosionHoles;
}
