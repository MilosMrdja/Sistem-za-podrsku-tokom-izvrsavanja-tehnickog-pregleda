import { Component, inject } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { finalize } from 'rxjs';
import { InspectionApiService } from '../../../services/api/inspection-api.service';
import { InspectionFlowService } from '../../../services/inspection-flow.service';
import { InspectionSessionService } from '../../../services/inspection-session.service';
import {
  requiredPositiveNumberValidators,
  touchInvalidForm,
} from '../../../shared/form-validators';
import { InspectionPageBase } from '../shared/inspection-page.base';

@Component({
  standalone: false,
  selector: 'app-brake-fluid-page',
  templateUrl: './brake-fluid.page.html',
})
export class BrakeFluidPage extends InspectionPageBase {
  private readonly fb = inject(FormBuilder);

  loading = false;
  readonly form = this.fb.group({
    moisturePercent: [2, requiredPositiveNumberValidators()],
    boilingPointCelsius: [200, requiredPositiveNumberValidators()],
    systemLeaking: [false, Validators.required],
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
      .brakeFluid({
        inspectionId: this.session.requireSession().inspectionId,
        moisturePercent: Number(raw.moisturePercent),
        boilingPointCelsius: Number(raw.boilingPointCelsius),
        systemLeaking: !!raw.systemLeaking,
      })
      .pipe(finalize(() => (this.loading = false)))
      .subscribe((res) => this.flow.handleSuccess(res));
  }
}
