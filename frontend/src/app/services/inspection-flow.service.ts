import { inject, Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { InspectionResult } from '../models/enums/inspection-result.enum';
import { InspectionResponseDto } from '../dto/response/inspection-response.dto';
import { InspectionSessionService } from './inspection-session.service';
import { DialogService } from './dialog.service';

const NEXT_ROUTE: Partial<Record<InspectionResult, string>> = {
  [InspectionResult.CREATED]: '/pregled/preduslovi',
  [InspectionResult.PRECONDITIONS_PASSED]: '/pregled/identifikacija',
  [InspectionResult.IDENTIFICATION_PASSED]: '/pregled/gume-tockovi',
  [InspectionResult.WHEELS_TYRES_PASSED]: '/pregled/motor',
  [InspectionResult.ENGINE_SYSTEM_PASSED]: '/pregled/kociona-tecnost',
  [InspectionResult.BRAKE_FLUID_PASSED]: '/pregled/izduvni',
  [InspectionResult.EXHAUST_SYSTEM_PASSED]: '/pregled/ogibljenje',
  [InspectionResult.CHASSIS_SUSPENSION_PASSED]: '/pregled/elektro',
  [InspectionResult.ELECTRICAL_SYSTEM_PASSED]: '/pregled/kocnice-test',
  [InspectionResult.BRAKE_TEST_PASSED]: '/pregled/svetla',
  [InspectionResult.LIGHTING_SYSTEM_PASSED]: '/pregled/oprema',
  [InspectionResult.MANDATORY_EQUIPMENT_PASSED]: '/pregled/zakljucak',
};

@Injectable({ providedIn: 'root' })
export class InspectionFlowService {
  private readonly router = inject(Router);
  private readonly session = inject(InspectionSessionService);
  private readonly dialogService = inject(DialogService);

  handleSuccess(response: InspectionResponseDto, onContinue?: () => void): void {
    this.session.updateFromResponse(response);

    const title = response.resultLabel ?? 'Rezultat';
    const body = response.primaryReason
      ? `${title}\n\n${response.primaryReason}`
      : title;

    const systemsText = this.formatSystems(response);
    const fullBody = systemsText ? `${body}\n\n${systemsText}` : body;

    if (this.session.isTerminal(response.result)) {
      this.dialogService.open({
        title: 'Završetak pregleda',
        message: fullBody,
        variant: response.result === InspectionResult.PROSAO ? 'success' : 'error',
        confirmLabel: 'Pogledaj zaključak',
        onConfirm: () => {
          this.router.navigate(['/pregled/zakljucak']);
          onContinue?.();
        },
      });
      return;
    }

    const next = NEXT_ROUTE[response.result];
    this.dialogService.open({
      title: 'Uspešno',
      message: fullBody,
      variant: 'success',
      confirmLabel: next ? 'Nastavi' : 'U redu',
      onConfirm: () => {
        if (next) {
          this.router.navigate([next]);
        }
        onContinue?.();
      },
    });
  }

  private formatSystems(response: InspectionResponseDto): string {
    if (!response.systems) {
      return '';
    }
    return Object.values(response.systems)
      .map((s) => {
        const status = s.passed ? 'PROŠLO' : 'NIJE PROŠLO';
        const reasons = s.failureReasons?.length
          ? `\n  - ${s.failureReasons.join('\n  - ')}`
          : '';
        return `${s.systemName}: ${status}${reasons}`;
      })
      .join('\n');
  }
}
