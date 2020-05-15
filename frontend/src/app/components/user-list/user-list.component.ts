import { Component, OnInit, ViewChild } from '@angular/core';
import { UserInfoDTO } from '../../../generated';
import { UserService } from '../../services/user.service';
import { ErrorMessageComponent } from '../error-message/error-message.component';
import { MatDialog } from '@angular/material/dialog';
import { ChangePasswordComponent } from '../change-password/change-password.component';
import { MatButtonToggleChange } from '@angular/material/button-toggle';

@Component({
  selector: 'tl-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {

  users: UserInfoDTO[] = [];
  private showOnlyLockedUsers: boolean;

  @ViewChild(ErrorMessageComponent)
  private errorMessageComponent: ErrorMessageComponent;

  constructor(private userService: UserService, private matDialog: MatDialog) {
  }

  ngOnInit(): void {
    this.loadUsers(false, 0);
  }

  onLockedToggleChanged(event: MatButtonToggleChange) {
    this.showOnlyLockedUsers = event.value === 'true';
    this.reload();
  }

  openResetPasswordDialog(user: UserInfoDTO) {
    const openDialog = this.matDialog.open(ChangePasswordComponent, { width: '60%',
      data: user });
    openDialog.afterClosed().subscribe(hasChanged => {
      if (hasChanged) {
        this.reload();
      }
    });
  }

  private reload(): void {
    this.loadUsers(this.showOnlyLockedUsers, 0);
  }

  private loadUsers(locked: boolean, page: number): void {
    this.userService.getUsers(locked, page)
        .subscribe(users => this.users = users,
          error => this.errorMessageComponent.defaultServiceErrorHandling(error));
  }

}
