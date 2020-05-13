import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UserService } from '../../services/user.service';
import { Router } from '@angular/router';
import { ErrorMessageComponent } from '../error-message/error-message.component';
import { mustMatch } from '../../global/must-match-validator';

@Component({
  selector: 'tl-change-password',
  templateUrl: './change-password.component.html'
})
export class ChangePasswordComponent implements OnInit {

  constructor(private formBuilder: FormBuilder, private userService: UserService, private router: Router) { }

  passwordForm: FormGroup;
  submitted: boolean = false;

  @ViewChild(ErrorMessageComponent)
  private errorMessageComponent: ErrorMessageComponent;

  ngOnInit(): void {
    this.passwordForm = this.formBuilder.group({
      userId: ['', [Validators.required]],
      password: ['', [Validators.required, Validators.minLength(8)]],
      passwordRepeat: ['', [Validators.required, Validators.minLength(8)]]
    }, { validator: mustMatch('password', 'passwordRepeat') });
  }

  changePassword(): void {
    this.submitted = true;
    if (this.passwordForm.valid) {
      const userId: number = this.passwordForm.get('userId').value;
      const password: string = this.passwordForm.get('password').value;
      this.userService.changePassword(userId, password).subscribe(
        () => {
          console.log('Successfully changed password');
          this.router.navigate(['/']);
        },
        error => this.errorMessageComponent.defaultServiceErrorHandling(error));
    }
  }

}
