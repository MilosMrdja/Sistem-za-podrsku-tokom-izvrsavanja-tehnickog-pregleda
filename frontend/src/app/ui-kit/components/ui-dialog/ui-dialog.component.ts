import { Component, inject } from '@angular/core';
import { DialogService } from '../../../services/dialog.service';

@Component({
  standalone: false,
  selector: 'app-ui-dialog',
  templateUrl: './ui-dialog.component.html',
  styleUrl: './ui-dialog.component.scss',
})
export class UiDialogComponent {
  private readonly dialogService = inject(DialogService);
  readonly dialog$ = this.dialogService.dialog$;

  confirm(): void {
    this.dialogService.confirm();
  }
}
