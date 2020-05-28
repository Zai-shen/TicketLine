import {Component, OnInit, ViewChild} from '@angular/core';
import {NewsDTO} from '../../../generated';
import {AuthService} from '../../services/auth.service';
import {ErrorMessageComponent} from '../error-message/error-message.component';
import {MatPaginator, PageEvent} from '@angular/material/paginator';
import {NewsService} from '../../services/news.service';

@Component({
  selector: 'tl-news',
  templateUrl: './news.component.html',
  styleUrls: ['./news.component.css']
})
export class NewsComponent implements OnInit {

  readonly NEWS_LIST_PAGE_SIZE = 25;
  news: NewsDTO[];
  newsFound: number;
  amountOfPages = 1;
  private currentPage = 0;

  @ViewChild(ErrorMessageComponent)
  private errorMessageComponent: ErrorMessageComponent;

  @ViewChild(MatPaginator)
  private paginator: MatPaginator;

  constructor(private newsService: NewsService, private authService: AuthService) {
  }

  ngOnInit() {
    this.getAllNews();
  }

  isAdmin(): boolean {
    return this.authService.isAdminLoggedIn();
  }

  onPaginationChange(event: PageEvent): void {
    this.currentPage = event.pageIndex;
    this.getAllNews();
  }

  computeMinutes(date: Date) {
    const eventStartTime = new Date(date);
    const eventEndTime = new Date();
    const duration = eventEndTime.valueOf() - eventStartTime.valueOf();
    const months = Math.floor(duration / 2628000000) % 12;
    const days = Math.floor(duration / 86400000) % 31;
    const hours = Math.floor(duration / 3600000) % 24; // 1 Hour = 36000 Milliseconds
    const minutes = Math.floor((duration % 3600000) / 60000) % 60; // 1 Minute = 60000 Milliseconds
    let time = 'vor ';
    if (minutes <= 2 && hours === 0 && days === 0 && months === 0) {
      time = 'gerade eben';
    } else {
      if (months === 1) {
        time += 'einem' + ' Monat ';
      } else if (months > 1) {
        time += months + ' Monaten ';
      }
      if (days === 1) {
        time += 'einem' + ' Tag ';
      } else if (days > 1) {
        time += days + ' Tagen ';
      }
      if (hours === 1) {
        time += 'einer' + ' Stunde ';
      } else if (hours > 1) {
        time += hours + ' Stunden ';
      }
      if (minutes === 1) {
        time += 'einer' + ' Minute ';
      } else if (minutes > 1) {
        time += minutes + ' Minuten ';
      }
    }
    return time;
  }

  private getAllNews(): void {
    this.newsService.getNewsList(this.currentPage).subscribe(news => {
        if (news.body !== null) {
          this.news = news.body;
          this.amountOfPages = Number(news.headers.get('X-Total-Count')) || 1;
          this.newsFound = Number(news.headers.get('X-Total-Count')) || 0;
          console.log(this.newsFound);
        }
      },
      error => this.errorMessageComponent.defaultServiceErrorHandling(error));
  }
}
