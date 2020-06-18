import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatRadioModule } from '@angular/material/radio';
import { UserInfoDTO } from '../../../../generated';

@Component({
  selector: 'tl-select-role-dialog',
  templateUrl: './select-role-dialog.component.html',
  styleUrls: ['./select-role-dialog.component.css']
})
export class SelectRoleDialogComponent implements OnInit {

  constructor(private formBuilder: FormBuilder, public dialogRef: MatDialogRef<SelectRoleDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: UserInfoDTO) {
    this.user = data;
  }

  roleForm: FormGroup;
  user: UserInfoDTO;

  ngOnInit(): void {
    this.roleForm = this.formBuilder.group({
      role: ['', [Validators.required]],
    });
  }

  changeRole(): void {
    this.dialogRef.close(true);
  }

}
