import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ErrorMessageComponent } from '../error-message/error-message.component';
import { LocationApiService, LocationDTO } from '../../../generated';

@Component({
  selector: 'tl-create-location',
  templateUrl: './create-location.component.html',
  styleUrls: ['./create-location.component.css']
})
export class CreateLocationComponent implements OnInit {

  submitted: boolean = false;
  locationForm: FormGroup;

  constructor(private formBuilder: FormBuilder, private locationApiService: LocationApiService) { }

  @ViewChild(ErrorMessageComponent)
  private errorMessageComponent: ErrorMessageComponent;

  ngOnInit(): void {
    this.locationForm = this.formBuilder.group({
      description: ['', Validators.required],
          address: this.formBuilder.group({
            street: ['', Validators.required],
            housenr: ['', Validators.required],
            postalcode: ['', Validators.required],
            city: ['', Validators.required],
            country: ['', Validators.required]
          })
      });
  }

  createLocation(): void {
    this.submitted = true;
    if (this.locationForm.valid) {
      const locationDTO: LocationDTO = Object.assign({}, this.locationForm.value);
      this.locationApiService.createLocation(locationDTO).subscribe(
        () => {
          console.log('Location successfully created');
        },
      error => this.errorMessageComponent.defaultServiceErrorHandling(error));
    }
  }

}
