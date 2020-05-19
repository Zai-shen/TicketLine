import { Injectable } from '@angular/core';
import { AuthRequest } from '../dtos/auth-request';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { tap } from 'rxjs/operators';
import * as jwt_decode from 'jwt-decode';
import { Globals } from '../global/globals';

enum ROLE {
  User = "USER",
  Admin = "ADMIN",
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  static ROLE = ROLE;

  private authBaseUri: string = this.globals.backendUri + '/login';

  constructor(private httpClient: HttpClient, private globals: Globals) {
  }

  /**
   * Login in the user. If it was successful, a valid JWT token will be stored
   * @param authRequest User data
   */
  loginUser(authRequest: AuthRequest): Observable<string> {
    return this.httpClient.post(this.authBaseUri, authRequest, { responseType: 'text' })
               .pipe(tap((authResponse: string) => this.setToken(authResponse)));
  }


  /**
   * Check if a valid JWT token is saved in the localStorage
   */
  isLoggedIn() {
    const expirationDate = this.getTokenExpirationDate(this.getToken());
    return !!this.getToken() && expirationDate != null && expirationDate > new Date();
  }

  isAdminLoggedIn(): boolean {
    return this.isLoggedIn() && this.getUserRole() === ROLE.Admin;
  }

  logoutUser() {
    console.log('Logout');
    localStorage.removeItem('authToken');
  }

  getToken(): string | null {
    return localStorage.getItem('authToken');
  }

  /**
   * Returns the user role based on the current token
   */
  getUserRole() {
    if (this.getToken() != null) {
      const decoded: any = jwt_decode(this.getToken() || '');
      const authInfo: string[] = decoded.rol;
      if (authInfo.includes('ROLE_ADMIN')) {
        return ROLE.Admin;
      } else if (authInfo.includes('ROLE_USER')) {
        return ROLE.User;
      }
    }
    return 'UNDEFINED';
  }

  private setToken(authResponse: string) {
    localStorage.setItem('authToken', authResponse);
  }

  private getTokenExpirationDate(token: string | null): Date | null {

    const decoded: any = jwt_decode(token || '');
    if (decoded.exp === undefined) {
      return null;
    }

    const date = new Date(0);
    date.setUTCSeconds(decoded.exp);
    return date;
  }

}
