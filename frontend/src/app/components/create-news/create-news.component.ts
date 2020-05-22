import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ErrorMessageComponent } from '../error-message/error-message.component';
import {AuthService} from '../../services/auth.service';
import {NewsApiService, NewsDTO} from '../../../generated';

@Component({
  selector: 'tl-create-news',
  templateUrl: './create-news.component.html',
  styleUrls: ['./create-news.component.css']
})
export class CreateNewsComponent implements OnInit {

  submitted: boolean = false;
  newsForm: FormGroup;

  constructor(private formBuilder: FormBuilder, private newsApiService: NewsApiService,
              private authService: AuthService) { }

  @ViewChild(ErrorMessageComponent)
  private errorMessageComponent: ErrorMessageComponent;

  ngOnInit(): void {
    this.newsForm = this.formBuilder.group({
      title: ['', Validators.required],
      summary: ['', Validators.required],
      content: ['', Validators.required],
      author: this.formBuilder.group({
        email: ['', Validators.required],
        firstName: ['', Validators.required],
        lastName: ['', Validators.required]
      })
    });
  }

  /** Sends news creation request
   *
   * @param newsDTO the news which should be created
   */
  createNews() {
    this.submitted = true;
    if (this.newsForm.valid) {
      const newsDTO: NewsDTO = Object.assign({}, this.newsForm.value);
      newsDTO.publishedAt = new Date().toISOString();
      console.log(newsDTO.author);
      console.log(newsDTO.author?.email);
      console.log(newsDTO.author?.firstName);
      console.log(newsDTO.author?.lastName);
      this.newsApiService.createNews(newsDTO).subscribe(
        () => {
          console.log('News successfully created');
        },
        error => this.errorMessageComponent.defaultServiceErrorHandling(error));
    }
  }
}
