package sistem_tehnicki_pregled.model.enums;

import lombok.Getter;

@Getter
public enum VehicleBrand {
    ABARTH("Abarth"),
    ALFA_ROMEO("Alfa Romeo"),
    AUDI("Audi"),
    BMW("BMW"),
    CHEVROLET("Chevrolet"),
    CHRYSLER("Chrysler"),
    CITROEN("Citroen"),
    CUPRA("Cupra"),
    DACIA("Dacia"),
    DODGE("Dodge"),
    DS("DS"),
    FIAT("Fiat"),
    FORD("Ford"),
    HONDA("Honda"),
    HYUNDAI("Hyundai"),
    JAGUAR("Jaguar"),
    JEEP("Jeep"),
    KIA("Kia"),
    LADA("Lada"),
    LAND_ROVER("Land Rover"),
    LEXUS("Lexus"),
    MAZDA("Mazda"),
    MERCEDES_BENZ("Mercedes-Benz"),
    MINI("Mini"),
    MITSUBISHI("Mitsubishi"),
    NISSAN("Nissan"),
    OPEL("Opel"),
    PEUGEOT("Peugeot"),
    PORSCHE("Porsche"),
    RENAULT("Renault"),
    SEAT("Seat"),
    SKODA("Skoda"),
    SMART("Smart"),
    SUBARU("Subaru"),
    SUZUKI("Suzuki"),
    TESLA("Tesla"),
    TOYOTA("Toyota"),
    VOLKSWAGEN("Volkswagen"),
    VOLVO("Volvo"),
    ZASTAVA("Zastava");

    private final String displayName;

    VehicleBrand(String displayName) {
        this.displayName = displayName;
    }

}
