import { Component, OnInit } from '@angular/core';
import {
  BookingDTO,
  EventApiService,
  EventDTO,
  FreeSeatgroupBookingDTO,
  PerformanceDTO,
  TicketApiService
} from '../../../generated';
import { ActivatedRoute, Router } from '@angular/router';
import { round } from 'lodash-es';

@Component({
  selector: 'tl-home',
  templateUrl: './event-detail.component.html',
  styleUrls: ['./event-detail.component.scss']
})
export class EventDetailComponent implements OnInit {

  public event: EventDTO;

  public performances: PerformanceDTO[];

  public errorMsg?: string;

  constructor(private eventService: EventApiService, private ticketApiService: TicketApiService,
    private route: ActivatedRoute, private router: Router) {
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
      this.eventService.getPerformances(+params['id']).subscribe(
        (performances: Array<PerformanceDTO>) => {
          this.performances = performances;
        },
        error => {
          if (error.status === 404) {
            this.errorMsg = 'Zum angeforerten Event konnten keine Performances geladen werden';
          }
        }
      );
    });
  }

  buyTicket(reserve: boolean) {
    const bookingDto: BookingDTO = {};
    bookingDto.freeSeats = { amount: 1 };
    bookingDto.fixedSeats = [{ seatgroupId: 1, x: 1, y: 1 }];
    if (!!this.event.id && !!this.performances[0].id) {
      this.ticketApiService.createTicket(this.event.id, this.performances[0].id, reserve, bookingDto)
          .subscribe(() => this.router.navigate(['/tickets']));
    }
  }
}
