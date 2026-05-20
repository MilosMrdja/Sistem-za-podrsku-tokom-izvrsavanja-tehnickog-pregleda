package sistem_tehnicki_pregled.service.factories;

import org.springframework.stereotype.Component;
import sistem_tehnicki_pregled.model.entities.Vehicle;
import sistem_tehnicki_pregled.model.enums.InspectionResult;
import sistem_tehnicki_pregled.model.models.FinalDecision;
import sistem_tehnicki_pregled.model.models.SystemStatus;
import sistem_tehnicki_pregled.service.dto.InspectionResponseDTO;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class InspectionResponseFactory {

    public InspectionResponseDTO build(Vehicle vehicle, InspectionResult result, LocalDateTime time, Long inspectionId) {
        return InspectionResponseDTO.builder()
                .registrationPlate(vehicle.getRegistrationPlate())
                .vin(vehicle.getVin())
                .inspectionId(inspectionId)
                .decidedAt(time)
                .result(result)
                .resultLabel(labelFor(result))
                .primaryReason(null)
                .systems(null)
                .build();
    }

    public InspectionResponseDTO build(Vehicle vehicle, FinalDecision decision) {
        return InspectionResponseDTO.builder()
                .registrationPlate(vehicle.getRegistrationPlate())
                .vin(vehicle.getVin())
                .inspectionId(null)
                .decidedAt(decision.getDecidedAt())
                .result(decision.getResult())
                .resultLabel(labelFor(decision.getResult()))
                .primaryReason(decision.getPrimaryReason())
                .systems(null)
                .build();
    }

    public InspectionResponseDTO build(
            Vehicle vehicle,
            FinalDecision decision,
            SystemStatus... systemsArray) { // Koristimo varargs (...)

        // Pretvaramo niz sistema u mapu pomoću Stream API-ja
        Map<String, InspectionResponseDTO.SystemResult> systems = Arrays.stream(systemsArray)
                .collect(Collectors.toMap(
                        SystemStatus::getSystemName,
                        this::toSystem
                ));

        return InspectionResponseDTO.builder()
                .registrationPlate(vehicle.getRegistrationPlate())
                .vin(vehicle.getVin())
                .inspectionId(null)
                .decidedAt(decision.getDecidedAt())
                .result(decision.getResult())
                .resultLabel(labelFor(decision.getResult()))
                .primaryReason(decision.getPrimaryReason())
                .systems(systems)
                .build();
    }

    private InspectionResponseDTO.SystemResult toSystem(SystemStatus status) {
        return InspectionResponseDTO.SystemResult.builder()
                .systemName(status.getSystemName())
                .passed(!status.isFailed())
                .failureReasons(status.getFailureReasons())
                .build();
    }

    private String labelFor(InspectionResult result) {
        return switch (result) {
            case PROSAO -> "Prošao";
            case NIJE_PROSAO -> "Nije prošao";
            case PREKINUT -> "Odmah prekinut";
            case NIJE_ZAPOCET -> "Nije započet";
            case CREATED -> "Podaci o vozilu uspešno uneseni";
            case PRECONDITIONS_PASSED -> "Preduslovi tehničkog pregleda ispunjeni";
            case IDENTIFICATION_PASSED -> "Identifikacija uspešno završena";
            case BRAKE_FLUID_PASSED -> "Kočiona tečnost ja uspešno završena";
            case EXHAUST_SYSTEM_PASSED -> "Provera izduvnih gasova je uspešno završena";
            case LIGHTING_SYSTEM_PASSED -> "Provera svetlosnih uređaja je uspešno završena";
            case ENGINE_SYSTEM_PASSED -> "Provera motornog sistema je uspešno završena";
            case CHASSIS_SUSPENSION_PASSED -> "Provera delova je uspešno završena";
            case WHEELS_TYRES_PASSED -> "Provera guma i točkova je uspešno završena";
            case ELECTRICAL_SYSTEM_PASSED -> "Provera elektroinstalacije je uspešno završena";
            case MANDATORY_EQUIPMENT_PASSED -> "Provera obavezne opreme je uspešno završena";
            case BRAKE_TEST_RUNNING -> "Provera kočnica je uspešno pokrenuta";
            case BRAKE_TEST_PASSED -> "Provera kočnica je uspešno završena";
        };
    }
}
