import { Injectable } from '@angular/core';
import { APP_CONFIG } from '../config/app.config';
import { InspectionSession } from '../models/inspection-session.model';
import { InspectionResponseDto } from '../dto/response/inspection-response.dto';
import { FuelType } from '../models/enums/fuel-type.enum';
import { InspectionResult } from '../models/enums/inspection-result.enum';

@Injectable({ providedIn: 'root' })
export class InspectionSessionService {
  get session(): InspectionSession | null {
    const raw = sessionStorage.getItem(APP_CONFIG.storageKey);
    return raw ? (JSON.parse(raw) as InspectionSession) : null;
  }

  get inspectionId(): number | null {
    return this.session?.inspectionId ?? null;
  }

  requireSession(): InspectionSession {
    const s = this.session;
    if (!s?.inspectionId) {
      throw new Error('Nema aktivnog pregleda. Počnite unosom vozila.');
    }
    return s;
  }

  initFromVehicleResponse(response: InspectionResponseDto, fuelType: FuelType): void {
    if (!response.inspectionId) {
      return;
    }
    console.log('Inspection response from backend:', response);
    this.save({
      inspectionId: response.inspectionId,
      registrationPlate: response.registrationPlate ?? '',
      vin: response.vin ?? '',
      fuelType,
      lastResult: response.result,
      lastResultLabel: response.resultLabel,
      primaryReason: response.primaryReason,
      systems: response.systems,
      finished: this.isTerminal(response.result),
    });
  }

  updateFromResponse(response: InspectionResponseDto): void {
    const current = this.session;
    if (!current) {
      return;
    }
    console.log('Inspection response from backend:', response);
    this.save({
      ...current,
      inspectionId: response.inspectionId ?? current.inspectionId,
      registrationPlate: response.registrationPlate ?? current.registrationPlate,
      vin: response.vin ?? current.vin,
      lastResult: response.result,
      lastResultLabel: response.resultLabel,
      primaryReason: response.primaryReason,
      systems: response.systems ?? current.systems,
      finished: this.isTerminal(response.result),
    });
  }

  clear(): void {
    sessionStorage.removeItem(APP_CONFIG.storageKey);
  }

  isTerminal(result: InspectionResult): boolean {
    return [
      InspectionResult.PROSAO,
      InspectionResult.NIJE_PROSAO,
      InspectionResult.PREKINUT,
      InspectionResult.NIJE_ZAPOCET,
    ].includes(result);
  }

  private save(session: InspectionSession): void {
    sessionStorage.setItem(APP_CONFIG.storageKey, JSON.stringify(session));
  }
}
