import { Component, OnInit, ViewChild } from '@angular/core';
import { ErrorType, UserInfoDTO } from '../../../generated';
import { UserService } from '../../services/user.service';
import { ErrorMessageComponent } from '../error-message/error-message.component';
import { MatDialog } from '@angular/material/dialog';
import { ChangePasswordComponent } from '../change-password/change-password.component';
import { MatButtonToggleChange } from '@angular/material/button-toggle';
import { SelectRoleDialogComponent } from './select-role-dialog/select-role-dialog.component';
import { MatPaginator, PageEvent } from '@angular/material/paginator';

@Component({
  selector: 'tl-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {

  readonly USER_LIST_PAGE_SIZE = 25;

  users: UserInfoDTO[] = [];
  searchEmail: string;
  totalAmountOfUsers = 1;
  private showOnlyLockedUsers: boolean;
  private currentPage = 0;

  @ViewChild(ErrorMessageComponent)
  private errorMessageComponent: ErrorMessageComponent;

  @ViewChild(MatPaginator)
  private paginator: MatPaginator;

  constructor(private userService: UserService, private matDialog: MatDialog) {
  }

  ngOnInit(): void {
    this.reload();
  }

  onLockedToggleChanged(event: MatButtonToggleChange): void {
    this.showOnlyLockedUsers = event.value === 'true';
    this.paginator.firstPage();
    this.reload();
  }

  onSearchEmailChange(): void {
    this.paginator.firstPage();
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

  lockUser(id: number): void {
    this.userService.lockUser(id)
      .subscribe(() => {
        this.reload();
      },
      error => this.errorMessageComponent.defaultServiceErrorHandling(error));
  }

  unlockUser(id: number): void {
    this.userService.unlockUser(id)
      .subscribe(() => {
        this.reload();
      },
      error => this.errorMessageComponent.defaultServiceErrorHandling(error));
  }

  private reload(): void {
    this.userService.getUsers(this.showOnlyLockedUsers, this.searchEmail, this.currentPage)
        .subscribe(users => {
            if (users.body !== null) {
              this.users = users.body;
              this.totalAmountOfUsers = Number(users.headers.get('X-Total-Count')) || 0;
            } else {
              this.errorMessageComponent.throwCustomError('Ungültige Antwort vom Server in der Nutzerabfrage',
                ErrorType.FATAL);
            }
          },
          error => this.errorMessageComponent.defaultServiceErrorHandling(error));
  }

  openChangeRoleDialog(user: UserInfoDTO): void {
    const openDialog = this.matDialog.open(SelectRoleDialogComponent, {
      width: '30%',
      data: user
    });
    openDialog.afterClosed().subscribe(hasChanged => {
      if (hasChanged) {
        this.reload();
      }
    });
  }

}
