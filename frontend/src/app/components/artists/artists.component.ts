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
  amountOfPages = 0;
  private currentPage = 0;
  searched: boolean;

  @ViewChild(ErrorMessageComponent)
  private errorMessageComponent: ErrorMessageComponent;

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
    if (this.searched == true) {
      this.searchArtists();
    } else {
      this.getAllArtists();
    }
  }

  clearSearch() {
    this.searched = false;
    this.artistForm.reset();
    this.getAllArtists();
  }

  searchArtists() {
    const searchArtistDTO: SearchArtistDTO = Object.assign({}, this.artistForm.value);
    if (!searchArtistDTO.firstname && !searchArtistDTO.lastname) {
    } else {
      this.searched = true;
      this.artistService.searchArtists(searchArtistDTO, this.currentPage).subscribe(artists => {
          if (artists.body !== null) {
            this.artists = artists.body;
            this.amountOfPages = Number(artists.headers.get('X-Total-Count')) || 0;
          }
        },
        error => this.errorMessageComponent.defaultServiceErrorHandling(error));
    }
  }

  private getAllArtists(): void {
    this.artistService.getArtistList(this.currentPage).subscribe(artists => {
        if (artists.body !== null) {
          this.artists = artists.body;
          this.amountOfPages = Number(artists.headers.get('X-Total-Count')) || 0;
        }
      },
      error => this.errorMessageComponent.defaultServiceErrorHandling(error));
  }

}
