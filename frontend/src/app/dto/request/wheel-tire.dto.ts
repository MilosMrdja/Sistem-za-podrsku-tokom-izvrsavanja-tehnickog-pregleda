import { AxleId } from '../../models/enums/axle-id.enum';
import { WheelId } from '../../models/enums/wheel-id.enum';

export interface WheelTireItemDto {
  wheelId: WheelId;
  axleId: AxleId;
  widthMm: number;
  aspectRatioPercent: number;
  rimDiameterInches: number;
  loadIndex: number;
  constructionType: string;
  speedRating: string;
  homologated: boolean;
  noPlayOrDamage: boolean;
}

export interface WheelTireDto {
  inspectionId: number;
  wheels: WheelTireItemDto[];
}
