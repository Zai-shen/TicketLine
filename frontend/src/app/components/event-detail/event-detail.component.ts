import { Component, OnInit } from '@angular/core';
import { BookingDTO, EventApiService, EventDTO, PerformanceDTO, TicketApiService } from '../../../generated';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { SeatRenderDTO } from '../seatplan/entities/seat-render-dto';
import { StandingAreaRenderDTO } from '../seatplan/entities/standing-area-render-dto';
import { SeatmapRenderData } from '../seatplan/entities/seatmap-render-data';
import { StandingAreaSelection } from '../seatplan/entities/standing-area-selection';
import { SeatmapOccupationDTO } from '../../../generated/model/seatmapOccupationDTO';
import { Observable } from 'rxjs';

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
    private snackBar: MatSnackBar) {
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

  getSeatmap(): SeatmapOccupationDTO {
    return JSON.parse('{"id":null,"seatGroupAreas":[{"id":31,"x":52.0,"y":48.0,"width":100.0,"height":160.0,"name":"Bereich-1","seats":[{"id":75058,"x":23.0,"y":135.0,"rowLabel":"i","colLabel":"1","radius":5.0,"price":10.0,"reserved":false,"sold":false},{"id":75048,"x":37.0,"y":93.0,"rowLabel":"f","colLabel":"2","radius":5.0,"price":10.0,"reserved":false,"sold":false},{"id":75023,"x":65.0,"y":149.0,"rowLabel":"j","colLabel":"4","radius":5.0,"price":10.0,"reserved":false,"sold":false},{"id":75009,"x":37.0,"y":23.0,"rowLabel":"a","colLabel":"2","radius":5.0,"price":10.0,"reserved":false,"sold":false},{"id":75052,"x":23.0,"y":37.0,"rowLabel":"b","colLabel":"1","radius":5.0,"price":10.0,"reserved":false,"sold":false},{"id":75042,"x":23.0,"y":149.0,"rowLabel":"j","colLabel":"1","radius":5.0,"price":10.0,"reserved":false,"sold":false},{"id":75060,"x":65.0,"y":93.0,"rowLabel":"f","colLabel":"4","radius":5.0,"price":10.0,"reserved":false,"sold":false},{"id":75003,"x":37.0,"y":135.0,"rowLabel":"i","colLabel":"2","radius":5.0,"price":10.0,"reserved":false,"sold":false},{"id":75051,"x":51.0,"y":135.0,"rowLabel":"i","colLabel":"3","radius":5.0,"price":10.0,"reserved":false,"sold":false},{"id":75008,"x":79.0,"y":23.0,"rowLabel":"a","colLabel":"5","radius":5.0,"price":10.0,"reserved":false,"sold":false},{"id":75034,"x":23.0,"y":23.0,"rowLabel":"a","colLabel":"1","radius":5.0,"price":10.0,"reserved":false,"sold":false},{"id":75046,"x":51.0,"y":37.0,"rowLabel":"b","colLabel":"3","radius":5.0,"price":10.0,"reserved":false,"sold":false},{"id":75035,"x":93.0,"y":37.0,"rowLabel":"b","colLabel":"6","radius":5.0,"price":10.0,"reserved":false,"sold":false},{"id":75041,"x":93.0,"y":65.0,"rowLabel":"d","colLabel":"6","radius":5.0,"price":10.0,"reserved":false,"sold":false},{"id":75049,"x":65.0,"y":107.0,"rowLabel":"g","colLabel":"4","radius":5.0,"price":10.0,"reserved":false,"sold":false},{"id":75002,"x":23.0,"y":93.0,"rowLabel":"f","colLabel":"1","radius":5.0,"price":10.0,"reserved":false,"sold":false},{"id":75027,"x":93.0,"y":79.0,"rowLabel":"e","colLabel":"6","radius":5.0,"price":10.0,"reserved":false,"sold":false},{"id":75059,"x":79.0,"y":121.0,"rowLabel":"h","colLabel":"5","radius":5.0,"price":10.0,"reserved":false,"sold":false},{"id":75047,"x":37.0,"y":79.0,"rowLabel":"e","colLabel":"2","radius":5.0,"price":10.0,"reserved":false,"sold":false},{"id":75055,"x":93.0,"y":23.0,"rowLabel":"a","colLabel":"6","radius":5.0,"price":10.0,"reserved":false,"sold":false},{"id":75004,"x":51.0,"y":79.0,"rowLabel":"e","colLabel":"3","radius":5.0,"price":10.0,"reserved":false,"sold":false},{"id":75020,"x":23.0,"y":51.0,"rowLabel":"c","colLabel":"1","radius":5.0,"price":10.0,"reserved":false,"sold":false},{"id":75056,"x":93.0,"y":149.0,"rowLabel":"j","colLabel":"6","radius":5.0,"price":10.0,"reserved":false,"sold":false},{"id":75028,"x":23.0,"y":107.0,"rowLabel":"g","colLabel":"1","radius":5.0,"price":10.0,"reserved":false,"sold":false},{"id":75022,"x":51.0,"y":93.0,"rowLabel":"f","colLabel":"3","radius":5.0,"price":10.0,"reserved":false,"sold":false},{"id":75007,"x":23.0,"y":79.0,"rowLabel":"e","colLabel":"1","radius":5.0,"price":10.0,"reserved":false,"sold":false},{"id":75011,"x":37.0,"y":37.0,"rowLabel":"b","colLabel":"2","radius":5.0,"price":10.0,"reserved":false,"sold":false},{"id":75025,"x":65.0,"y":37.0,"rowLabel":"b","colLabel":"4","radius":5.0,"price":10.0,"reserved":false,"sold":false},{"id":75050,"x":23.0,"y":65.0,"rowLabel":"d","colLabel":"1","radius":5.0,"price":10.0,"reserved":false,"sold":false},{"id":75014,"x":93.0,"y":121.0,"rowLabel":"h","colLabel":"6","radius":5.0,"price":10.0,"reserved":false,"sold":false},{"id":75043,"x":51.0,"y":51.0,"rowLabel":"c","colLabel":"3","radius":5.0,"price":10.0,"reserved":false,"sold":false},{"id":75012,"x":51.0,"y":65.0,"rowLabel":"d","colLabel":"3","radius":5.0,"price":10.0,"reserved":false,"sold":false},{"id":75016,"x":79.0,"y":65.0,"rowLabel":"d","colLabel":"5","radius":5.0,"price":10.0,"reserved":false,"sold":false},{"id":75039,"x":79.0,"y":79.0,"rowLabel":"e","colLabel":"5","radius":5.0,"price":10.0,"reserved":false,"sold":false},{"id":75029,"x":79.0,"y":135.0,"rowLabel":"i","colLabel":"5","radius":5.0,"price":10.0,"reserved":false,"sold":false},{"id":75032,"x":79.0,"y":93.0,"rowLabel":"f","colLabel":"5","radius":5.0,"price":10.0,"reserved":false,"sold":false},{"id":75026,"x":37.0,"y":51.0,"rowLabel":"c","colLabel":"2","radius":5.0,"price":10.0,"reserved":false,"sold":false},{"id":75013,"x":79.0,"y":149.0,"rowLabel":"j","colLabel":"5","radius":5.0,"price":10.0,"reserved":false,"sold":false},{"id":75006,"x":93.0,"y":107.0,"rowLabel":"g","colLabel":"6","radius":5.0,"price":10.0,"reserved":false,"sold":false},{"id":75017,"x":51.0,"y":107.0,"rowLabel":"g","colLabel":"3","radius":5.0,"price":10.0,"reserved":false,"sold":false},{"id":75038,"x":65.0,"y":121.0,"rowLabel":"h","colLabel":"4","radius":5.0,"price":10.0,"reserved":false,"sold":false},{"id":75030,"x":65.0,"y":23.0,"rowLabel":"a","colLabel":"4","radius":5.0,"price":10.0,"reserved":false,"sold":false},{"id":75044,"x":37.0,"y":107.0,"rowLabel":"g","colLabel":"2","radius":5.0,"price":10.0,"reserved":false,"sold":false},{"id":75037,"x":37.0,"y":121.0,"rowLabel":"h","colLabel":"2","radius":5.0,"price":10.0,"reserved":false,"sold":false},{"id":75019,"x":65.0,"y":135.0,"rowLabel":"i","colLabel":"4","radius":5.0,"price":10.0,"reserved":false,"sold":false},{"id":75005,"x":79.0,"y":37.0,"rowLabel":"b","colLabel":"5","radius":5.0,"price":10.0,"reserved":false,"sold":false},{"id":75053,"x":65.0,"y":79.0,"rowLabel":"e","colLabel":"4","radius":5.0,"price":10.0,"reserved":false,"sold":false},{"id":75024,"x":23.0,"y":121.0,"rowLabel":"h","colLabel":"1","radius":5.0,"price":10.0,"reserved":false,"sold":false},{"id":75036,"x":79.0,"y":107.0,"rowLabel":"g","colLabel":"5","radius":5.0,"price":10.0,"reserved":false,"sold":false},{"id":75031,"x":65.0,"y":51.0,"rowLabel":"c","colLabel":"4","radius":5.0,"price":10.0,"reserved":false,"sold":false},{"id":75033,"x":51.0,"y":149.0,"rowLabel":"j","colLabel":"3","radius":5.0,"price":10.0,"reserved":false,"sold":false},{"id":75018,"x":51.0,"y":121.0,"rowLabel":"h","colLabel":"3","radius":5.0,"price":10.0,"reserved":false,"sold":false},{"id":75045,"x":51.0,"y":23.0,"rowLabel":"a","colLabel":"3","radius":5.0,"price":10.0,"reserved":false,"sold":false},{"id":75001,"x":65.0,"y":65.0,"rowLabel":"d","colLabel":"4","radius":5.0,"price":10.0,"reserved":false,"sold":false},{"id":75054,"x":37.0,"y":65.0,"rowLabel":"d","colLabel":"2","radius":5.0,"price":10.0,"reserved":false,"sold":false},{"id":75021,"x":37.0,"y":149.0,"rowLabel":"j","colLabel":"2","radius":5.0,"price":10.0,"reserved":false,"sold":false},{"id":75015,"x":93.0,"y":93.0,"rowLabel":"f","colLabel":"6","radius":5.0,"price":10.0,"reserved":false,"sold":false},{"id":75040,"x":93.0,"y":135.0,"rowLabel":"i","colLabel":"6","radius":5.0,"price":10.0,"reserved":false,"sold":false},{"id":75010,"x":93.0,"y":51.0,"rowLabel":"c","colLabel":"6","radius":5.0,"price":10.0,"reserved":false,"sold":false},{"id":75057,"x":79.0,"y":51.0,"rowLabel":"c","colLabel":"5","radius":5.0,"price":10.0,"reserved":false,"sold":false}],"seatLabels":[{"x":9.0,"y":79.0,"size":5.0,"text":"e"},{"x":9.0,"y":121.0,"size":5.0,"text":"h"},{"x":65.0,"y":9.0,"size":5.0,"text":"4"},{"x":9.0,"y":37.0,"size":5.0,"text":"b"},{"x":93.0,"y":9.0,"size":5.0,"text":"6"},{"x":23.0,"y":9.0,"size":5.0,"text":"1"},{"x":9.0,"y":65.0,"size":5.0,"text":"d"},{"x":9.0,"y":51.0,"size":5.0,"text":"c"},{"x":9.0,"y":107.0,"size":5.0,"text":"g"},{"x":9.0,"y":149.0,"size":5.0,"text":"j"},{"x":79.0,"y":9.0,"size":5.0,"text":"5"},{"x":9.0,"y":93.0,"size":5.0,"text":"f"},{"x":9.0,"y":135.0,"size":5.0,"text":"i"},{"x":9.0,"y":23.0,"size":5.0,"text":"a"},{"x":51.0,"y":9.0,"size":5.0,"text":"3"},{"x":37.0,"y":9.0,"size":5.0,"text":"2"}]}],"standingAreas":[{"id":31,"x":173.0,"y":46.0,"width":100.0,"height":137.0,"name":"Bereich-0","maxPeople":10,"price":null,"reserved":3,"sold":3}]}');
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
    bookingDto.freeSeats = [{ seatGroupId: 1, amount: 1}];
    bookingDto.fixedSeats = [{ seatgroupId: 1, x: 1, y: 1}];

    if (!!this.event.id && !!performance.id) {
      this.ticketApiService.createTicket(this.event.id, performance.id, reserve, bookingDto)
          .subscribe(() => {
            this.snackBar.open('Kauf erfolgreich');
            this.router.navigate(['/user/tickets']);
          });
    }
  }
}
