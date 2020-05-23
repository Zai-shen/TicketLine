import { Component, OnInit, ViewChild } from '@angular/core';
import { EventCategory, EventDTO, PerformanceDTO } from '../../../generated';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Location } from '@angular/common';
import { MatTable } from '@angular/material/table';

@Component({
  selector: 'tl-create-event',
  templateUrl: './create-event.component.html',
  styleUrls: ['./create-event.component.scss']
})
export class CreateEventComponent implements OnInit {

  performances: PerformanceDTO[] = [];
  eventForm: FormGroup;
  event: EventDTO = {};
  submitted: boolean = false;

  @ViewChild(MatTable)
  private matTableComponent: MatTable<any>;

  constructor(private formBuilder: FormBuilder,
            private location: Location) {
  }

  ngOnInit(): void {
    this.eventForm = this.formBuilder.group({
      title: new FormControl(this.event.title, [Validators.required]),
      category: new FormControl(this.event.category, [Validators.required]),
      description: new FormControl(this.event.description, [Validators.required]),
      duration: new FormControl(this.event.duration, [Validators.required, Validators.min(0)]),
    });
   /* this.performances = [{location: { description: 'do' }, time: 'um ans', date: 'moang'},
      {location: { description: 'do' }, time: 'um zwa', date: 'iwamoang'},
      {location: { description: 'duat' }, time: 'um drei', date: 'späda'}];*/
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

  createEvent() {
    this.submitted = true;
  }

}
