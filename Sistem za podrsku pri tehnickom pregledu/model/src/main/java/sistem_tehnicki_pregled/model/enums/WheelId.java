package sistem_tehnicki_pregled.model.enums;

import lombok.Getter;

@Getter
public enum WheelId {
    FL(AxleId.FRONT),
    FR(AxleId.FRONT),
    RL(AxleId.REAR),
    RR(AxleId.REAR);

    private final AxleId axleId;

    WheelId(AxleId axleId) {
        this.axleId = axleId;
    }

}
