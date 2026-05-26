import { Component, Input, Optional, Self } from '@angular/core';
import { ControlValueAccessor, NgControl } from '@angular/forms';
import { getValidationErrorMessage } from '../../../shared/form-validators';

export interface UiSelectOption<T = string> {
  label: string;
  value: T;
}

@Component({
  standalone: false,
  selector: 'app-ui-dropdown',
  templateUrl: './ui-dropdown.component.html',
  styleUrl: './ui-dropdown.component.scss',
})
export class UiDropdownComponent implements ControlValueAccessor {
  @Input() label = '';
  @Input() options: UiSelectOption[] = [];
  @Input() required = false;
  @Input() placeholder = '-- Izaberite --';
  @Input() disabledPlaceholder = 'Prvo izaberite marku';

  value: string | null = null;
  disabled = false;

  private onChange: (v: string | null) => void = () => {};
  private onTouched: () => void = () => {};

  constructor(@Optional() @Self() public ngControl: NgControl) {
    if (this.ngControl) {
      this.ngControl.valueAccessor = this;
    }
  }

  get showError(): boolean {
    return !!this.ngControl?.invalid && !!this.ngControl?.touched;
  }

  get errorMessage(): string {
    return getValidationErrorMessage(this.ngControl?.errors);
  }

  get emptyLabel(): string {
    return this.disabled ? this.disabledPlaceholder : this.placeholder;
  }

  writeValue(value: string | null): void {
    this.value = value;
  }

  registerOnChange(fn: (v: string | null) => void): void {
    this.onChange = fn;
  }

  registerOnTouched(fn: () => void): void {
    this.onTouched = fn;
  }

  setDisabledState(isDisabled: boolean): void {
    this.disabled = isDisabled;
  }

  onSelect(event: Event): void {
    const raw = (event.target as HTMLSelectElement).value;
    this.value = raw || null;
    this.onChange(this.value);
    this.onTouched();
  }

  onBlur(): void {
    this.onTouched();
  }
}
