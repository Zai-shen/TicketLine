import { Injectable } from '@angular/core';
import { LoginDTO, UserApiService, UserDTO, UserInfoDTO } from '../../generated';
import { Observable } from 'rxjs';

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

  getUsers(locked: boolean, page: number): Observable<UserInfoDTO[]> {
    return this.userApiService.getUsers(locked, page);
  }
}
