import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { InspectionResult } from '../../../models/enums/inspection-result.enum';
import { InspectionSessionService } from '../../../services/inspection-session.service';
import { InspectionPageBase } from '../shared/inspection-page.base';

@Component({ standalone: false,
  selector: 'app-summary-page',
  templateUrl: './summary.page.html',
  styleUrl: './summary.page.scss',
})
export class SummaryPage extends InspectionPageBase {
  readonly InspectionResult = InspectionResult;

  constructor(
    session: InspectionSessionService,
    private readonly router: Router,
  ) {
    super(session);
  }

  get s() {
    return this.session.session;
  }

  get passed(): boolean {
    return this.s?.lastResult === InspectionResult.PROSAO;
  }

  get failureReasons(): string[] {
    if (!this.s?.systems) {
      return [];
    }

    return Object.values(this.s.systems)
      .filter((system) => !system.passed)
      .flatMap((system) => {
        if (system.failureReasons?.length) {
          return system.failureReasons;
        }
        return [`${system.systemName} nije prošao proveru.`];
      });
  }

  startNew(): void {
    this.session.clear();
    this.router.navigate(['/pregled/vozilo']);
  }
}
