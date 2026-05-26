import { FuelType } from './enums/fuel-type.enum';
import { InspectionResult } from './enums/inspection-result.enum';

export interface InspectionSession {
  inspectionId: number;
  registrationPlate: string;
  vin: string;
  fuelType: FuelType;
  lastResult?: InspectionResult;
  lastResultLabel?: string;
  primaryReason?: string;
  finished?: boolean;
}
