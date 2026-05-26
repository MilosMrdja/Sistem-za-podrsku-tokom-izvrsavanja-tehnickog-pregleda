import { Component, inject } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { finalize } from 'rxjs';
import { LightBulbType } from '../../../models/enums/light-bulb-type.enum';
import { InspectionApiService } from '../../../services/api/inspection-api.service';
import { InspectionFlowService } from '../../../services/inspection-flow.service';
import { InspectionSessionService } from '../../../services/inspection-session.service';
import {
  requiredNonNegativeNumberValidators,
  requiredPositiveNumberValidators,
  touchInvalidForm,
} from '../../../shared/form-validators';
import { InspectionPageBase } from '../shared/inspection-page.base';

@Component({
  standalone: false,
  selector: 'app-lighting-system-page',
  templateUrl: './lighting-system.page.html',
})
export class LightingSystemPage extends InspectionPageBase {
  private readonly fb = inject(FormBuilder);

  loading = false;
  readonly bulbOptions = Object.values(LightBulbType).map((v) => ({
    label: v,
    value: v,
  }));

  readonly form = this.fb.group({
    lowBeamIntensityM: [400, requiredPositiveNumberValidators()],
    highBeamIntensityM: [600, requiredPositiveNumberValidators()],
    frontFogLightIntensityM: [0, requiredNonNegativeNumberValidators()],
    rearFogLightIntensityM: [0, requiredNonNegativeNumberValidators()],
    frontPositionLightIntensityM: [100, requiredPositiveNumberValidators()],
    rearPositionLightIntensityM: [80, requiredPositiveNumberValidators()],
    parkingLightIntensityM: [90, requiredPositiveNumberValidators()],
    stopLightIntensityM: [200, requiredPositiveNumberValidators()],
    headlightBulbType: [
      LightBulbType.HALOGEN_DUAL_FILAMENT,
      Validators.required,
    ],
    highBeamOrientationLx: [1.5, requiredPositiveNumberValidators()],
    lowBeamOrientationLx: [1.2, requiredPositiveNumberValidators()],
    reversingLightsWhite: [true, Validators.required],
    plateLightOperational: [true, Validators.required],
    thirdStopLightPresent: [true, Validators.required],
    turnIndicatorsAmber: [true, Validators.required],
    turnIndicatorFrequencyPerMin: [90, requiredPositiveNumberValidators()],
    hazardSwitchFunctional: [true, Validators.required],
    turnIndicatorsFunctional: [true, Validators.required],
    frontSideTurnIndicatorCount: [0, requiredNonNegativeNumberValidators()],
    frontTurnIndicatorCount: [2, requiredPositiveNumberValidators()],
    rearTurnIndicatorCount: [2, requiredPositiveNumberValidators()],
    sideTurnIndicatorCount: [0, requiredNonNegativeNumberValidators()],
  });

  constructor(
    private readonly api: InspectionApiService,
    private readonly flow: InspectionFlowService,
    session: InspectionSessionService,
  ) {
    super(session);
  }

  submit(): void {
    if (!touchInvalidForm(this.form)) {
      return;
    }
    const raw = this.form.getRawValue();
    this.loading = true;
    this.api
      .lightingSystem({
        inspectionId: this.session.requireSession().inspectionId,
        headlightBulbType: raw.headlightBulbType as LightBulbType,
        lowBeamIntensityM: Number(raw.lowBeamIntensityM),
        highBeamIntensityM: Number(raw.highBeamIntensityM),
        frontFogLightIntensityM: Number(raw.frontFogLightIntensityM),
        rearFogLightIntensityM: Number(raw.rearFogLightIntensityM),
        frontPositionLightIntensityM: Number(raw.frontPositionLightIntensityM),
        rearPositionLightIntensityM: Number(raw.rearPositionLightIntensityM),
        parkingLightIntensityM: Number(raw.parkingLightIntensityM),
        stopLightIntensityM: Number(raw.stopLightIntensityM),
        highBeamOrientationLx: Number(raw.highBeamOrientationLx),
        lowBeamOrientationLx: Number(raw.lowBeamOrientationLx),
        turnIndicatorFrequencyPerMin: Number(raw.turnIndicatorFrequencyPerMin),
        frontSideTurnIndicatorCount: Number(raw.frontSideTurnIndicatorCount),
        frontTurnIndicatorCount: Number(raw.frontTurnIndicatorCount),
        rearTurnIndicatorCount: Number(raw.rearTurnIndicatorCount),
        sideTurnIndicatorCount: Number(raw.sideTurnIndicatorCount),
        reversingLightsWhite: !!raw.reversingLightsWhite,
        plateLightOperational: !!raw.plateLightOperational,
        thirdStopLightPresent: !!raw.thirdStopLightPresent,
        turnIndicatorsAmber: !!raw.turnIndicatorsAmber,
        hazardSwitchFunctional: !!raw.hazardSwitchFunctional,
        turnIndicatorsFunctional: !!raw.turnIndicatorsFunctional,
      })
      .pipe(finalize(() => (this.loading = false)))
      .subscribe((res) => this.flow.handleSuccess(res));
  }
}
