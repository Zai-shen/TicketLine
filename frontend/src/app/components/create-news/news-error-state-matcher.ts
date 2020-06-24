import { ErrorStateMatcher } from '@angular/material/core';
import { FormControl, FormGroupDirective, NgForm } from '@angular/forms';

export class NewsErrorStateMatcher implements ErrorStateMatcher {

  private submitted: boolean = false;

  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    return (!!control && control.invalid && (control.touched || this.submitted));
  }

  setSubmitted(submitted: boolean): void {
    this.submitted = submitted;
  }
}
