import { Component, inject } from '@angular/core';
import { FormArray, FormBuilder, Validators } from '@angular/forms';
import { finalize } from 'rxjs';
import { AxleId } from '../../../models/enums/axle-id.enum';
import { WheelId } from '../../../models/enums/wheel-id.enum';
import { InspectionApiService } from '../../../services/api/inspection-api.service';
import { InspectionFlowService } from '../../../services/inspection-flow.service';
import { InspectionSessionService } from '../../../services/inspection-session.service';
import {
  requiredPositiveNumberValidators,
  touchInvalidForm,
} from '../../../shared/form-validators';
import { InspectionPageBase } from '../shared/inspection-page.base';

interface WheelFormConfig {
  wheelId: WheelId;
  label: string;
  axleId: AxleId;
}

@Component({
  standalone: false,
  selector: 'app-wheel-tire-page',
  templateUrl: './wheel-tire.page.html',
  styleUrl: './wheel-tire.page.scss',
})
export class WheelTirePage extends InspectionPageBase {
  private readonly fb = inject(FormBuilder);

  loading = false;
  readonly axleOptions = Object.values(AxleId).map((v) => ({
    label: v,
    value: v,
  }));
  readonly constructionOptions = [
    { label: 'Radijalna', value: 'RADIAL' },
    { label: 'Dijagonalna', value: 'BIAS' },
  ];
  readonly wheelConfigs: WheelFormConfig[] = [
    { wheelId: WheelId.FL, label: 'Prednji levi (FL)', axleId: AxleId.FRONT },
    { wheelId: WheelId.FR, label: 'Prednji desni (FR)', axleId: AxleId.FRONT },
    { wheelId: WheelId.RL, label: 'Zadnji levi (RL)', axleId: AxleId.REAR },
    { wheelId: WheelId.RR, label: 'Zadnji desni (RR)', axleId: AxleId.REAR },
  ];

  readonly form = this.fb.group({
    wheels: this.fb.array(
      this.wheelConfigs.map((wheel) => this.createWheelGroup(wheel)),
    ),
  });

  constructor(
    private readonly api: InspectionApiService,
    private readonly flow: InspectionFlowService,
    session: InspectionSessionService,
  ) {
    super(session);
  }

  get wheels(): FormArray {
    return this.form.controls.wheels;
  }

  private createWheelGroup(wheel: WheelFormConfig) {
    return this.fb.group({
      wheelId: [wheel.wheelId, Validators.required],
      axleId: [{ value: wheel.axleId, disabled: true }, Validators.required],
      widthMm: [205, requiredPositiveNumberValidators()],
      aspectRatioPercent: [55, requiredPositiveNumberValidators()],
      rimDiameterInches: [16, requiredPositiveNumberValidators()],
      loadIndex: [91, requiredPositiveNumberValidators()],
      constructionType: ['RADIAL', Validators.required],
      speedRating: ['V', [Validators.required, Validators.minLength(1)]],
      homologated: [true, Validators.required],
      noPlayOrDamage: [true, Validators.required],
    });
  }

  submit(): void {
    if (!touchInvalidForm(this.form)) {
      return;
    }
    const raw = this.form.getRawValue();
    this.loading = true;
    this.api
      .wheelTire({
        inspectionId: this.session.requireSession().inspectionId,
        wheels: raw.wheels.map((wheel) => ({
          wheelId: wheel.wheelId as WheelId,
          axleId: wheel.axleId as AxleId,
          widthMm: Number(wheel.widthMm),
          aspectRatioPercent: Number(wheel.aspectRatioPercent),
          rimDiameterInches: Number(wheel.rimDiameterInches),
          loadIndex: Number(wheel.loadIndex),
          constructionType: wheel.constructionType!,
          speedRating: wheel.speedRating!,
          homologated: !!wheel.homologated,
          noPlayOrDamage: !!wheel.noPlayOrDamage,
        })),
      })
      .pipe(finalize(() => (this.loading = false)))
      .subscribe((res) => this.flow.handleSuccess(res));
  }
}
