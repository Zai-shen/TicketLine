import { Injectable } from '@angular/core';
import { EventApiService, EventDTO, PerformanceDTO } from '../../generated';

@Injectable({
  providedIn: 'root'
})
export class PerformanceService {
  constructor(private readonly eventApiService: EventApiService) {
  }
  createPerformance(eventId: number, performance: PerformanceDTO) {
        return this.eventApiService.createPerformance(eventId, performance);
  }
}
