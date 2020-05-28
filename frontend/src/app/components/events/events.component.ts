import { Component, OnInit } from '@angular/core';
import { EventApiService, EventCategory, PerformanceDTO } from '../../../generated';
import { UserService } from '../../services/user.service';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'tl-events',
  templateUrl: './events.component.html',
  styleUrls: ['./events.component.scss']
})
export class EventsComponent implements OnInit {

  constructor(private readonly eventService: EventApiService,
              private readonly authService: AuthService) {
  }

  public performances: PerformanceDTO[];

  get eventCategories(): string[] {
    return Object.keys(EventCategory);
  }

  ngOnInit() {
    this.eventService.getTopTenEvents(0).subscribe(value => {
      this.performances = value;
    });
  }

  isAdminLoggedIn(): boolean {
    return this.authService.isAdminLoggedIn();
  }

}
