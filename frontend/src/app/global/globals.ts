import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class Globals {
  readonly backendUri: string = 'http://localhost:8080/v1';
  readonly defaultSnackbarDuration: number = 5 * 1000; // 5 seconds
}
