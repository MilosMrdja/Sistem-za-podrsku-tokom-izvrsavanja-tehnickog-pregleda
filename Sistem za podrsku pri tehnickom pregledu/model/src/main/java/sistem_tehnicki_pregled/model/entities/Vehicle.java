package sistem_tehnicki_pregled.model.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import sistem_tehnicki_pregled.model.enums.FuelType;
import sistem_tehnicki_pregled.model.enums.VehicleBrand;
import sistem_tehnicki_pregled.model.enums.VehicleModel;
import sistem_tehnicki_pregled.model.enums.VehicleType;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "vehicles")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String vin;

    @Column(nullable = false)
    private String engineCode;

    @Column(unique = true, nullable = false)
    private String registrationPlate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VehicleBrand brand;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VehicleModel model;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VehicleType vehicleType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FuelType fuelType;

    @Column(nullable = false)
    private LocalDate productionDate;

    @Column(nullable = false)
    private LocalDate firstRegistrationDate;

    @Column(nullable = false)
    private LocalDate firstRegistrationDateSerbia;

    @Column(nullable = false)
    private Double maxAllowedMassKg;

    @Column(nullable = false)
    private Double frontAxleLoadKg;

    @Column(nullable = false)
    private Double rearAxleLoadKg;

    @Column(nullable = false)
    private Double enginePowerKw;

    @Column(nullable = false)
    private Double lengthMetres;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
