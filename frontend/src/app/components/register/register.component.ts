import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ErrorMessageComponent } from '../error-message/error-message.component';
import { UserDTO } from '../../../generated';
import { mustMatch } from '../../global/must-match-validator';
import { UserService } from '../../services/user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'tl-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {

  constructor(private formBuilder: FormBuilder, private userService: UserService, private router: Router) {
  }

  registerForm: FormGroup;
  submitted: boolean = false;

  @ViewChild(ErrorMessageComponent)
  private errorMessageComponent: ErrorMessageComponent;

  ngOnInit(): void {
    this.registerForm = this.formBuilder.group({
      firstname: ['', Validators.required],
      lastname: ['', Validators.required],
      login: this.formBuilder.group({
        email: ['', [Validators.required, Validators.email]],
        password: ['', [Validators.required, Validators.minLength(8)]],
        passwordRepeat: ['', [Validators.required, Validators.minLength(8)]]
      }, { validator: mustMatch('password', 'passwordRepeat') }),
      address: this.formBuilder.group({
        street: ['', Validators.required],
        housenr: ['', Validators.required],
        postalcode: ['', Validators.required],
        city: ['', Validators.required],
        country: ['', Validators.required]
      })
    });
  }

  submitRegister(): void {
    this.submitted = true;
    if (this.registerForm.valid) {
      const user: UserDTO = Object.assign({}, this.registerForm.value);
      this.userService.register(user).subscribe(
        () => {
          console.log('Successfully registered user: ' + user.login.email);
          this.router.navigate(['/']);
        },
        error => this.errorMessageComponent.defaultServiceErrorHandling(error));
    }
  }

}
