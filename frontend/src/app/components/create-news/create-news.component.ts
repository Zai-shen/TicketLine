import { Component, ElementRef, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ErrorMessageComponent } from '../error-message/error-message.component';
import { AuthService } from '../../services/auth.service';
import { NewsDTO, UserDTO } from '../../../generated';
import { UserService } from '../../services/user.service';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Globals } from '../../global/globals';
import { NewsService } from '../../services/news.service';

@Component({
  selector: 'tl-create-news',
  templateUrl: './create-news.component.html',
  styleUrls: ['./create-news.component.css']
})
export class CreateNewsComponent implements OnInit {

  userName: string;
  submitted: boolean = false;
  newsForm: FormGroup;
  private base64PictureString: string;

  constructor(private formBuilder: FormBuilder, private newsService: NewsService,
              private authService: AuthService, private userService: UserService,
              private router: Router, private snackBar: MatSnackBar,
              private globals: Globals) { }

  @ViewChild('pictureFile') pictureFile: ElementRef;
  @ViewChild(ErrorMessageComponent)
  private errorMessageComponent: ErrorMessageComponent;

  ngOnInit(): void {
    this.newsForm = this.formBuilder.group({
      title: ['', [Validators.required]],
      summary: ['', [Validators.required]],
      content: ['', [Validators.required]],
      author: ['', [Validators.required]]
    });
    this.getCurrentUsersName();
  }

  getCurrentUsersName(): void {
    this.userService.getSelf().subscribe(
      (user: UserDTO) => {
        this.userName = user.firstname + ' ' + user.lastname;
        this.newsForm.controls['author'].setValue(this.userName);
        this.newsForm.controls['author'].disable();
      },
      error => {
        this.errorMessageComponent.defaultServiceErrorHandling(error);
      }
    );
  }

  submitAndRedirect(): void {
    this.createNews();
    this.router.navigate(['/news']);
  }

  createNews(): void {
    this.submitted = true;
    if (this.newsForm.valid) {
      const newsDTO: NewsDTO = Object.assign({}, this.newsForm.value);
      newsDTO.author = this.userName;
      newsDTO.publishedAt = new Date().toISOString();
      this.newsService.createNews(newsDTO).subscribe(
        newsId => {
          this.submitted = false;
          this.newsForm.reset();
          this.newsForm.controls['author'].setValue(this.userName);
          this.newsForm.controls['author'].disable();
          this.snackBar.open('Daten erfolgreich gespeichert.', 'OK', {
            duration: this.globals.defaultSnackbarDuration,
          });
          this.uploadPicture(newsId);
        },
        error => this.errorMessageComponent.defaultServiceErrorHandling(error)
      );
    }
  }

  uploadPicture(newsId: number): void {
    if (this.base64PictureString !== undefined && this.base64PictureString !== null) {
      this.newsService.uploadPictureForNewsWithId(newsId, this.base64PictureString).subscribe(
        (_success: any) => {
          this.pictureFile.nativeElement.value = null;
          setTimeout(() => {
            this.snackBar.open('Bild erfolgreich gespeichert', 'OK');
          }, 1000);
        },
        error => {
          this.errorMessageComponent.defaultServiceErrorHandling(error);
        }
      );
    }
  }

  onUploadChange(eVent: any) {
    const file = eVent.target.files[0];

    if (file) {
      const reader = new FileReader();
      reader.onload = this.handleReaderLoaded.bind(this);
      reader.readAsBinaryString(file);
    }
  }

  handleReaderLoaded(readerEvent: any) {
    this.base64PictureString = 'data:image/png;base64,' + (btoa(readerEvent.target.result));
  }
}
