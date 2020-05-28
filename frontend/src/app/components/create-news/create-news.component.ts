import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ErrorMessageComponent } from '../error-message/error-message.component';
import {AuthService} from '../../services/auth.service';
import {NewsApiService, NewsDTO, UserDTO} from '../../../generated';
import {UserService} from '../../services/user.service';

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
              private authService: AuthService, private userService: UserService) { }

  @ViewChild(ErrorMessageComponent)
  private errorMessageComponent: ErrorMessageComponent;

  ngOnInit(): void {
    this.newsForm = this.formBuilder.group({
      title: ['', Validators.required],
      summary: ['', Validators.required],
      content: ['', Validators.required],
      author: [{
        value: '',
        disabled: true},
        Validators.required]
    });
    this.getCurrentUsersName();
  }

  getCurrentUsersName(): void {
    this.userService.getSelf().subscribe(
      (user: UserDTO) => {
        this.userName = user.firstname + ' ' + user.lastname;
        this.newsForm.controls['author'].setValue(this.userName);
      },
      error => {
        this.errorMessageComponent.defaultServiceErrorHandling(error);
      }
    );
  }

  /** Sends news creation request
   *
   * @param newsDTO the news which should be created
   */
  createNews() {
    this.submitted = true;
    if (this.newsForm.valid) {
      const newsDTO: NewsDTO = Object.assign({}, this.newsForm.value);
      newsDTO.publishedAt = new Date();
      this.newsApiService.createNews(newsDTO).subscribe(
        () => {
          console.log('News successfully created');
        },
        error => this.errorMessageComponent.defaultServiceErrorHandling(error));
    }
  }
}
