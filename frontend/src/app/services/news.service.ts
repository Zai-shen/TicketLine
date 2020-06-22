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

  getNewsList(inclRead: boolean, page: number): Observable<HttpResponse<Array<NewsDTO>>> {
    return this.newsApiService.getNewsList(inclRead, page, 'response');
  }

  createNews(news: NewsDTO): Observable<number> {
    return this.newsApiService.createNews(news);
  }

   uploadPictureForNewsWithId(newsId: number, base64: string): Observable<string> {
    return this.newsApiService.uploadPictureForNews(newsId, base64);
  }
}
