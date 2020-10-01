import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';
import { Operation } from '../models/operation';

export function withdrawalValidator(balance: number): ValidatorFn {
  return (control: AbstractControl): ValidationErrors | null => {
    if (control.value.operation === Operation.WITHDRAWAL.toString() && control.value.amount > balance) {
      return { amountAboveBalanceInvalid: true };
    }

    return null;
  };
}
