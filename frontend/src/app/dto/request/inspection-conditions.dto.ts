export interface InspectionConditionsDto {
  inspectionId: number;
  vehicleClean: boolean;
  vehicleOperational: boolean;
  notOverloaded: boolean;
  registeredInSerbia: boolean;
  registrationActive: boolean;
  bothPlatesPresent: boolean;
  noCorrosionHoles: boolean;
}
