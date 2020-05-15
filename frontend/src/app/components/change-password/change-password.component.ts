import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UserService } from '../../services/user.service';
import { ErrorMessageComponent } from '../error-message/error-message.component';
import { mustMatch } from '../../global/must-match-validator';
import { UserInfoDTO } from '../../../generated';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'tl-change-password',
  templateUrl: './change-password.component.html'
})
export class ChangePasswordComponent implements OnInit {

  constructor(private formBuilder: FormBuilder, private userService: UserService,
    public dialogRef: MatDialogRef<ChangePasswordComponent>,
    @Inject(MAT_DIALOG_DATA) public data: UserInfoDTO) {
    this.user = data;
  }

  user: UserInfoDTO;
  passwordForm: FormGroup;
  submitted: boolean = false;

  @ViewChild(ErrorMessageComponent)
  private errorMessageComponent: ErrorMessageComponent;

  ngOnInit(): void {
    this.passwordForm = this.formBuilder.group({
      password: ['', [Validators.required, Validators.minLength(8)]],
      passwordRepeat: ['', [Validators.required, Validators.minLength(8)]]
    }, { validator: mustMatch('password', 'passwordRepeat') });
  }

  changePassword(): void {
    this.submitted = true;
    if (this.passwordForm.valid) {
      const userId: number = this.user.id;
      const password: string = this.passwordForm.get('password').value;
      this.userService.changePassword(userId, password).subscribe(
        () => {
          console.log('Successfully changed password');
          this.dialogRef.close(true);
        },
        error => this.errorMessageComponent.defaultServiceErrorHandling(error));
    }
  }

}
