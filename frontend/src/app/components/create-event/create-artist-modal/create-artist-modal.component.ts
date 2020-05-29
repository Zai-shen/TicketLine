import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { ArtistApiService } from '../../../../generated';
import { ErrorMessageComponent } from '../../error-message/error-message.component';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'tl-create-performance-modal',
  templateUrl: './create-artist-modal.component.html',
  styleUrls: ['./create-artist-modal.component.scss']
})
export class CreateArtistModalComponent implements OnInit {

  @ViewChild(ErrorMessageComponent)
  private errorMessageComponent: ErrorMessageComponent;

  artistForm: FormGroup;
  submitted = false;

  constructor(private readonly formBuilder: FormBuilder,
    private readonly dialogRef: MatDialogRef<CreateArtistModalComponent>,
    private artistService: ArtistApiService,
    private snackBar: MatSnackBar) {
  }

  ngOnInit(): void {
    this.artistForm = new FormGroup({
      firstname: new FormControl(null, [
        Validators.required
      ]),
      lastname: new FormControl(null, [
        Validators.required
      ])
    });
  }

  createArtist() {
    console.log(this.artistForm.value)
    this.submitted = true;
    if (this.artistForm.valid) {
      this.artistService.createArtist(this.artistForm.value).subscribe(
        (_success: any) => {
          this.snackBar.open('Künstler gespeichert', 'OK');
          this.dialogRef.close();
        },
        (error: any) => {
          if (error.status === 409) {
            this.snackBar.open('Künstler existiert bereits', 'OK');
          } else {
            this.errorMessageComponent.defaultServiceErrorHandling(error);
          }
        }
      );
    }
  }
}
