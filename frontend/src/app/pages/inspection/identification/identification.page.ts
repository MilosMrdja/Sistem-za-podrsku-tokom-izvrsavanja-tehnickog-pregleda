import { Component, inject } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { finalize } from 'rxjs';
import { InspectionApiService } from '../../../services/api/inspection-api.service';
import { InspectionFlowService } from '../../../services/inspection-flow.service';
import { InspectionSessionService } from '../../../services/inspection-session.service';
import { VehicleIdentificationDto } from '../../../dto/request/vehicle-identification.dto';
import { InspectionPageBase } from '../shared/inspection-page.base';

@Component({ standalone: false, selector: 'app-identification-page', templateUrl: './identification.page.html' })

export class IdentificationPage extends InspectionPageBase {
  private readonly fb = inject(FormBuilder);

  loading = false;
  readonly form = this.fb.group({
    vinReadable: [true, Validators.required],
    vinMatchesDocument: [true, Validators.required],
    engineCodeMatchesDatabase: [true, Validators.required],
    brandMatchesDatabase: [true, Validators.required],
    productionYearMatchesDatabase: [true, Validators.required],
    existsInDatabase: [true, Validators.required],
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
      .identification({
        inspectionId: this.session.requireSession().inspectionId,
        ...(this.form.getRawValue() as Omit<VehicleIdentificationDto, 'inspectionId'>),
      })
      .pipe(finalize(() => (this.loading = false)))
      .subscribe((res) => this.flow.handleSuccess(res));
  }
}
