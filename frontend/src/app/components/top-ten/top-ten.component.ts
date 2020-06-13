import { Component, OnInit, ViewChild } from '@angular/core';
import { ErrorType, EventCategory, EventDTO, SearchEventDTO } from '../../../generated';
import { AuthService } from '../../services/auth.service';
import { EventService } from '../../services/event.service';
import { PageEvent } from '@angular/material/paginator';
import { ErrorMessageComponent } from '../error-message/error-message.component';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'tl-top-ten',
  templateUrl: './top-ten.component.html',
  styleUrls: ['./top-ten.component.scss']
})
export class TopTenComponent implements OnInit {

  readonly EVENT_LIST_PAGE_SIZE = 25;

  constructor(private readonly eventService: EventService,
    private readonly authService: AuthService, private formBuilder: FormBuilder) {
  }

  events: EventDTO[];
  searchForm: FormGroup;

  @ViewChild(ErrorMessageComponent)
  private errorMessageComponent: ErrorMessageComponent;

  get eventCategories(): string[] {
    return Object.keys(EventCategory);
  }

  ngOnInit(): void {
    this.searchForm = this.formBuilder.group({
      category: [null],
    });
    this.reload();
  }

  searchEvents(): void {
    this.reload();
  }

  private reload(): void {
    this.eventService.getTopTen(this.searchForm.get('category')?.value)
        .subscribe(events => {
            this.events = events;
          },
          error => this.errorMessageComponent.defaultServiceErrorHandling(error));
  }

}
