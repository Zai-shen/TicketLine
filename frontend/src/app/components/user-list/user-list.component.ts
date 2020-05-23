import { Component, OnInit, ViewChild } from '@angular/core';
import { ErrorType, UserInfoDTO } from '../../../generated';
import { UserService } from '../../services/user.service';
import { ErrorMessageComponent } from '../error-message/error-message.component';
import { MatDialog } from '@angular/material/dialog';
import { ChangePasswordComponent } from '../change-password/change-password.component';
import { MatButtonToggleChange } from '@angular/material/button-toggle';
import { PageEvent } from '@angular/material/paginator';

@Component({
  selector: 'tl-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {

  readonly USER_LIST_PAGE_SIZE = 25;

  users: UserInfoDTO[] = [];
  searchEmail: string;
  amountOfPages = 1;
  private showOnlyLockedUsers: boolean;
  currentPage = 0;

  @ViewChild(ErrorMessageComponent)
  private errorMessageComponent: ErrorMessageComponent;

  constructor(private userService: UserService, private matDialog: MatDialog) {
  }

  ngOnInit(): void {
    this.reload();
  }

  onLockedToggleChanged(event: MatButtonToggleChange): void {
    this.showOnlyLockedUsers = event.value === 'true';
    this.currentPage = 0;
    this.reload();
  }

  onSearchEmailChange(): void {
    this.currentPage = 0;
    this.reload();
  }

  onPaginationChange(event: PageEvent): void {
    this.currentPage = event.pageIndex;
    this.reload();
  }

  openResetPasswordDialog(user: UserInfoDTO): void {
    const openDialog = this.matDialog.open(ChangePasswordComponent, {
      width: '60%',
      data: user
    });
    openDialog.afterClosed().subscribe(hasChanged => {
      if (hasChanged) {
        this.reload();
      }
    });
  }

  private reload(): void {
    this.userService.getUsers(this.showOnlyLockedUsers, this.searchEmail, this.currentPage)
        .subscribe(users => {
            if (users.body !== null) {
              this.users = users.body;
              this.amountOfPages = Number(users.headers.get('X-Total-Count')) || 1;
            } else {
              this.errorMessageComponent.throwCustomError('Ungültige Antwort vom Server in der Nutzerabfrage',
                ErrorType.FATAL);
            }
          },
          error => this.errorMessageComponent.defaultServiceErrorHandling(error));
  }

}
