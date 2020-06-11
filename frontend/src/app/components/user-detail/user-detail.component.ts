import { Component, OnInit, ViewChild } from '@angular/core';
import { UserDTO, UserUpdateDTO } from '../../../generated';
import { UserService } from '../../services/user.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Globals } from '../../global/globals';
import { AuthService } from '../../services/auth.service';
import { ErrorMessageComponent } from '../error-message/error-message.component';

@Component({
  selector: 'tl-home',
  templateUrl: './user-detail.component.html',
  styleUrls: ['./user-detail.component.scss']
})
export class UserDetailComponent implements OnInit {

  public user: UserDTO;

  public errorMsg?: string;

  @ViewChild(ErrorMessageComponent)
  private errorMessageComponent: ErrorMessageComponent;

  constructor(private userService: UserService, private snackBar: MatSnackBar, private globals: Globals,
    private authService: AuthService) {
  }

  ngOnInit(): void {
    this.getCurrentUser();
  }

  saveUser(): void {
    const updateDTO: UserUpdateDTO = {
      firstname: this.user.firstname,
      lastname: this.user.lastname,
      address: this.user.address
    };
    if (this.user.id !== undefined) {
      this.userService.updateUser(this.user.id, updateDTO).subscribe(
        (_success: any) => {
          this.snackBar.open('Daten Gespeichert', 'OK', {
            duration: this.globals.defaultSnackbarDuration
          });
          this.getCurrentUser();
        },
        error => this.errorMessageComponent.defaultServiceErrorHandling(error)
      );
    }
  }

  getCurrentUser(): void {
    this.userService.getSelf().subscribe(
      (user: UserDTO) => {
        this.user = user;
      },
      error => this.errorMessageComponent.defaultServiceErrorHandling(error)
    );
  }

  openRemoveConfirmDialog(): void {
    this.snackBar.open('Nuteraccount wirklich unwiederbringlich löschen?', 'OK',
      { duration: this.globals.defaultSnackbarDuration })
        .onAction().subscribe(() => {
      this.userService.removeUser().subscribe(() => {
          this.authService.logoutUser();
        }, error => this.errorMessageComponent.defaultServiceErrorHandling(error)
      );
    });
  }

}
