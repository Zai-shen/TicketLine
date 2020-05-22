import { Component, OnInit } from '@angular/core';
import { NewsApiService, NewsDTO } from '../../../generated';
import {AuthService} from '../../services/auth.service';

@Component({
  selector: 'tl-news',
  templateUrl: './news.component.html',
  styleUrls: ['./news.component.css']
})
export class NewsComponent implements OnInit {

  constructor(private newsService: NewsApiService, private authService: AuthService) {
  }

  public news: NewsDTO[];

  ngOnInit() {
    this.newsService.getNewsList().subscribe(value => {
      this.news = value;
    });
  }

  isAdmin(): boolean {
    return this.authService.isAdminLoggedIn();
  }
}
