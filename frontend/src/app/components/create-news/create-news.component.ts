import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ErrorMessageComponent } from '../error-message/error-message.component';
import {AuthService} from '../../services/auth.service';
import {NewsApiService, NewsDTO, UserDTO} from '../../../generated';
import {UserService} from '../../services/user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'tl-create-news',
  templateUrl: './create-news.component.html',
  styleUrls: ['./create-news.component.css']
})
export class CreateNewsComponent implements OnInit {

  userName: string;
  submitted: boolean = false;
  newsForm: FormGroup;

  constructor(private formBuilder: FormBuilder, private newsApiService: NewsApiService,
              private authService: AuthService, private userService: UserService,
              private router: Router) { }

  @ViewChild(ErrorMessageComponent)
  private errorMessageComponent: ErrorMessageComponent;

  ngOnInit(): void {
    this.newsForm = this.formBuilder.group({
      title: ['', Validators.required],
      summary: ['', Validators.required],
      content: ['', Validators.required],
      author: ['', Validators.required]
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

  submitAndRedirect() {
    this.createNews();
    this.router.navigate(['/news']);
  }

  /** Sends news creation request
   *
   * @param newsDTO the news which should be created
   */
  createNews() {
    this.submitted = true;
    if (this.newsForm.valid) {
      const newsDTO: NewsDTO = Object.assign({}, this.newsForm.value);
      newsDTO.author = this.userName;
      newsDTO.publishedAt = new Date();
      this.newsApiService.createNews(newsDTO).subscribe(
        () => {
          console.log('News successfully created');
          alert('Erfolgreich gespeichert.');
        },
        error => () => {
          this.errorMessageComponent.defaultServiceErrorHandling(error);
          alert('Fehler beim speichern:' + error.message);
        }
      );
    }
  }
}
