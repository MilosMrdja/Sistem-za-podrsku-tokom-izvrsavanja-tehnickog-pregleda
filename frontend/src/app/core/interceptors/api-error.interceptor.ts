import { HttpErrorResponse, HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { catchError, throwError } from 'rxjs';
import { ApiError } from '../../models/api-error.model';
import { DialogService } from '../../services/dialog.service';

export const apiErrorInterceptor: HttpInterceptorFn = (req, next) => {
  const dialog = inject(DialogService);

  return next(req).pipe(
    catchError((err: HttpErrorResponse) => {
      const apiError = err.error as ApiError | null;
      const message =
        apiError?.message ??
        err.message ??
        'Došlo je do greške prilikom komunikacije sa serverom.';

      dialog.open({
        title: apiError?.error ?? `Greška ${err.status}`,
        message,
        variant: 'error',
        confirmLabel: 'U redu',
      });

      return throwError(() => err);
    }),
  );
};
