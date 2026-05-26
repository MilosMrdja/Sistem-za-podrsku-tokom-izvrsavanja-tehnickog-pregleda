import { Component, inject } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { finalize } from 'rxjs';
import { InspectionApiService } from '../../../services/api/inspection-api.service';
import { InspectionFlowService } from '../../../services/inspection-flow.service';
import { InspectionSessionService } from '../../../services/inspection-session.service';
import { InspectionPageBase } from '../shared/inspection-page.base';
import { InspectionConditionsDto } from '../../../dto/request/inspection-conditions.dto';

@Component({ standalone: false,
  selector: 'app-pre-condition-page',
  templateUrl: './pre-condition.page.html',
})

export class PreConditionPage extends InspectionPageBase {
  private readonly fb = inject(FormBuilder);

  loading = false;
  readonly form = this.fb.group({
    vehicleClean: [true, Validators.required],
    vehicleOperational: [true, Validators.required],
    notOverloaded: [true, Validators.required],
    registeredInSerbia: [true, Validators.required],
    registrationActive: [true, Validators.required],
    bothPlatesPresent: [true, Validators.required],
    noCorrosionHoles: [true, Validators.required],
  });

  constructor(private readonly api: InspectionApiService,
    private readonly flow: InspectionFlowService,
    session: InspectionSessionService,
  ) {
    super(session);
  }

  submit(): void {
    const inspectionId = this.session.requireSession().inspectionId;
    this.loading = true;
    this.api
      .preCondition({
        inspectionId,
        ...(this.form.getRawValue() as Omit<InspectionConditionsDto, 'inspectionId'>),
      })
      .pipe(finalize(() => (this.loading = false)))
      .subscribe((res) => this.flow.handleSuccess(res));
  }
}
