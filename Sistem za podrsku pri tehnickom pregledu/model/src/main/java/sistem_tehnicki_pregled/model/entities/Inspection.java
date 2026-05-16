package sistem_tehnicki_pregled.model.entities;

import jakarta.persistence.*;
import lombok.*;
import sistem_tehnicki_pregled.model.enums.InspectionResult;

import java.time.LocalDateTime;

@Entity
@Table(name = "inspections")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Inspection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    @Enumerated(EnumType.STRING)
    private InspectionResult result;

    private LocalDateTime startedAt;

    private LocalDateTime finishedAt;

    private boolean resolved;
}
