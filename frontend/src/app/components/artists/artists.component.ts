import { Component, OnInit, ViewChild } from '@angular/core';
import { ErrorType, ArtistDTO, SearchArtistDTO } from '../../../generated';
import { ArtistService } from '../../services/artist.service';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ErrorMessageComponent } from '../error-message/error-message.component';
import { PageEvent } from '@angular/material/paginator';


@Component({
  selector: 'tl-artists',
  templateUrl: './artists.component.html',
  styleUrls: ['./artists.component.scss']
})
export class ArtistsComponent implements OnInit {

  readonly ARTIST_LIST_PAGE_SIZE = 25;

  artists: ArtistDTO[];
  artistForm: FormGroup;
  amountOfPages = 1;
  private currentPage = 0;

  @ViewChild(ErrorMessageComponent)
  private errorMessageComponent: ErrorMessageComponent;

  constructor(private formBuilder: FormBuilder, private artistService: ArtistService) { }

  ngOnInit(): void {
    this.reload();
    this.artistForm = this.formBuilder.group({
      firstname: [''],
      lastname: [''],
    });
  }

  onPaginationChange(event: PageEvent): void {
    this.currentPage = event.pageIndex;
    this.reload();
  }

  searchArtists() {
    const searchArtistDTO: SearchArtistDTO = Object.assign({}, this.artistForm.value);
    this.artistService.searchArtists(searchArtistDTO, this.currentPage).subscribe(artists => {
      if (artists.body !== null) {
        this.artists = artists.body;
        this.amountOfPages = Number(artists.headers.get('X-Total-Count')) || 1;
        console.log(artists.headers);
      }
    });
  }

  private reload(): void {
    this.artistService.getArtistList(this.currentPage).subscribe(artists => {
      if (artists.body !== null) {
        this.artists = artists.body;
        this.amountOfPages = Number(artists.headers.get('X-Total-Count')) || 1;
        console.log(artists);
      } else {
        this.errorMessageComponent.throwCustomError('Ungültige Antwort vom Server in der Nutzerabfrage', ErrorType.FATAL);
      }
    },
    error => this.errorMessageComponent.defaultServiceErrorHandling(error));
  }


}
