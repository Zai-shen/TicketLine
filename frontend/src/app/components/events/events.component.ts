import { Component, OnInit, ViewChild } from '@angular/core';
import { ErrorType, EventCategory, EventDTO } from '../../../generated';
import { AuthService } from '../../services/auth.service';
import { EventService } from '../../services/event.service';
import { PageEvent } from '@angular/material/paginator';
import { ErrorMessageComponent } from '../error-message/error-message.component';

@Component({
  selector: 'tl-events',
  templateUrl: './events.component.html',
  styleUrls: ['./events.component.scss']
})
export class EventsComponent implements OnInit {

  readonly EVENT_LIST_PAGE_SIZE = 25;

  constructor(private readonly eventService: EventService,
    private readonly authService: AuthService) {
  }

  events: EventDTO[];
  currentPage = 0;
  amountOfPages = 1;

  @ViewChild(ErrorMessageComponent)
  private errorMessageComponent: ErrorMessageComponent;

  get eventCategories(): string[] {
    return Object.keys(EventCategory);
  }

  ngOnInit() {
    this.reload();
  }

  isAdminLoggedIn(): boolean {
    return this.authService.isAdminLoggedIn();
  }

  onPaginationChange(event: PageEvent): void {
    this.currentPage = event.pageIndex;
    this.reload();
  }

  private reload(): void {
    this.eventService.searchEvents({}, this.currentPage)
        .subscribe(events => {
            if (events.body !== null) {
              this.events = events.body;
              this.amountOfPages = Number(events.headers.get('X-Total-Count')) || 1;
            } else {
              this.errorMessageComponent.throwCustomError('Ungültige Antwort vom Server in der Eventabfrage',
                ErrorType.FATAL);
            }
          },
          error => this.errorMessageComponent.defaultServiceErrorHandling(error));
  }

}
