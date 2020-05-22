import {Component, OnInit} from '@angular/core';
import {AuthService} from '../../services/auth.service';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'tl-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  constructor(public authService: AuthService, private router: Router, private _snackBar: MatSnackBar) { }

  ngOnInit() {
  }

  isAdminLoggedIn(): boolean {
    return this.authService.isLoggedIn() && this.authService.getUserRole() === 'ADMIN';
  }

  logout() {
    this.authService.logoutUser();
    this._snackBar.open('Erfolgreich abgemeldet', 'OK', {
      duration: 5 * 1000
    });
    this.router.navigate(['/']);
  }

}

