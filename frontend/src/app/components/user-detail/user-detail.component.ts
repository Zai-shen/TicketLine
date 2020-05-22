import {Component, OnInit} from '@angular/core';
import { UserApiService, UserDTO, UserUpdateDTO } from '../../../generated';
import { UserService } from '../../services/user.service';
import { MatSnackBar } from '@angular/material/snack-bar';
@Component({
  selector: 'tl-home',
  templateUrl: './user-detail.component.html',
  styleUrls: ['./user-detail.component.scss']
})
export class UserDetailComponent implements OnInit {

  public user: UserDTO;

  public errorMsg?: string;

  constructor(private userService: UserService, private snackBar: MatSnackBar) { }

  ngOnInit() {
    this.getCurrentUser();
  }

  saveUser() {
    const updateDTO: UserUpdateDTO = {firstname: this.user.firstname, lastname: this.user.lastname, address: this.user.address};
    if (this.user.id !== undefined) {
      this.userService.updateUser(this.user.id, updateDTO).subscribe(
        (_success: any) => {
          this.snackBar.open('Daten Gespeichert', 'OK', {
            duration: 5 * 1000,
          });
          this.getCurrentUser();
        },
        error => {
          this.errorMsg = error.message;
        }
      );
    }
  }

  getCurrentUser() {
    this.userService.getSelf().subscribe(
      (user: UserDTO) => {
        this.user = user;
      },
      error => {
        this.errorMsg = 'Error fetching user: ' + error.message;
      }
    );
  }

}
