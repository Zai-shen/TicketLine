import { Component, OnInit, ViewChild } from '@angular/core';
import { EventCategory, EventDTO, EventSoldDTO} from '../../../generated';
import { AuthService } from '../../services/auth.service';
import { EventService } from '../../services/event.service';
import { ErrorMessageComponent } from '../error-message/error-message.component';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'tl-top-ten',
  templateUrl: './top-ten.component.html',
  styleUrls: ['./top-ten.component.scss']
})
export class TopTenComponent implements OnInit {

  readonly EVENT_LIST_PAGE_SIZE = 25;
  private maxSold = 0;

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
            events.forEach(e => {
              if (e.id) {
                this.eventService.getSold(e.id).subscribe(
                  (sold: EventSoldDTO) => {
                    if (this.maxSold < sold.sold) {
                      this.maxSold = sold.sold;
                    }
                    e.sold = sold.sold;
                    console.log(e);
                  }
                );
              }
            });
          },
          error => this.errorMessageComponent.defaultServiceErrorHandling(error));
  }

  getStyle(event: EventDTO): any {
    if (event.sold && this.maxSold >= event.sold) {
      const percent = Math.round((event.sold/this.maxSold) * 100);
      return {
        'background-image': `linear-gradient(to right, #ebebeb ${percent}%, #ffffff ${percent}%)`,
      };
    }
    return {};
  }
}
