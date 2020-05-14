import { Component, OnInit, ViewChild } from '@angular/core';
import { UserInfoDTO } from '../../../generated';
import { UserService } from '../../services/user.service';
import { ErrorMessageComponent } from '../error-message/error-message.component';

@Component({
  selector: 'tl-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {

  users: UserInfoDTO[] = [];

  @ViewChild(ErrorMessageComponent)
  private errorMessageComponent: ErrorMessageComponent;

  constructor(private userService: UserService) { }

  ngOnInit(): void {
    this.loadUsers(false, 0);
  }

  onLockedToggleChanged($event: any) {
    console.log('test');
  }

  private loadUsers(locked: boolean, page: number): void {
    this.userService.getUsers(locked, page)
        .subscribe(users => this.users = users,
          error => this.errorMessageComponent.defaultServiceErrorHandling(error));
  }

}
