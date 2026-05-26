import { VehicleBrand } from '../enums/vehicle-brand.enum';
import { VEHICLE_MODELS_BY_BRAND } from './vehicle-models.by-brand';

export interface VehicleSelectOption<T = string> {
  label: string;
  value: T;
}

export function getVehicleBrandOptions(): VehicleSelectOption<VehicleBrand>[] {
  return Object.values(VehicleBrand).map((brand) => ({
    label: brand,
    value: brand,
  }));
}

export function getVehicleModelOptions(
  brand: VehicleBrand | string | null | undefined,
): VehicleSelectOption<string>[] {
  if (!brand) {
    return [];
  }
  const models =
    VEHICLE_MODELS_BY_BRAND[brand as VehicleBrand] ?? [];
  return models.map((model) => ({ label: model, value: model }));
}

export function toVehicleBrandApiValue(
  brand: VehicleBrand | string | null | undefined,
): string {
  const entry = Object.entries(VehicleBrand).find(
    ([, label]) => label === brand,
  );

  return entry?.[0] ?? String(brand ?? '').toUpperCase();
}

export function toVehicleModelApiValue(
  brand: VehicleBrand | string | null | undefined,
  model: string | null | undefined,
): string {
  const brandValue = toVehicleBrandApiValue(brand);
  const modelValue = String(model ?? '')
    .normalize('NFD')
    .replace(/[\u0300-\u036f]/g, '')
    .replace(/[^a-zA-Z0-9]+/g, '_')
    .replace(/^_+|_+$/g, '')
    .toUpperCase();

  return `${brandValue}_${modelValue}`;
}
