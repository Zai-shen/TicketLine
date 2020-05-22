import {Component, OnInit} from '@angular/core';
import { UserApiService, UserDTO } from '../../../generated';
@Component({
  selector: 'tl-home',
  templateUrl: './user-detail.component.html',
  styleUrls: ['./user-detail.component.scss']
})
export class UserDetailComponent implements OnInit {

  public user: UserDTO;

  public errorMsg?: string;

  constructor(private userService: UserApiService) { }

  ngOnInit() {
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
