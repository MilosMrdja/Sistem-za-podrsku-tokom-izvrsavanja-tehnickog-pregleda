package sistem_tehnicki_pregled.service.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sistem_tehnicki_pregled.service.application.InspectionService;
import sistem_tehnicki_pregled.service.dto.InspectionConditionsDTO;
import sistem_tehnicki_pregled.service.dto.InspectionResponseDTO;
import sistem_tehnicki_pregled.service.dto.VehicleDTO;
import sistem_tehnicki_pregled.service.dto.VehicleIdentificationDTO;
import sistem_tehnicki_pregled.service.dto.facts.*;

@Slf4j
@RestController
@RequestMapping("/api/inspection")
@RequiredArgsConstructor
public class InspectionController {
    private final InspectionService inspectionService;

    @PostMapping("")
    public ResponseEntity<InspectionResponseDTO> createInspection(@Valid @RequestBody VehicleDTO request) {
        return ResponseEntity.ok(inspectionService.createInspection(request));
    }

    @PostMapping("/pre-condition")
    public ResponseEntity<InspectionResponseDTO> checkInspectionCondition(@Valid @RequestBody InspectionConditionsDTO request) {
        return ResponseEntity.ok(inspectionService.checkInspectionCondition(request));
    }
    @PostMapping("/identification")
    public ResponseEntity<InspectionResponseDTO> vehicleIdentification(@Valid @RequestBody VehicleIdentificationDTO request) {
        return ResponseEntity.ok(inspectionService.vehicleIdentification(request));
    }
    @PostMapping("/wheel-tire")
    public ResponseEntity<InspectionResponseDTO> wheelTire(@Valid @RequestBody WheelTireDTO request) {
        return ResponseEntity.ok(inspectionService.checkWheelTire(request));
    }
    @PostMapping("/engine-system")
    public ResponseEntity<InspectionResponseDTO> engineSystem(@Valid @RequestBody EngineSystemDTO request) {
        return ResponseEntity.ok(inspectionService.checkEngineSystem(request));
    }
    @PostMapping("/brake-fluid")
    public ResponseEntity<InspectionResponseDTO> brakeFluid(@Valid @RequestBody BrakeFluidDTO request) {
        return ResponseEntity.ok(inspectionService.checkBrakeFluid(request));
    }
    @PostMapping("/exhaust-system")
    public ResponseEntity<InspectionResponseDTO> exhaustSystem(@Valid @RequestBody ExhaustMeasurementDTO request) {
        return ResponseEntity.ok(inspectionService.checkExhaustSystem(request));
    }
    @PostMapping("/suspension-system")
    public ResponseEntity<InspectionResponseDTO> suspensionSystem(@Valid @RequestBody SuspensionSystemDTO request) {
        return ResponseEntity.ok(inspectionService.checkSuspensionSystem(request));
    }
    @PostMapping("/electrical-system")
    public ResponseEntity<InspectionResponseDTO> electricalSystem(@Valid @RequestBody ElectricalSystemDTO request) {
        return ResponseEntity.ok(inspectionService.checkElectricalSystem(request));
    }
    @PostMapping("/lighting-system")
    public ResponseEntity<InspectionResponseDTO> lightingSystem(@Valid @RequestBody LightingSystemDTO request) {
        return ResponseEntity.ok(inspectionService.checkLightingSystem(request));
    }
    @PostMapping("/mandatory-equipment")
    public ResponseEntity<InspectionResponseDTO> mandatoryEquipment(@Valid @RequestBody MandatoryEquipmentDTO request) {
        return ResponseEntity.ok(inspectionService.checkMandatoryEquipment(request));
    }


}
