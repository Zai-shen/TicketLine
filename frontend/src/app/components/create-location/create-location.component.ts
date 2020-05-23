import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ErrorMessageComponent } from '../error-message/error-message.component';
import {LocationApiService} from '../../../generated';
import {LocationDTO} from '../../../generated';
import {AuthService} from '../../services/auth.service';

@Component({
  selector: 'tl-create-location',
  templateUrl: './create-location.component.html',
  styleUrls: ['./create-location.component.css']
})
export class CreateLocationComponent implements OnInit {

  submitted: boolean = false;
  locationForm: FormGroup;

  constructor(private formBuilder: FormBuilder, private locationApiService: LocationApiService,
                            private authService: AuthService) { }

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

  /**
   * Sends location creation request
   * @param locationDTO the location which should be created
   */
  createLocation() {
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
