import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ErrorMessageComponent } from '../error-message/error-message.component';
import { LocationApiService, LocationCreationDTO, LocationDTO } from '../../../generated';
import { Router } from '@angular/router';
import { SeatplannerComponent } from '../seatplanner/seatplanner.component';

@Component({
  selector: 'tl-create-location',
  templateUrl: './create-location.component.html',
  styleUrls: ['./create-location.component.css']
})
export class CreateLocationComponent implements OnInit {

  submitted: boolean = false;
  locationForm: FormGroup;

  @ViewChild(SeatplannerComponent)
  private seatPlanner: SeatplannerComponent;

  constructor(private formBuilder: FormBuilder, private locationApiService: LocationApiService,
    private router: Router) { }

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
      const locationCreatioDTO: LocationCreationDTO = Object.assign({seatmap: this.seatPlanner.getDTO()}, this.locationForm.value);
      this.locationApiService.createLocation(locationCreatioDTO).subscribe(
        () => {
          console.log('Location successfully created');
          this.router.navigate(['/location']);
        },
      error => this.errorMessageComponent.defaultServiceErrorHandling(error));
    }
  }

}
