import { Injectable } from '@angular/core';
import { EventApiService, EventDTO, SearchEventDTO } from '../../generated';
import { Observable } from 'rxjs';
import { HttpResponse } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class EventService {
  constructor(private readonly eventApiService: EventApiService) {
  }
  createEvent(event: EventDTO): Observable<number> {
     return this.eventApiService.createEvent(event);
  }

  searchEvents(eventSearchParameters: SearchEventDTO, page: number): Observable<HttpResponse<EventDTO[]>> {
    return this.eventApiService.searchEvents(eventSearchParameters, page, 'response');
  }
}
