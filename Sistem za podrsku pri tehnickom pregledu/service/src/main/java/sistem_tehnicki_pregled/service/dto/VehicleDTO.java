package sistem_tehnicki_pregled.service.dto;

import jakarta.validation.constraints.*;
import sistem_tehnicki_pregled.model.enums.FuelType;
import sistem_tehnicki_pregled.model.enums.VehicleBrand;
import sistem_tehnicki_pregled.model.enums.VehicleModel;
import sistem_tehnicki_pregled.model.enums.VehicleType;
import lombok.Data;

import java.time.LocalDate;

/**
 * Vehicle administrative and identification data supplied by the operator.
 */
@Data
public class VehicleDTO {

    @NotBlank(message = "Broj šasije (VIN) je obavezan")
    private String vin;

    @NotBlank(message = "Kod motora je obavezan")
    private String engineCode;

    @NotBlank(message = "Registarska oznaka je obavezna")
    private String registrationPlate;

    @NotNull(message = "Marka vozila je obavezna")
    private VehicleBrand brand;

    @NotNull(message = "Model vozila je obavezan")
    private VehicleModel model;

    @NotNull(message = "Tip vozila je obavezan")
    private VehicleType vehicleType;

    @NotNull(message = "Tip goriva je obavezan")
    private FuelType fuelType;

    @NotNull(message = "Datum proizvodnje je obavezan")
    @PastOrPresent(message = "Datum proizvodnje ne može biti u budućnosti")
    private LocalDate productionDate;

    @NotNull(message = "Datum prve registracije je obavezan")
    @PastOrPresent(message = "Datum prve registracije ne može biti u budućnosti")
    private LocalDate firstRegistrationDate;

    @NotNull(message = "Datum prve registracije u Srbiji je obavezan")
    @PastOrPresent(message = "Datum prve registracije u Srbiji ne može biti u budućnosti")
    private LocalDate firstRegistrationDateSerbia;

    @NotNull(message = "Maksimalna dozvoljena masa je obavezna")
    @Positive(message = "Masa mora biti veća od 0")
    private Double maxAllowedMassKg;

    @NotNull(message = "Opterećenje prednje osovine je obavezno")
    @PositiveOrZero(message = "Opterećenje prednje osovine ne može biti negativno")
    private Double frontAxleLoadKg;

    @NotNull(message = "Opterećenje zadnje osovine je obavezno")
    @PositiveOrZero(message = "Opterećenje zadnje osovine ne može biti negativno")
    private Double rearAxleLoadKg;

    @NotNull(message = "Snaga motora je obavezna")
    @Positive(message = "Snaga motora mora biti veća od 0")
    private Double enginePowerKw;

    @NotNull(message = "Dužina vozila je obavezna")
    @Positive(message = "Dužina vozila mora biti veća od 0")
    private Double lengthMetres;
}
