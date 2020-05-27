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

  computeMinutes(date: Date) {
    const eventStartTime = new Date(date);
    const eventEndTime = new Date();
    const duration = eventEndTime.valueOf() - eventStartTime.valueOf();
    const months = Math.floor(duration / 2628000000) % 12;
    const days = Math.floor(duration / 86400000) % 31;
    const hours = Math.floor(duration / 3600000) % 24; // 1 Hour = 36000 Milliseconds
    const minutes = Math.floor((duration % 3600000) / 60000) % 60; // 1 Minute = 60000 Milliseconds
    // const seconds = Math.floor(((duration % 360000) % 60000) / 1000) % 60; // 1 Second = 1000 Milliseconds
    let time;
    if (minutes <= 2) {
      time = 'gerade eben';
    } else {
      time = 'vor ' + (months > 0 ? months + ' Monaten ' : '') + (days > 0 ? days + ' Tagen ' : '') +
        (hours > 0 ? hours + ' Stunden ' : '') + (minutes > 0 ? minutes + ' Minuten' : '');
    }
    return time;
  }

}
