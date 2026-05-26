import { Component, inject } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { finalize } from 'rxjs';
import { InspectionApiService } from '../../../services/api/inspection-api.service';
import { InspectionFlowService } from '../../../services/inspection-flow.service';
import { InspectionSessionService } from '../../../services/inspection-session.service';
import { EngineSystemDto } from '../../../dto/request/engine-system.dto';
import { InspectionPageBase } from '../shared/inspection-page.base';

@Component({ standalone: false, selector: 'app-engine-system-page', templateUrl: './engine-system.page.html' })

export class EngineSystemPage extends InspectionPageBase {
  private readonly fb = inject(FormBuilder);

  loading = false;
  readonly form = this.fb.group({
    engineMountsIntact: [true, Validators.required],
    noEngineFluidLeak: [true, Validators.required],
    ignitionSystemIntact: [true, Validators.required],
    fuelSupplySecured: [true, Validators.required],
    noFuelLeak: [true, Validators.required],
    transmissionSafeForSingleHandOperation: [true, Validators.required],
  });

  constructor(private readonly api: InspectionApiService,
    private readonly flow: InspectionFlowService,
    session: InspectionSessionService,
  ) {
    super(session);
  }

  submit(): void {
    this.loading = true;
    this.api
      .engineSystem({
        inspectionId: this.session.requireSession().inspectionId,
        ...(this.form.getRawValue() as Omit<EngineSystemDto, 'inspectionId'>),
      })
      .pipe(finalize(() => (this.loading = false)))
      .subscribe((res) => this.flow.handleSuccess(res));
  }
}
