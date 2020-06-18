import { Component, OnInit } from '@angular/core';
import {
  BookingDTO,
  EventApiService,
  EventDTO,
  PerformanceDTO,
  SeatmapOccupationDTO,
  TicketApiService
} from '../../../generated';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { SeatRenderDTO } from '../seatplan/entities/seat-render-dto';
import { StandingAreaSelection } from '../seatplan/entities/standing-area-selection';
import { EMPTY, Observable } from 'rxjs';
import { Globals } from '../../global/globals';

@Component({
  selector: 'tl-home',
  templateUrl: './event-detail.component.html',
  styleUrls: ['./event-detail.component.scss']
})
export class EventDetailComponent implements OnInit {

  event: EventDTO;

  performances: PerformanceDTO[];

  public errorMsg?: string;

  constructor(private eventService: EventApiService, private ticketApiService: TicketApiService,
    private route: ActivatedRoute, private router: Router, private authService: AuthService,
    private snackBar: MatSnackBar, private globals: Globals) {
  }

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

  getSeatmap(performance: PerformanceDTO): Observable<SeatmapOccupationDTO> {
    const eventId = this.event.id;
    const performanceID = performance.id;
    if (eventId && performanceID) {
      return this.eventService.getSeatmapOfPerformance(eventId, performanceID);
    }
    return EMPTY;
  }

  selectedSeatsChanged(seats: SeatRenderDTO[]) {
    // TODO
    console.log(seats);
  }

  selectedStandingAreasChanged(standingAreas: StandingAreaSelection[]) {
    // TODO
    console.log(standingAreas);
  }

  isUserLoggedIn(): boolean {
    return this.authService.isLoggedIn();
  }

  buyTicket(reserve: boolean, performance: PerformanceDTO): void {
    const bookingDto: BookingDTO = {};
    // TODO: replace these tickets with the real tickets, once the seatmap gets implemented.
    bookingDto.freeSeats = [{ seatGroupId: 2, amount: 1 }];
    bookingDto.fixedSeats = [{ seatgroupId: 1, x: 23, y: 23 }];

    if (!!this.event.id && !!performance.id) {
      this.ticketApiService.createTicket(this.event.id, performance.id, reserve, bookingDto)
          .subscribe(() => {
            this.snackBar.open('Kauf erfolgreich', undefined, {
              duration: this.globals.defaultSnackbarDuration
            });
            this.router.navigate(['/user/tickets']);
          });
    }
  }
}
