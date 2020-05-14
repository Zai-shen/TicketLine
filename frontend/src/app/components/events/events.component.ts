import { Component, OnInit } from '@angular/core';
import { EventApiService, EventCategory, PerformanceDTO } from '../../../generated';

@Component({
  selector: 'tl-events',
  templateUrl: './events.component.html',
  styleUrls: ['./events.component.scss']
})
export class EventsComponent implements OnInit {

  constructor(private eventService: EventApiService) {
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

}
