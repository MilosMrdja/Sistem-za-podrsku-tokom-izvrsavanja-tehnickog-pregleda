import { Component, inject } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { finalize } from 'rxjs';
import { InspectionApiService } from '../../../services/api/inspection-api.service';
import { InspectionFlowService } from '../../../services/inspection-flow.service';
import { InspectionSessionService } from '../../../services/inspection-session.service';
import { MandatoryEquipmentDto } from '../../../dto/request/mandatory-equipment.dto';
import { InspectionPageBase } from '../shared/inspection-page.base';

@Component({ standalone: false, selector: 'app-mandatory-equipment-page', templateUrl: './mandatory-equipment.page.html' })

export class MandatoryEquipmentPage extends InspectionPageBase {
  private readonly fb = inject(FormBuilder);

  loading = false;
  readonly form = this.fb.group({
    spareWheelPresent: [true, Validators.required],
    warningTrianglePresent: [true, Validators.required],
    firstAidKitPresent: [true, Validators.required],
    towEquipmentPresent: [true, Validators.required],
    reflectiveVestPresent: [true, Validators.required],
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
      .mandatoryEquipment({
        inspectionId: this.session.requireSession().inspectionId,
        ...(this.form.getRawValue() as Omit<MandatoryEquipmentDto, 'inspectionId'>),
      })
      .pipe(finalize(() => (this.loading = false)))
      .subscribe((res) => this.flow.handleSuccess(res));
  }
}
