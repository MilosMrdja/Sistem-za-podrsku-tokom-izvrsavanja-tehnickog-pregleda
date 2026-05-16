package sistem_tehnicki_pregled.service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sistem_tehnicki_pregled.model.entities.Vehicle;

public interface VehicleRepository
        extends JpaRepository<Vehicle, Long> {

    boolean existsByVin(String vin);
    boolean existsByRegistrationPlate(String registrationPlate);
}
