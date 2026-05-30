import { FuelType } from './enums/fuel-type.enum';
import { InspectionResult } from './enums/inspection-result.enum';
import { SystemResultDto } from '../dto/response/inspection-response.dto';

export interface InspectionSession {
  inspectionId: number;
  registrationPlate: string;
  vin: string;
  fuelType: FuelType;
  lastResult?: InspectionResult;
  lastResultLabel?: string;
  primaryReason?: string;
  systems?: Record<string, SystemResultDto>;
  finished?: boolean;
}
