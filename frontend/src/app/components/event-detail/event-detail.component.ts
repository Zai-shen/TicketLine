import { Component, OnInit } from '@angular/core';
import {
  BookingDTO,
  BookingRequestDTO,
  EventApiService,
  EventDTO,
  PerformanceDTO, SeatmapOccupationDTO,
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

  selectedSeats: Map<PerformanceDTO, [SeatRenderDTO[], StandingAreaSelection[]]> =
    new Map<PerformanceDTO, [SeatRenderDTO[], StandingAreaSelection[]]>();

  public errorMsg?: string;

  constructor(private eventService: EventApiService, private ticketApiService: TicketApiService,
    private route: ActivatedRoute, private router: Router, private authService: AuthService,
    private snackBar: MatSnackBar, private globals: Globals) {
  }

  reservedBooking: BookingDTO = { fixedSeats: [], freeSeats: [] };

  ngOnInit(): void {
    if (!!history.state.performance) {
      this.reservedBooking = history.state;
    }
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

  selectedSeatsChanged(performance: PerformanceDTO, newSeats: SeatRenderDTO[]) {
    console.log(newSeats);
    const entry = this.selectedSeats.get(performance);
    if (entry) {
      const [_, standing] = entry;
      this.selectedSeats.set(performance, [newSeats, standing]);
    } else {
      this.selectedSeats.set(performance, [newSeats, []]);
    }
  }

  selectedStandingAreasChanged(performance: PerformanceDTO, standingAreas: StandingAreaSelection[]) {
    const entry = this.selectedSeats.get(performance);
    standingAreas = standingAreas.filter(x => x.selectedPositions > 0);
    if (entry) {
      const [seats, _] = entry;
      this.selectedSeats.set(performance, [seats, standingAreas]);
    } else {
      this.selectedSeats.set(performance, [[], standingAreas]);
    }
  }

  isUserLoggedIn(): boolean {
    return this.authService.isLoggedIn();
  }

  formatSeats(performance: PerformanceDTO): string[] {
    const entry = this.selectedSeats.get(performance);
    if (entry) {
      const [seats, _] = entry;
      return seats.map(x => `Reihe ${ x.rowLabel } Platz ${ x.colLabel } - ${ x.area.price }€`);
    }
    return [];
  }

  formatStanding(performance: PerformanceDTO): string[] {
    const entry = this.selectedSeats.get(performance);
    if (entry) {
      const [_, standing] = entry;
      return standing.map(
        x => (x.selectedPositions > 1 ? `${ x.selectedPositions } Stehplätze` : `${ x.selectedPositions } Stehplatz`) +
          ` - ${ x.selectedPositions * x.standingArea.price }€`);
    }
    return [];
  }

  buyTicket(reserve: boolean, performance: PerformanceDTO): void {
    const bookingDto: BookingRequestDTO = {};
    const entry = this.selectedSeats.get(performance);
    let seats: SeatRenderDTO[] = [];
    let standing: StandingAreaSelection[] = [];
    if (entry && (entry[0].length + entry[1].length > 0)) {
      [seats, standing] = entry;
    } else {
      this.snackBar.open('Bitte wählen Sie zuerst die gewünschten Plätze aus', 'OK', {
        duration: this.globals.defaultSnackbarDuration
      });
      return;
    }
    console.log(standing);
    console.log(seats.map(x => x.id));
    bookingDto.seats = seats.map(x => x.id);
    bookingDto.areas = [{ seatGroupId: 1, amount: 1 }];
    bookingDto.areas = standing.map(x => {
      return { seatGroupId: x.standingArea.id, amount: x.selectedPositions };
    });
    bookingDto.reservationId = this.reservedBooking.id;

    if (!!this.event.id && !!performance.id) {
      this.ticketApiService.createTicket(this.event.id, performance.id, reserve, bookingDto)
          .subscribe(() => {
            this.snackBar.open('Kauf erfolgreich', 'OK', {
              duration: this.globals.defaultSnackbarDuration
            });
            this.router.navigate(['/user/tickets']);
          });
    }
  }
}
