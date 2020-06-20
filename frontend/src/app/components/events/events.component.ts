import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { ArtistApiService, ArtistDTO, ErrorType, EventCategory, EventDTO, SearchEventDTO } from '../../../generated';
import { AuthService } from '../../services/auth.service';
import { EventService } from '../../services/event.service';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { ErrorMessageComponent } from '../error-message/error-message.component';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { debounceTime, map, switchMap, tap } from 'rxjs/operators';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'tl-events',
  templateUrl: './events.component.html',
  styleUrls: ['./events.component.scss']
})
export class EventsComponent implements OnInit {

  readonly EVENT_LIST_PAGE_SIZE = 25;

  constructor(
    private readonly eventService: EventService,
    private readonly authService: AuthService,
    private formBuilder: FormBuilder,
    private readonly artistService: ArtistApiService,
    private readonly route: ActivatedRoute) {
  }


  events: EventDTO[];
  totalAmountOfEvents = 0;
  searchForm: FormGroup;
  private currentPage = 0;

  @ViewChild(ErrorMessageComponent)
  private errorMessageComponent: ErrorMessageComponent;

  @ViewChild(MatPaginator)
  private paginator: MatPaginator;

  get eventCategories(): string[] {
    return Object.keys(EventCategory);
  }

  ngOnInit(): void {
    this.searchForm = this.formBuilder.group({
      title: [''],
      category: [null],
      duration: [''],
      description: [''],
      artist: [''],
    });
    const artistCtrl = this.searchForm.get('artist');
    this.route.queryParamMap
        .pipe(map(params => params.get('artist_id')))
        .subscribe(
          (value: string | null) => {
            if (value !== null && !isNaN(+value)) {
              this.artistService.getArtist(+value).subscribe(
                (artist: ArtistDTO) => {
                  if (artistCtrl !== null) {
                    artistCtrl.setValue(artist);
                    this.reload();
                  }
                }
              );
            }
          }
        );
    this.reload();
  }


  isAdminLoggedIn(): boolean {
    return this.authService.isAdminLoggedIn();
  }

  onPaginationChange(event: PageEvent): void {
    this.currentPage = event.pageIndex;
    this.reload();
  }

  searchEvents(): void {
    this.paginator.firstPage();
    this.reload();
  }

  private reload(): void {
    let searchEventDTO: SearchEventDTO = {};
    console.log(this.searchForm.value);
    if (this.searchForm) {
      searchEventDTO = Object.assign({}, this.searchForm.value);
      // noinspection SuspiciousTypeOfGuard
      if (!searchEventDTO.artist?.id) {
        searchEventDTO.artist = undefined;
      }
    }

    this.eventService.searchEvents(searchEventDTO, this.currentPage)
        .subscribe(events => {
            if (events.body !== null) {
              this.events = events.body;
              this.totalAmountOfEvents = Number(events.headers.get('X-Total-Count')) || 0;
            } else {
              this.errorMessageComponent.throwCustomError('Ungültige Antwort vom Server in der Eventabfrage',
                ErrorType.FATAL);
            }
          },
          error => this.errorMessageComponent.defaultServiceErrorHandling(error));
  }
}
