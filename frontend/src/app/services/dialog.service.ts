import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

export interface DialogState {
  visible: boolean;
  title: string;
  message: string;
  variant: 'success' | 'error' | 'info';
  confirmLabel: string;
  onConfirm?: () => void;
}

const HIDDEN: DialogState = {
  visible: false,
  title: '',
  message: '',
  variant: 'info',
  confirmLabel: 'U redu',
};

@Injectable({ providedIn: 'root' })
export class DialogService {
  private readonly state$ = new BehaviorSubject<DialogState>(HIDDEN);

  readonly dialog$ = this.state$.asObservable();

  open(options: Partial<DialogState> & Pick<DialogState, 'title' | 'message'>): void {
    const variant = options.variant ?? 'info';

    this.state$.next({
      ...HIDDEN,
      ...options,
      visible: true,
      title: this.getFormalTitle(options.title, variant),
      message: options.message,
      variant,
      confirmLabel: options.confirmLabel ?? 'U redu',
    });
  }

  confirm(): void {
    const current = this.state$.value;
    current.onConfirm?.();
    this.close();
  }

  close(): void {
    this.state$.next(HIDDEN);
  }

  private getFormalTitle(title: string, variant: DialogState['variant']): string {
    if (variant === 'success') {
      return title || 'Uspešno izvršena operacija';
    }
    if (variant === 'error') {
      return title || 'Greška';
    }
    return title;
  }
}
