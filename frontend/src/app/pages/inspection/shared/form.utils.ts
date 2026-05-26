export { touchInvalidForm } from '../../../shared/form-validators';

export function asFormDto<T>(value: object): T {
  return value as T;
}
