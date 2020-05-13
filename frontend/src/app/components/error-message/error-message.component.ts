import {Component, OnInit} from '@angular/core';
import { ErrorDTO, ErrorType } from '../../../generated';

@Component({
  selector: 'tl-error-message',
  templateUrl: './error-message.component.html'
})
export class ErrorMessageComponent implements OnInit {

  error: ErrorDTO = null;

  constructor() { }

  ngOnInit(): void {
  }

  vanishError(): void {
    this.error = null;
  }

  defaultServiceErrorHandling(error: any): void {
    console.log(error);
    if (typeof error.error === 'object') {
      this.error = error.error;
    } else {
      this.error = {
        message: 'Unerwartete Errorantwort: ' + error.error,
        type: ErrorType.FATAL
      };
    }
  }

  throwCustomError(errorMessage: string, errorType: ErrorType): void {
    this.error = {
      message: errorMessage,
      type: errorType
    };
  }

}
