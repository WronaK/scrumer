import {FormControl, ValidationErrors} from "@angular/forms";

export class PasswordValidator {
  static isEqual =
    (firstValue: FormControl, secondValue: FormControl) => (): ValidationErrors | null => {
      if (firstValue.value != secondValue.value) {
        return {isEqual: true};
      }
      return null;
    }
}
