import {
  AbstractControl,
  FormGroup,
  ValidationErrors,
  ValidatorFn,
  Validators,
} from '@angular/forms';

/** String mora imati više od 2 karaktera (min. 3) */
export const MIN_STRING_LENGTH = 3;

/** VIN: tačno 18 alfanumeričkih karaktera */
export const VIN_PATTERN = /^[A-Za-z0-9]{17}$/;

/** Registarska oznaka: 2 slova + 3–5 cifara + 2 slova */
export const REGISTRATION_PLATE_PATTERN = /^[A-Za-z]{2}\d{3,5}[A-Za-z]{2}$/i;

export function requiredStringValidators(): ValidatorFn[] {
  return [Validators.required, Validators.minLength(MIN_STRING_LENGTH)];
}

export function vinFormatValidator(): ValidatorFn {
  return (control: AbstractControl): ValidationErrors | null => {
    const value = control.value;
    if (value == null || value === '') {
      return null;
    }
    return VIN_PATTERN.test(String(value).trim()) ? null : { invalidVin: true };
  };
}

export function registrationPlateFormatValidator(): ValidatorFn {
  return (control: AbstractControl): ValidationErrors | null => {
    const value = control.value;
    if (value == null || value === '') {
      return null;
    }
    return REGISTRATION_PLATE_PATTERN.test(String(value).trim())
      ? null
      : { invalidRegistrationPlate: true };
  };
}

export function requiredVinValidators(): ValidatorFn[] {
  return [Validators.required, vinFormatValidator()];
}

export function requiredRegistrationPlateValidators(): ValidatorFn[] {
  return [Validators.required, registrationPlateFormatValidator()];
}

/** Broj mora biti strogo veći od 0 */
export function requiredPositiveNumberValidators(): ValidatorFn[] {
  return [Validators.required, Validators.min(0.0001)];
}

/** Broj može biti 0 ili veći (npr. brojači, opciona svetla) */
export function requiredNonNegativeNumberValidators(): ValidatorFn[] {
  return [Validators.required, Validators.min(0)];
}

export function requiredPercentageValidators(): ValidatorFn[] {
  return [Validators.required, percentageRangeValidator()];
}

export function percentageRangeValidator(): ValidatorFn {
  return (control: AbstractControl): ValidationErrors | null => {
    const value = control.value;
    if (value == null || value === '') {
      return null;
    }
    const numericValue = Number(value);
    return numericValue >= 1 && numericValue <= 100
      ? null
      : { percentageRange: true };
  };
}

export function dateNotBefore(
  beforeControlName: string,
  errorKey: string,
): ValidatorFn {
  return (control: AbstractControl): ValidationErrors | null => {
    const group = control.parent;
    if (!group) {
      return null;
    }
    const before = group.get(beforeControlName)?.value;
    const current = control.value;
    if (!before || !current) {
      return null;
    }
    if (new Date(String(current)) < new Date(String(before))) {
      return { [errorKey]: true };
    }
    return null;
  };
}

export function linkDateRevalidation(
  form: FormGroup,
  source: string,
  targets: string[],
): void {
  form.get(source)?.valueChanges.subscribe(() => {
    for (const name of targets) {
      form.get(name)?.updateValueAndValidity({ emitEvent: false });
    }
  });
}

export function touchInvalidForm(form: FormGroup): boolean {
  if (form.invalid) {
    form.markAllAsTouched();
    return false;
  }
  return true;
}

export function getValidationErrorMessage(
  errors: ValidationErrors | null | undefined,
): string {
  if (!errors) {
    return '';
  }
  if (errors['required']) {
    return 'Polje je obavezno.';
  }
  if (errors['minlength']) {
    const req = errors['minlength'].requiredLength;
    return `Unesite najmanje ${req} karaktera.`;
  }
  if (errors['min']) {
    return 'Vrednost mora biti veća od 0.';
  }
  if (errors['percentageRange']) {
    return 'Procenat mora biti između 1 i 100.';
  }
  if (errors['invalidVin']) {
    return 'VIN mora imati tačno 17 slova i brojeva.';
  }
  if (errors['invalidRegistrationPlate']) {
    return 'Format: 2 slova, 3–5 cifara, 2 slova (npr. BG123AB).';
  }
  if (errors['dateBeforeProduction']) {
    return 'Datum prve registracije ne može biti pre datuma proizvodnje.';
  }
  if (errors['dateBeforeFirstRegistration']) {
    return 'Datum prve registracije u Srbiji ne može biti pre datuma prve registracije.';
  }
  return 'Neispravna vrednost.';
}
