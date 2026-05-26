export interface EngineSystemDto {
  inspectionId: number;
  engineMountsIntact: boolean;
  noEngineFluidLeak: boolean;
  ignitionSystemIntact: boolean;
  fuelSupplySecured: boolean;
  noFuelLeak: boolean;
  transmissionSafeForSingleHandOperation: boolean;
}
