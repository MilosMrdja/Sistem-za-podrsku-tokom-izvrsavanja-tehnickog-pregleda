import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { UiButtonComponent } from './components/ui-button/ui-button.component';
import { UiInputComponent } from './components/ui-input/ui-input.component';
import { UiCheckboxComponent } from './components/ui-checkbox/ui-checkbox.component';
import { UiDropdownComponent } from './components/ui-dropdown/ui-dropdown.component';
import { UiFormCardComponent } from './components/ui-form-card/ui-form-card.component';
import { UiLoadingComponent } from './components/ui-loading/ui-loading.component';
import { UiDialogComponent } from './components/ui-dialog/ui-dialog.component';

@NgModule({
  declarations: [
    UiButtonComponent,
    UiInputComponent,
    UiCheckboxComponent,
    UiDropdownComponent,
    UiFormCardComponent,
    UiLoadingComponent,
    UiDialogComponent,
  ],
  imports: [CommonModule, FormsModule, ReactiveFormsModule],
  exports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    UiButtonComponent,
    UiInputComponent,
    UiCheckboxComponent,
    UiDropdownComponent,
    UiFormCardComponent,
    UiLoadingComponent,
    UiDialogComponent,
  ],
})
export class UiKitModule {}
