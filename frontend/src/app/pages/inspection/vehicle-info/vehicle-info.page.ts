import { Component, inject, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { finalize } from 'rxjs';
import { FuelType } from '../../../models/enums/fuel-type.enum';
import { VehicleBrand } from '../../../models/enums/vehicle-brand.enum';
import { VehicleType } from '../../../models/enums/vehicle-type.enum';
import {
  getVehicleBrandOptions,
  getVehicleModelOptions,
  toVehicleBrandApiValue,
  toVehicleModelApiValue,
} from '../../../models/data/vehicle-brand.utils';
import { InspectionApiService } from '../../../services/api/inspection-api.service';
import { InspectionFlowService } from '../../../services/inspection-flow.service';
import { InspectionSessionService } from '../../../services/inspection-session.service';
import { VehicleDto } from '../../../dto/request/vehicle.dto';
import {
  dateNotBefore,
  linkDateRevalidation,
  requiredPositiveNumberValidators,
  requiredRegistrationPlateValidators,
  requiredStringValidators,
  requiredVinValidators,
  touchInvalidForm,
} from '../../../shared/form-validators';
import { VehicleSelectOption } from '../../../models/data/vehicle-brand.utils';

@Component({
  standalone: false,
  selector: 'app-vehicle-info-page',
  templateUrl: './vehicle-info.page.html',
})
export class VehicleInfoPage implements OnInit {
  private readonly fb = inject(FormBuilder);

  loading = false;

  readonly brandOptions = getVehicleBrandOptions();
  modelOptions: VehicleSelectOption<string>[] = [];

  readonly fuelOptions = [
    { label: 'Dizel', value: FuelType.DIESEL },
    { label: 'Benzin (OTO)', value: FuelType.PETROL },
    { label: 'Gas', value: FuelType.GAS },
  ];

  readonly vehicleTypeOptions = [
    { label: 'M1', value: VehicleType.M1 },
    { label: 'M2', value: VehicleType.M2 },
    { label: 'M3', value: VehicleType.M3 },
  ];

  readonly form = this.fb.group({
    vin: ['', requiredVinValidators()],
    engineCode: ['', requiredStringValidators()],
    registrationPlate: ['', requiredRegistrationPlateValidators()],
    brand: ['' as VehicleBrand | '', Validators.required],
    model: [{ value: '', disabled: true }, Validators.required],
    vehicleType: [VehicleType.M1, Validators.required],
    fuelType: [FuelType.PETROL, Validators.required],
    productionDate: ['', Validators.required],
    firstRegistrationDate: [
      '',
      [
        Validators.required,
        dateNotBefore('productionDate', 'dateBeforeProduction'),
      ],
    ],
    firstRegistrationDateSerbia: [
      '',
      [
        Validators.required,
        dateNotBefore('firstRegistrationDate', 'dateBeforeFirstRegistration'),
      ],
    ],
    maxAllowedMassKg: [1500, requiredPositiveNumberValidators()],
    frontAxleLoadKg: [700, requiredPositiveNumberValidators()],
    rearAxleLoadKg: [800, requiredPositiveNumberValidators()],
    enginePowerKw: [80, requiredPositiveNumberValidators()],
    lengthMetres: [4.2, requiredPositiveNumberValidators()],
  });

  constructor(
    private readonly api: InspectionApiService,
    private readonly flow: InspectionFlowService,
    private readonly session: InspectionSessionService,
  ) {
    this.session.clear();
    linkDateRevalidation(this.form, 'productionDate', [
      'firstRegistrationDate',
      'firstRegistrationDateSerbia',
    ]);
    linkDateRevalidation(this.form, 'firstRegistrationDate', [
      'firstRegistrationDateSerbia',
    ]);
  }

  ngOnInit(): void {
    this.form.get('brand')?.valueChanges.subscribe((brand) => {
      this.onBrandChange(brand as VehicleBrand | '');
    });
  }

  submit(): void {
    if (!touchInvalidForm(this.form)) {
      return;
    }
    this.loading = true;
    const raw = this.form.getRawValue() as VehicleDto;
    this.api
      .createInspection({
        ...raw,
        registrationPlate: String(raw.registrationPlate).toUpperCase(),
        vin: String(raw.vin).toUpperCase(),
        brand: toVehicleBrandApiValue(raw.brand),
        model: toVehicleModelApiValue(raw.brand, raw.model),
        vehicleType: raw.vehicleType as VehicleType,
        fuelType: raw.fuelType as FuelType,
        productionDate: String(raw.productionDate),
        firstRegistrationDate: String(raw.firstRegistrationDate),
        firstRegistrationDateSerbia: String(raw.firstRegistrationDateSerbia),
        maxAllowedMassKg: Number(raw.maxAllowedMassKg),
        frontAxleLoadKg: Number(raw.frontAxleLoadKg),
        rearAxleLoadKg: Number(raw.rearAxleLoadKg),
        enginePowerKw: Number(raw.enginePowerKw),
        lengthMetres: Number(raw.lengthMetres),
      })
      .pipe(finalize(() => (this.loading = false)))
      .subscribe((res) => {
        this.session.initFromVehicleResponse(res, raw.fuelType as FuelType);
        this.flow.handleSuccess(res);
      });
  }

  private onBrandChange(brand: VehicleBrand | ''): void {
    const modelCtrl = this.form.get('model');
    if (!modelCtrl) {
      return;
    }

    modelCtrl.reset('');
    this.modelOptions = getVehicleModelOptions(brand || null);

    if (!brand) {
      modelCtrl.disable();
      return;
    }

    modelCtrl.enable();
  }
}
