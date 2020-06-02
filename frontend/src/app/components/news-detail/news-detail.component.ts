import { Component, OnInit } from '@angular/core';
import { NewsApiService, NewsDTO } from '../../../generated';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'tl-home',
  templateUrl: './news-detail.component.html',
  styleUrls: ['./news-detail.component.css']
})
export class NewsDetailComponent implements OnInit {

  news: NewsDTO;
  public errorMsg?: string;

  constructor(private newsService: NewsApiService, private authService: AuthService,
    private route: ActivatedRoute, private router: Router,
    private snackBar: MatSnackBar) {
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.newsService.getNews(+params['id']).subscribe(
        (news: NewsDTO) => {
          this.news = news;
        },
        error => {
          if (error.status === 404) {
            this.errorMsg = 'Das angeforderte Event konnte nicht gefunden werden';
          }
        }
      );
    });
  }

  convertToString(theDate: Date): string {
    const theConverted = new Date(theDate);
    return theConverted.toDateString() + ' um ' + theConverted.getHours() + ':' + theConverted.getMinutes()
      + ':' + theConverted.getSeconds();
  }

  isUserLoggedIn(): boolean {
    return this.authService.isLoggedIn();
  }
}
