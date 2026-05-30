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

    if (this.session.isTerminal(response.result)) {
      this.dialogService.open({
        title: 'Završetak pregleda',
        message: this.formatTerminalMessage(response),
        variant: response.result === InspectionResult.PROSAO ? 'success' : 'error',
        confirmLabel: 'U redu',
        onConfirm: () => {
          this.router.navigate(['/pregled/zakljucak']);
          onContinue?.();
        },
      });
      return;
    }

    const next = NEXT_ROUTE[response.result];
    this.dialogService.open({
      title: 'Uspešno izvršena operacija',
      message: response.primaryReason ?? response.resultLabel ?? 'Provera je uspešno završena.',
      variant: 'success',
      confirmLabel: 'U redu',
      onConfirm: () => {
        if (next) {
          this.router.navigate([next]);
        }
        onContinue?.();
      },
    });
  }

  private formatTerminalMessage(response: InspectionResponseDto): string {
    if (response.result === InspectionResult.PROSAO) {
      return response.primaryReason ?? response.resultLabel ?? 'Tehnički pregled je uspešno završen.';
    }

    const failedReasons = this.getFailedReasons(response);
    if (!failedReasons.length) {
      return (
        response.primaryReason ??
        'Ustanovljeni su nedostaci koji onemogućavaju prolazak tehničkog pregleda.'
      );
    }

    return [
      'Ustanovljeni su nedostaci koji onemogućavaju prolazak tehničkog pregleda:',
      '',
      ...failedReasons.map((reason) => `- ${reason}`),
    ].join('\n');
  }

  private getFailedReasons(response: InspectionResponseDto): string[] {
    if (!response.systems) {
      return [];
    }

    return Object.values(response.systems)
      .filter((system) => !system.passed)
      .flatMap((system) => {
        if (system.failureReasons?.length) {
          return system.failureReasons;
        }
        return [`${system.systemName} nije prošao proveru.`];
      });
  }
}
