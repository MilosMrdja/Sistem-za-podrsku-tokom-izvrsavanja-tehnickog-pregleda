import { FuelType } from '../../models/enums/fuel-type.enum';
import { VehicleType } from '../../models/enums/vehicle-type.enum';

export interface VehicleDto {
  vin: string;
  engineCode: string;
  registrationPlate: string;
  brand: string;
  model: string;
  vehicleType: VehicleType;
  fuelType: FuelType;
  productionDate: string;
  firstRegistrationDate: string;
  firstRegistrationDateSerbia: string;
  maxAllowedMassKg: number;
  frontAxleLoadKg: number;
  rearAxleLoadKg: number;
  enginePowerKw: number;
  lengthMetres: number;
}
