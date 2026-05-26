import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { InspectionSessionService } from '../../../services/inspection-session.service';

export const inspectionSessionGuard: CanActivateFn = () => {
  const session = inject(InspectionSessionService);
  const router = inject(Router);
  if (session.inspectionId) {
    return true;
  }
  return router.createUrlTree(['/pregled/vozilo']);
};
