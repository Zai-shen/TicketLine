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
  totalAmountOfArtists = 0;
  private currentPage = 0;
  searched: boolean;

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
    this.paginator.firstPage();
    this.getAllArtists();
  }

  newSearch(): void {
    this.paginator.firstPage();
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
            this.totalAmountOfArtists = Number(artists.headers.get('X-Total-Count')) || 0;
          }
        },
        error => this.errorMessageComponent.defaultServiceErrorHandling(error));
    }
  }

  private getAllArtists(): void {
    this.artistService.getArtistList(this.currentPage).subscribe(artists => {
        if (artists.body !== null) {
          this.artists = artists.body;
          this.totalAmountOfArtists = Number(artists.headers.get('X-Total-Count')) || 0;
        }
      },
      error => this.errorMessageComponent.defaultServiceErrorHandling(error));
  }

}
