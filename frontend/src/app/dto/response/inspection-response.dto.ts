import { InspectionResult } from '../../models/enums/inspection-result.enum';

export interface SystemResultDto {
  systemName: string;
  passed: boolean;
  failureReasons: string[];
}

export interface InspectionResponseDto {
  registrationPlate?: string;
  vin?: string;
  inspectionId?: number;
  decidedAt?: string;
  result: InspectionResult;
  resultLabel?: string;
  primaryReason?: string;
  systems?: Record<string, SystemResultDto>;
}
