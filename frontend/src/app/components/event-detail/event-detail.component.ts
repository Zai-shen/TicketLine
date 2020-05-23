import { Component, OnInit } from '@angular/core';
import { EventApiService, EventDTO } from '../../../generated';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'tl-home',
  templateUrl: './event-detail.component.html',
  styleUrls: ['./event-detail.component.scss']
})
export class EventDetailComponent implements OnInit {

  public event: EventDTO;

  public errorMsg?: string;

  constructor(private eventService: EventApiService, private route: ActivatedRoute) {
  }

  public seats = Array.from(Array(32).keys());

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.eventService.getEvent(+params['id']).subscribe(
        (event: EventDTO) => {
          this.event = event;
        },
        error => {
          if (error.status === 404) {
            this.errorMsg = 'Das angeforderte Event konnte nicht gefunden werden';
          }
        }
      );
    });
  }

}
