import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatRadioModule } from '@angular/material/radio';
import { ErrorType, UserInfoDTO, UserUpdateDTO } from '../../../../generated';
import { UserService } from '../../../services/user.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Globals } from '../../../global/globals';
import { ErrorMessageComponent } from '../../error-message/error-message.component';

@Component({
  selector: 'tl-select-role-dialog',
  templateUrl: './select-role-dialog.component.html',
  styleUrls: ['./select-role-dialog.component.scss']
})
export class SelectRoleDialogComponent implements OnInit {

  @ViewChild(ErrorMessageComponent)
  private errorMessageComponent: ErrorMessageComponent;

  constructor(private userService: UserService, private formBuilder: FormBuilder,
    public dialogRef: MatDialogRef<SelectRoleDialogComponent>, private snackBar: MatSnackBar, private globals: Globals,
    @Inject(MAT_DIALOG_DATA) public data: UserInfoDTO) {
    this.user = data;
  }

  roleForm: FormGroup;
  user: UserInfoDTO;
  updateDTO: UserUpdateDTO = {};

  ngOnInit(): void {
    this.roleForm = this.formBuilder.group({
      role: ['', [Validators.required]],
    });
  }

  changeRole(): void {
    this.updateDTO.role = this.roleForm.controls.role.value;
    this.userService.updateUser(this.user.id, this.updateDTO).subscribe(
      (_success: any) => {
        this.snackBar.open('Daten Gespeichert', 'OK', {
          duration: this.globals.defaultSnackbarDuration
        });
        this.dialogRef.close(true);
      },
      error => {
        this.dialogRef.close(error);
      }
    );
  }

}
