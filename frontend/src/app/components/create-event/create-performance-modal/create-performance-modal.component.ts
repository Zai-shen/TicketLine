import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { AbstractControl, FormBuilder, FormControl, FormGroup, ValidatorFn, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { LocationApiService, LocationDTO, PerformanceDTO } from '../../../../generated';
import { map, startWith } from 'rxjs/operators';
import { Observable } from 'rxjs';
import { ErrorMessageComponent } from '../../error-message/error-message.component';
import { lowerCase } from 'lodash-es';
import { MatAutocompleteSelectedEvent } from '@angular/material/autocomplete';
import { MatDatepickerInput, MatDatepickerInputEvent } from '@angular/material/datepicker';

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

  locationControl = new FormControl(this.performance.location, [
    Validators.required,
    this.locationValidator()
  ]);

  performanceForm = new FormGroup({
    location: this.locationControl,
    date: new FormControl(this.performance.date, [
      Validators.required
    ]),
    time: new FormControl(this.selectedTime, [
      Validators.required,
      Validators.pattern('\\d{1,2}:\\d{1,2}')
    ]),
  });
  submitted = false;
  isEditMode = false;

  locations: LocationDTO[];
  filteredLocations: Observable<LocationDTO[]>;
  minDate = new Date();

  constructor(@Inject(MAT_DIALOG_DATA) private readonly inputPerformance: PerformanceDTO,
    private readonly formBuilder: FormBuilder,
    private readonly dialogRef: MatDialogRef<CreatePerformanceModalComponent>,
    private readonly locationService: LocationApiService) {
  }

  ngOnInit(): void {
    if (this.inputPerformance != null) {
      this.isEditMode = true;
      this.performance = this.inputPerformance;
      this.performanceForm.setValue({
        location: this.performance.location,
        date: this.performance.date,
        time: this.performance.date?.getHours() + ':' + this.performance.date?.getMinutes()
      });
    }

   this.locationService.getLocationList().subscribe(
      locations => {
        this.locations = locations;
          this.filteredLocations = this.locationControl.valueChanges.pipe(
            startWith(''),
            map(value => this.filterLocations(value || ''))
          );
      },
      error => this.errorMessageComponent.defaultServiceErrorHandling(error)
    );
  }

  locationSelected(event: MatAutocompleteSelectedEvent) {
    this.performance.location = event.option.value;
  }

  dateChange(event: MatDatepickerInputEvent<Date>) {
    this.performance.date = event.value ? event.value : undefined;
    if (this.selectedTime != null && this.performance.date != null) {
      this.setTimeOnDate(this.selectedTime);
    }
  }

  timeChange(time: string) {
    this.selectedTime = time;
    this.setTimeOnDate(time);
  }

  setTimeOnDate(time: string) {
    const splitTime = time.split(':');
    const hours = parseInt(splitTime[0], 10);
    const minutes = parseInt(splitTime[1], 10);
    if (this.performance.date != null && !isNaN(hours) && !isNaN(minutes)) {
      this.performance.date.setHours(hours, minutes);
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
