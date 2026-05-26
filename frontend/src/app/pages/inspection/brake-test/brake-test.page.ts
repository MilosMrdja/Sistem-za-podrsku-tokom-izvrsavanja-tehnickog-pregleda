import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { InspectionResult } from '../../../models/enums/inspection-result.enum';
import { InspectionApiService } from '../../../services/api/inspection-api.service';
import { InspectionFlowService } from '../../../services/inspection-flow.service';
import { InspectionSessionService } from '../../../services/inspection-session.service';
import { BrakeTestWebSocketService } from '../../../services/websocket/brake-test-websocket.service';
import { InspectionPageBase } from '../shared/inspection-page.base';

@Component({ standalone: false, selector: 'app-brake-test-page', templateUrl: './brake-test.page.html' })
export class BrakeTestPage extends InspectionPageBase implements OnInit, OnDestroy {
  loading = false;
  started = false;
  statusMessage = 'Test kocnica jos nije pokrenut.';
  private sub?: Subscription;

  constructor(
    private readonly api: InspectionApiService,
    private readonly flow: InspectionFlowService,
    private readonly ws: BrakeTestWebSocketService,
    session: InspectionSessionService,
  ) {
    super(session);
  }

  ngOnInit(): void {
    const inspectionId = this.session.requireSession().inspectionId;
    this.sub = this.ws.connect(inspectionId).subscribe((payload) => {
      if (
        payload.result === InspectionResult.BRAKE_TEST_PASSED ||
        payload.result === InspectionResult.NIJE_PROSAO
      ) {
        this.loading = false;
        this.session.updateFromResponse(payload);
        this.flow.handleSuccess(payload);
      }
    });
  }

  startTest(): void {
    if (this.loading || this.started) {
      return;
    }
    const inspectionId = this.session.requireSession().inspectionId;
    this.loading = true;
    this.statusMessage = 'Pokretanje testa kocnica...';
    this.api.startBrakeTest(inspectionId).subscribe({
      next: () => {
        this.started = true;
        this.statusMessage =
          'Test kocnica je u toku. Cekamo rezultate sa simulatora (oko 10 sekundi)...';
      },
      error: () => {
        this.loading = false;
      },
    });
  }

  ngOnDestroy(): void {
    this.sub?.unsubscribe();
    this.ws.disconnect();
  }
}
