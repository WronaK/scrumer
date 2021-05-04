import {ErrorStateMatcher} from "@angular/material/core";
import {FormControl, FormGroupDirective, NgForm} from "@angular/forms";


export class FormGroupErrorMatcher implements ErrorStateMatcher {
  errorName: string;

  constructor(errorName: string) {
    this.errorName = errorName;
  }

  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    return control?.parent?.hasError(this.errorName) || control?.invalid || !!form?.errors;
  }



}
