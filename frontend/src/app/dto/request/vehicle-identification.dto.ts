export interface VehicleIdentificationDto {
  inspectionId: number;
  vinReadable: boolean;
  vinMatchesDocument: boolean;
  engineCodeMatchesDatabase: boolean;
  brandMatchesDatabase: boolean;
  productionYearMatchesDatabase: boolean;
  existsInDatabase: boolean;
}
