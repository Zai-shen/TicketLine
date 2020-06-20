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
  totalAmountOfNews = 0;
  alsoShowReadNews = false;
  private currentPage = 0;

  @ViewChild(ErrorMessageComponent)
  private errorMessageComponent: ErrorMessageComponent;

  @ViewChild(MatPaginator)
  private paginator: MatPaginator;

  constructor(private newsService: NewsService, private authService: AuthService) {
  }

  ngOnInit() {
    this.getNewsList();
  }

  isAdmin(): boolean {
    return this.authService.isAdminLoggedIn();
  }

  onPaginationChange(event: PageEvent): void {
    this.currentPage = event.pageIndex;
    this.getNewsList();
  }

  onShowReadNewsToggleSwitch(): void {
    this.paginator.firstPage();
    this.getNewsList();
  }

  private getNewsList(): void {
    this.newsService.getNewsList(this.alsoShowReadNews, this.currentPage).subscribe(news => {
        if (news.body !== null) {
          this.news = news.body;
          this.totalAmountOfNews = Number(news.headers.get('X-Total-Count')) || 0;
        }
      },
      error => this.errorMessageComponent.defaultServiceErrorHandling(error));
  }
}
