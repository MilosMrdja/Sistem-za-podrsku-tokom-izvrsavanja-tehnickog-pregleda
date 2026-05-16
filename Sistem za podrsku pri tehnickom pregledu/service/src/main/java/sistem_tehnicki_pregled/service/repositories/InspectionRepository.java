package sistem_tehnicki_pregled.service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sistem_tehnicki_pregled.model.entities.Inspection;

public interface InspectionRepository
        extends JpaRepository<Inspection, Long> {
}