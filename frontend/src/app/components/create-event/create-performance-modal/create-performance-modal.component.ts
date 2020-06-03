import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { AbstractControl, FormBuilder, FormControl, FormGroup, ValidatorFn, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { LocationApiService, LocationDTO, PerformanceDTO } from '../../../../generated';
import { map, startWith } from 'rxjs/operators';
import { Observable } from 'rxjs';
import { ErrorMessageComponent } from '../../error-message/error-message.component';
import { lowerCase } from 'lodash-es';
import { MatAutocompleteSelectedEvent } from '@angular/material/autocomplete';
import { MatDatepickerInputEvent } from '@angular/material/datepicker';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'tl-create-performance-modal',
  templateUrl: './create-performance-modal.component.html',
  styleUrls: ['./create-performance-modal.component.scss']
})
export class CreatePerformanceModalComponent implements OnInit {

  @ViewChild(ErrorMessageComponent)
  private errorMessageComponent: ErrorMessageComponent;

  performance: PerformanceDTO = {};
  selectedTime = '';

  performanceForm: FormGroup;
  submitted = false;
  isEditMode = false;

  locations: LocationDTO[];
  filteredLocations: Observable<LocationDTO[]>;
  minDate = new Date();
  wipDateTime: Date | undefined = new Date();

  constructor(@Inject(MAT_DIALOG_DATA) private readonly inputPerformance: PerformanceDTO,
    private readonly formBuilder: FormBuilder,
    private readonly dialogRef: MatDialogRef<CreatePerformanceModalComponent>,
    private readonly locationService: LocationApiService,
    private readonly datePipe: DatePipe) {
  }

  ngOnInit(): void {
    if (this.inputPerformance != null) {
      this.isEditMode = true;
      this.performance = this.inputPerformance;

      this.selectedTime = this.datePipe.transform(this.performance.dateTime, 'HH:mm') || '';
    }
    const locationControl = new FormControl(this.performance.location, [
      Validators.required,
      this.locationValidator()
    ]);
    this.performanceForm = new FormGroup({
      location: locationControl,
      date: new FormControl(this.performance.dateTime, [
        Validators.required
      ]),
      time: new FormControl(this.selectedTime, [
        Validators.required,
        Validators.pattern('\\d{1,2}:\\d{1,2}')
      ]),
    });

   this.locationService.getLocationList().subscribe(
      (locations: LocationDTO[]) => {
        this.locations = locations;
          this.filteredLocations = locationControl.valueChanges.pipe(
            startWith(''),
            map((value: any) => this.filterLocations(value || ''))
          );
      },
      (error: any) => this.errorMessageComponent.defaultServiceErrorHandling(error)
    );
  }

  locationSelected(event: MatAutocompleteSelectedEvent) {
    this.performance.location = event.option.value;
  }

  dateChange(event: MatDatepickerInputEvent<Date>) {
    this.wipDateTime = event.value ? event.value : undefined;
    this.performance.dateTime = event.value ? event.value.toISOString() : undefined;
    if (this.selectedTime != null) {
      this.setTimeOnDate(this.selectedTime);
    }
  }

  timeChange(time: string) {
    this.selectedTime = time;
    this.setTimeOnDate(time);
  }

  setTimeOnDate(time: string) {
    if (this.wipDateTime != null) {
      const splitTime = time.split(':');
      const hours = parseInt(splitTime[0], 10);
      const minutes = parseInt(splitTime[1], 10);
      if (this.wipDateTime != null && !isNaN(hours) && !isNaN(minutes)) {
        this.wipDateTime.setHours(hours, minutes);
      }
    }
  }

  private filterLocations(value: string): LocationDTO[] {
    if (value == null) {
      return this.locations;
    }
    const filterValue = lowerCase(value);
    return this.locations.filter(location => location.description != null && location.description.toLowerCase().includes(filterValue));
  }

  createPerformance() {
    this.performance.dateTime = this.wipDateTime?.toISOString();
    this.submitted = true;
    if (this.performanceForm.valid) {
      this.dialogRef.close(this.performance);
    }
  }

  locationValidator(): ValidatorFn {
      return (control: AbstractControl): { [key: string]: any } | null => {
        if (typeof control.value === 'string') {
          return { 'invalidAutocompleteObject': { value: control.value  }};
        }
        return null;
      };
  }

  displayLocation(location: LocationDTO): string {
    return location && location.description ? location.description : '';
  }

  get createButtonText() {
    return this.isEditMode ? 'Speichern' : 'Erstellen';
  }

  get dialogTitle() {
    return this.isEditMode ? 'Veranstaltung bearbeiten' : 'Veranstaltung hinzufügen';
  }
}
