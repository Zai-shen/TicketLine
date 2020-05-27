import { Component, OnInit, ViewChild } from '@angular/core';
import { ArtistDTO, SearchArtistDTO } from '../../../generated';
import { ArtistService } from '../../services/artist.service';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ErrorMessageComponent } from '../error-message/error-message.component';
import { MatPaginator, PageEvent } from '@angular/material/paginator';


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
  searched: boolean;
  artistsFound = 0;

  @ViewChild(ErrorMessageComponent)
  private errorMessageComponent: ErrorMessageComponent;

  @ViewChild(MatPaginator)
  private paginator: MatPaginator;

  constructor(private formBuilder: FormBuilder, private artistService: ArtistService) { }

  ngOnInit(): void {
    this.getAllArtists();
    this.artistForm = this.formBuilder.group({
      firstname: [''],
      lastname: [''],
    });
  }

  onPaginationChange(event: PageEvent): void {
    this.currentPage = event.pageIndex;
    if (this.searched) {
      this.searchArtists();
    } else {
      this.getAllArtists();
    }
  }

  clearSearch(): void {
    this.searched = false;
    this.artistForm.reset();
    this.currentPage = 0;
    this.paginator.pageIndex = 0;
    this.getAllArtists();
  }

  newSearch(): void {
    this.currentPage = 0;
    this.paginator.pageIndex = 0;
    this.searchArtists();
  }

  searchArtists(): void {
    const searchArtistDTO: SearchArtistDTO = Object.assign({}, this.artistForm.value);
    if (!searchArtistDTO.firstname && !searchArtistDTO.lastname) {
    } else {
      this.searched = true;
      this.artistService.searchArtists(searchArtistDTO, this.currentPage).subscribe(artists => {
          if (artists.body !== null) {
            this.artists = artists.body;
            this.amountOfPages = Number(artists.headers.get('X-Total-Count')) || 1;
            this.artistsFound = Number(artists.headers.get('X-Total-Count')) || 0;
          }
        },
        error => this.errorMessageComponent.defaultServiceErrorHandling(error));
    }
  }

  private getAllArtists(): void {
    this.artistService.getArtistList(this.currentPage).subscribe(artists => {
        if (artists.body !== null) {
          this.artists = artists.body;
          this.amountOfPages = Number(artists.headers.get('X-Total-Count')) || 1;
          this.artistsFound = Number(artists.headers.get('X-Total-Count')) || 0;
        }
      },
      error => this.errorMessageComponent.defaultServiceErrorHandling(error));
  }

}