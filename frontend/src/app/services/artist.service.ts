import { Injectable } from '@angular/core';
import { ArtistApiService, ArtistDTO, SearchArtistDTO } from '../../generated';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ArtistService {

  constructor(private artistApiService: ArtistApiService) { }

  getArtistList(page: number): Observable<HttpResponse<Array<ArtistDTO>>> {
    return this.artistApiService.getArtistList(page, 'response');
  }

  searchArtists(searchArtistDTO: SearchArtistDTO, page: number): Observable<HttpResponse<Array<ArtistDTO>>> {
    return this.artistApiService.searchArtists(searchArtistDTO, page, 'response');
  }
}
