import { Component, OnInit, ViewChild } from '@angular/core';
import { ErrorType, EventCategory, EventDTO, SearchEventDTO } from '../../../generated';
import { AuthService } from '../../services/auth.service';
import { EventService } from '../../services/event.service';
import { PageEvent } from '@angular/material/paginator';
import { ErrorMessageComponent } from '../error-message/error-message.component';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'tl-events',
  templateUrl: './events.component.html',
  styleUrls: ['./events.component.scss']
})
export class EventsComponent implements OnInit {

  readonly EVENT_LIST_PAGE_SIZE = 25;

  constructor(private readonly eventService: EventService,
    private readonly authService: AuthService, private formBuilder: FormBuilder) {
  }

  events: EventDTO[];
  currentPage = 0;
  amountOfPages = 1;
  searchForm: FormGroup;

  @ViewChild(ErrorMessageComponent)
  private errorMessageComponent: ErrorMessageComponent;

  get eventCategories(): string[] {
    return Object.keys(EventCategory);
  }

  ngOnInit(): void {
    this.reload();
    this.searchForm = this.formBuilder.group({
      title: [''],
      category: [null],
      duration: [''],
      description: ['']
    });
  }

  isAdminLoggedIn(): boolean {
    return this.authService.isAdminLoggedIn();
  }

  onPaginationChange(event: PageEvent): void {
    this.currentPage = event.pageIndex;
    this.reload();
  }

  searchEvents(): void {
    this.currentPage = 0;
    this.reload();
  }

  private reload(): void {
    let searchEventDTO: SearchEventDTO = {};

    if (this.searchForm) {
      searchEventDTO = Object.assign({}, this.searchForm.value);
    }

    this.eventService.searchEvents(searchEventDTO, this.currentPage)
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
