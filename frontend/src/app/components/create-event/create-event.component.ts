import { Component, OnInit, ViewChild } from '@angular/core';
import { EventCategory, EventDTO, PerformanceDTO } from '../../../generated';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Location } from '@angular/common';
import { EventService } from '../../services/event.service';
import { PerformanceService } from '../../services/performance.service';
import { ErrorMessageComponent } from '../error-message/error-message.component';
import { forkJoin, Observable } from 'rxjs';

@Component({
  selector: 'tl-create-event',
  templateUrl: './create-event.component.html',
  styleUrls: ['./create-event.component.scss']
})
export class CreateEventComponent implements OnInit {
  performances: PerformanceDTO[] = [];
  eventForm: FormGroup;
  submitted: boolean = false;
  @ViewChild(ErrorMessageComponent)
  private errorMessageComponent: ErrorMessageComponent;

  constructor(private readonly formBuilder: FormBuilder,
              private readonly location: Location,
              private readonly performanceService: PerformanceService,
              private readonly eventService: EventService) {
  }

  ngOnInit(): void {
    this.eventForm = this.formBuilder.group({
      title: new FormControl(null, [Validators.required]),
      category: new FormControl(null, [Validators.required]),
      description: new FormControl(null, [Validators.required]),
      duration: new FormControl(null, [Validators.required, Validators.min(0)]),
    });
  }

  get eventCategories(): string[] {
    return Object.keys(EventCategory);
  }

  goBack() {
    this.location.back();
  }

  performancesUpdated(performances: PerformanceDTO[]) {
    this.performances = performances;
  }

  async createEvent() {
    this.submitted = true;
    if (this.eventForm.valid && this.performances != null && this.performances.length > 0) {
      this.eventService.createEvent(this.eventForm.value).subscribe(
        eventId => {
          const performancePromises: Observable<any>[] = [];
          this.performances.map(perf => performancePromises.push(this.performanceService.createPerformance(eventId, perf)));
          forkJoin(performancePromises).subscribe(
            () => this.goBack(),
            error => this.errorMessageComponent.defaultServiceErrorHandling(error));
        },
        error => this.errorMessageComponent.defaultServiceErrorHandling(error)
      );
    }
  }
}
