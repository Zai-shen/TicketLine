import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import {NewsApiService, NewsDTO} from '../../generated';

@Injectable({
  providedIn: 'root'
})
export class NewsService {

  constructor(private newsApiService: NewsApiService) { }

  getNewsList(page: number): Observable<HttpResponse<Array<NewsDTO>>> {
    return this.newsApiService.getNewsList(0, page, 'response');
  }
}
