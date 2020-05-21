import { Component, OnInit } from '@angular/core';
import { NewsApiService, NewsDTO } from '../../../generated';

@Component({
  selector: 'tl-news',
  templateUrl: './news.component.html',
  styleUrls: ['./news.component.css']
})
export class NewsComponent implements OnInit {

  constructor(private newsService: NewsApiService) {
  }

  public news: NewsDTO[];

  ngOnInit() {
    this.newsService.getNewsList().subscribe(value => {
      this.news = value;
    });
  }
}
