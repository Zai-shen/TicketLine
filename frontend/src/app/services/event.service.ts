import { Injectable } from '@angular/core';
import { EventApiService, EventDTO, PerformanceDTO } from '../../generated';
import { cloneDeep } from 'lodash-es';
import { defer, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class EventService {
  constructor(private readonly eventApiService: EventApiService) {
  }
  createEvent(event: EventDTO) {
     return this.eventApiService.createEvent(event);
  }
}
