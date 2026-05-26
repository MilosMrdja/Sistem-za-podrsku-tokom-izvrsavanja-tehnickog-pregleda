import { LightBulbType } from '../../models/enums/light-bulb-type.enum';

export interface LightingSystemDto {
  inspectionId: number;
  lowBeamIntensityM: number;
  highBeamIntensityM: number;
  frontFogLightIntensityM: number;
  rearFogLightIntensityM: number;
  frontPositionLightIntensityM: number;
  rearPositionLightIntensityM: number;
  parkingLightIntensityM: number;
  stopLightIntensityM: number;
  headlightBulbType: LightBulbType;
  highBeamOrientationLx: number;
  lowBeamOrientationLx: number;
  reversingLightsWhite: boolean;
  plateLightOperational: boolean;
  thirdStopLightPresent: boolean;
  turnIndicatorsAmber: boolean;
  turnIndicatorFrequencyPerMin: number;
  hazardSwitchFunctional: boolean;
  turnIndicatorsFunctional: boolean;
  frontSideTurnIndicatorCount: number;
  frontTurnIndicatorCount: number;
  rearTurnIndicatorCount: number;
  sideTurnIndicatorCount: number;
}
