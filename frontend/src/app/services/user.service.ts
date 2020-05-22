import { Injectable } from '@angular/core';
import { LoginDTO, UserApiService, UserDTO, UserUpdateDTO } from '../../generated';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private userApiService: UserApiService) { }

  register(user: UserDTO): Observable<void> {
    return this.userApiService.register(user);
  }

  changePassword(userId: number, password: string) {
    const loginDTO: LoginDTO = {email: 'dummy@example.com', password: password};
    return this.userApiService.resetPassword(userId, loginDTO);
  }

  updateUser(userId: number, updateDTO: UserUpdateDTO): Observable<void> {
    return this.userApiService.updateUser(userId, updateDTO);
  }

  getSelf(): Observable<UserDTO> {
    return this.userApiService.getSelf();
  }
}
