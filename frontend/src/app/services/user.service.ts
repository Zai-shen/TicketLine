import { Injectable } from '@angular/core';
import { LoginDTO, UserApiService, UserDTO, UserInfoDTO } from '../../generated';
import { Observable } from 'rxjs';
import { HttpResponse } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private userApiService: UserApiService) { }

  register(user: UserDTO): Observable<void> {
    return this.userApiService.register(user);
  }

  changePassword(userId: number, password: string): Observable<any> {
    const loginDTO: LoginDTO = {email: 'dummy@example.com', password: password};
    return this.userApiService.resetPassword(userId, loginDTO);
  }

  getUsers(locked: boolean, email: string, page: number): Observable<HttpResponse<Array<UserInfoDTO>>> {
    return this.userApiService.getUsers(locked, email, page, 'response');
  }

  unlockUser(userId: number): Observable<any> {
    return this.userApiService.unlockUser(userId);
  }
}
