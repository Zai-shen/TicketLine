import { Injectable } from '@angular/core';
import { EventApiService, EventDTO, PerformanceDTO, SearchPerformanceDTO } from '../../generated';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PerformanceService {
  constructor(private readonly eventApiService: EventApiService) {
  }
  createPerformance(eventId: number, performance: PerformanceDTO) {
        return this.eventApiService.createPerformance(eventId, performance);
  }

  getAllPerformances(page: number): Observable<HttpResponse<Array<PerformanceDTO>>> {
    return this.eventApiService.getAllPerformances(page, 'response');
  }

  searchPerformances(searchPerformanceDTO: SearchPerformanceDTO, page: number): Observable<HttpResponse<Array<PerformanceDTO>>> {
    return this.eventApiService.searchPerformances(searchPerformanceDTO, page, 'response');
  }
}
