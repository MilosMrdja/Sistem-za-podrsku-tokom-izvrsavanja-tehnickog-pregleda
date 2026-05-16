package sistem_tehnicki_pregled.service.dto.facts;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MandatoryEquipmentDTO {

    @NotNull(message = "ID pregleda vozila je obavezan")
    private Long inspectionId;

    @NotNull(message = "Morate označiti da li postoji rezervni točak")
    private Boolean spareWheelPresent;

    @NotNull(message = "Morate označiti da li postoji sigurnosni trougao")
    private Boolean warningTrianglePresent;

    @NotNull(message = "Morate označiti da li postoji prva pomoć")
    private Boolean firstAidKitPresent;

    @NotNull(message = "Morate označiti da li postoji uže ili poluga za vuču")
    private Boolean towEquipmentPresent;

    @NotNull(message = "Morate označiti da li postoji svetloodbojni prsluk")
    private Boolean reflectiveVestPresent;
}
