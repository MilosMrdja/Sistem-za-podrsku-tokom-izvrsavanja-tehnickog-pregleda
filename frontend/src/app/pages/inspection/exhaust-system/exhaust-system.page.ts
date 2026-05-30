import { Component, inject, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { finalize } from 'rxjs';
import { FuelType } from '../../../models/enums/fuel-type.enum';
import { InspectionApiService } from '../../../services/api/inspection-api.service';
import { InspectionFlowService } from '../../../services/inspection-flow.service';
import { InspectionSessionService } from '../../../services/inspection-session.service';
import {
  requiredPercentageValidators,
  requiredPositiveNumberValidators,
  touchInvalidForm,
} from '../../../shared/form-validators';
import { InspectionPageBase } from '../shared/inspection-page.base';

@Component({
  standalone: false,
  selector: 'app-exhaust-system-page',
  templateUrl: './exhaust-system.page.html',
})
export class ExhaustSystemPage extends InspectionPageBase implements OnInit {
  private readonly fb = inject(FormBuilder);

  loading = false;
  get isDiesel(): boolean {
    return this.session.session?.fuelType === FuelType.DIESEL;
  }

  get isPetrol(): boolean {
    return this.session.session?.fuelType === FuelType.PETROL;
  }

  readonly form = this.fb.group({
    dieselAbsorptionCoefficient: [1.2],
    petrolCoPercent: [1],
  });

  constructor(
    private readonly api: InspectionApiService,
    private readonly flow: InspectionFlowService,
    session: InspectionSessionService,
  ) {
    super(session);
  }

  ngOnInit(): void {
    this.applyFuelValidators();
  }

  submit(): void {
    if (!touchInvalidForm(this.form)) {
      return;
    }
    const raw = this.form.getRawValue();
    this.loading = true;
    this.api
      .exhaustSystem({
        inspectionId: this.session.requireSession().inspectionId,
        dieselAbsorptionCoefficient: this.isDiesel
          ? Number(raw.dieselAbsorptionCoefficient)
          : undefined,
        petrolCoPercent: this.isPetrol
          ? Number(raw.petrolCoPercent)
          : undefined,
      })
      .pipe(finalize(() => (this.loading = false)))
      .subscribe((res) => this.flow.handleSuccess(res));
  }

  private applyFuelValidators(): void {
    const diesel = this.form.get('dieselAbsorptionCoefficient');
    const petrol = this.form.get('petrolCoPercent');

    if (this.isDiesel) {
      diesel?.setValidators(requiredPositiveNumberValidators());
      petrol?.clearValidators();
    } else if (this.isPetrol) {
      petrol?.setValidators(requiredPercentageValidators());
      diesel?.clearValidators();
    } else {
      diesel?.clearValidators();
      petrol?.clearValidators();
    }

    diesel?.updateValueAndValidity();
    petrol?.updateValueAndValidity();
  }
}
