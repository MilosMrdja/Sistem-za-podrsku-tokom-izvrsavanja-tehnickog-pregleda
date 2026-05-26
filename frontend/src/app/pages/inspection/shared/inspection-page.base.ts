import { InspectionSessionService } from '../../../services/inspection-session.service';

export abstract class InspectionPageBase {
  constructor(protected readonly session: InspectionSessionService) {}

  get sessionSubtitle(): string {
    const s = this.session.session;
    return s ? `Pregled #${s.inspectionId} · ${s.registrationPlate}` : '';
  }
}
