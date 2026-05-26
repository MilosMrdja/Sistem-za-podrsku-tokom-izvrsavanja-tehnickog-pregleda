import { Component, inject } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { finalize } from 'rxjs';
import { InspectionApiService } from '../../../services/api/inspection-api.service';
import { InspectionFlowService } from '../../../services/inspection-flow.service';
import { InspectionSessionService } from '../../../services/inspection-session.service';
import { SuspensionSystemDto } from '../../../dto/request/suspension-system.dto';
import { InspectionPageBase } from '../shared/inspection-page.base';

@Component({ standalone: false, selector: 'app-suspension-system-page', templateUrl: './suspension-system.page.html' })

export class SuspensionSystemPage extends InspectionPageBase {
  private readonly fb = inject(FormBuilder);

  loading = false;
  readonly form = this.fb.group({
    armsIntact: [true, Validators.required],
    forksIntact: [true, Validators.required],
    stabilizersIntact: [true, Validators.required],
    jointsIntact: [true, Validators.required],
    shockAbsorbersIntact: [true, Validators.required],
    springsIntact: [true, Validators.required],
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
      .suspensionSystem({
        inspectionId: this.session.requireSession().inspectionId,
        ...(this.form.getRawValue() as Omit<SuspensionSystemDto, 'inspectionId'>),
      })
      .pipe(finalize(() => (this.loading = false)))
      .subscribe((res) => this.flow.handleSuccess(res));
  }
}
