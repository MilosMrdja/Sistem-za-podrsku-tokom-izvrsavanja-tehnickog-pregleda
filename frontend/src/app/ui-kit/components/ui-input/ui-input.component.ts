import { Component, Input, Optional, Self } from '@angular/core';
import { ControlValueAccessor, NgControl } from '@angular/forms';
import { getValidationErrorMessage } from '../../../shared/form-validators';

@Component({
  standalone: false,
  selector: 'app-ui-input',
  templateUrl: './ui-input.component.html',
  styleUrl: './ui-input.component.scss',
})
export class UiInputComponent implements ControlValueAccessor {
  @Input() label = '';
  @Input() type: 'text' | 'number' | 'date' = 'text';
  @Input() placeholder = '';
  @Input() required = false;
  @Input() hint = '';
  @Input() maxLength: number | null = null;

  value: string | number | null = null;
  disabled = false;

  private onChange: (v: string | number | null) => void = () => {};
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

  writeValue(value: string | number | null): void {
    this.value = value;
  }

  registerOnChange(fn: (v: string | number | null) => void): void {
    this.onChange = fn;
  }

  registerOnTouched(fn: () => void): void {
    this.onTouched = fn;
  }

  setDisabledState(isDisabled: boolean): void {
    this.disabled = isDisabled;
  }

  onInput(event: Event): void {
    const raw = (event.target as HTMLInputElement).value;
    const next = this.type === 'number' ? (raw === '' ? null : Number(raw)) : raw;
    this.value = next;
    this.onChange(next);
  }

  onBlur(): void {
    this.onTouched();
  }
}
