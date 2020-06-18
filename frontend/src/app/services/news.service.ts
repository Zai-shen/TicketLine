import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import {NewsApiService, NewsDTO} from '../../generated';

@Injectable({
  providedIn: 'root'
})
export class NewsService {

  constructor(private newsApiService: NewsApiService) { }

  getNews(id: number): Observable<NewsDTO> {
    return this.newsApiService.getNews(id);
  }

  getNewsList(page: number): Observable<HttpResponse<Array<NewsDTO>>> {
    return this.newsApiService.getNewsList(0, page, 'response');
  }

  createNews(news: NewsDTO): Observable<any> {
    return this.newsApiService.createNews(news);
  }
}
