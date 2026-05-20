package sistem_tehnicki_pregled.model.enums;

public enum InspectionResult {
    PROSAO,          // Vehicle passed all checks
    NIJE_PROSAO,     // Vehicle failed — inspection completed but defects found
    PREKINUT,        // Inspection halted — critical fault detected
    NIJE_ZAPOCET,    // Inspection could not begin
    CREATED, // Info are inserted
    PRECONDITIONS_PASSED,
    IDENTIFICATION_PASSED,
    BRAKE_FLUID_PASSED,
    EXHAUST_SYSTEM_PASSED,
    LIGHTING_SYSTEM_PASSED,
    ENGINE_SYSTEM_PASSED,
    CHASSIS_SUSPENSION_PASSED,
    WHEELS_TYRES_PASSED,
    ELECTRICAL_SYSTEM_PASSED,
    MANDATORY_EQUIPMENT_PASSED,
    BRAKE_TEST_RUNNING,
    BRAKE_TEST_PASSED
}
