import { Injectable, NgZone, OnDestroy } from '@angular/core';
import { Client, IMessage } from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import { Observable, Subject } from 'rxjs';
import { environment } from '../../../environments/environment';
import { InspectionResponseDto } from '../../dto/response/inspection-response.dto';

@Injectable({ providedIn: 'root' })
export class BrakeTestWebSocketService implements OnDestroy {
  private client: Client | null = null;
  private readonly messages$ = new Subject<InspectionResponseDto>();

  connect(inspectionId: number): Observable<InspectionResponseDto> {
    this.disconnect();

    this.client = new Client({
      webSocketFactory: () => new SockJS(environment.wsUrl) as WebSocket,
      reconnectDelay: 3000,
      onConnect: () => {
        this.client?.subscribe(
          `/topic/inspection/${inspectionId}/brake-test`,
          (message: IMessage) => {
            const payload = JSON.parse(message.body) as InspectionResponseDto;
            this.zone.run(() => this.messages$.next(payload));
          },
        );
      },
    });

    this.client.activate();
    return this.messages$.asObservable();
  }

  disconnect(): void {
    if (this.client?.active) {
      this.client.deactivate();
    }
    this.client = null;
  }

  ngOnDestroy(): void {
    this.disconnect();
  }

  constructor(private readonly zone: NgZone) {}
}
