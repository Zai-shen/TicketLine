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
    let time = 'vor ';
    if (minutes <= 2 && hours === 0 && days === 0 && months === 0) {
      time = 'gerade eben';
    } else {
      if (months === 1) {
        time += months + ' Monat ';
      } else if (months > 1) {
        time += months + ' Monaten ';
      }
      if (days === 1) {
        time += days + ' Tag ';
      } else if (days > 1) {
        time += days + ' Tagen ';
      }
      if (hours === 1) {
        time += hours + ' Stunde ';
      } else if (hours > 1) {
        time += hours + ' Stunden ';
      }
      if (minutes === 1) {
        time += minutes + ' Minute ';
      } else if (minutes > 1) {
        time += minutes + ' Minuten ';
      }
    }
    return time;
  }

}
