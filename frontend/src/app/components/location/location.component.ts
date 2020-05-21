import {Component, OnInit, ViewChild} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ErrorMessageComponent } from '../error-message/error-message.component';
import {LocationApiService} from '../../../generated/api/location.api.service';
import {LocationDTO} from '../../../generated/model/locationDTO';
import {AddressDTO} from '../../../generated/model/addressDTO';
import {SearchLocationDTO} from '../../../generated/model/searchLocationDTO';
import {AuthService} from '../../services/auth.service';

@Component({
  selector: 'app-location',
  templateUrl: './location.component.html',
  styleUrls: ['./location.component.css']
})
export class LocationComponent implements OnInit {

  locations: LocationDTO[];
  searchForm: FormGroup;

  constructor(private formBuilder: FormBuilder, private locationApiService: LocationApiService,
              private authService: AuthService) { }

  @ViewChild(ErrorMessageComponent)
  private errorMessageComponent: ErrorMessageComponent;

  ngOnInit(): void {
    this.getLocationList();
    this.searchForm = this.formBuilder.group({
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

  isAdmin(): boolean {
    return this.authService.isAdminLoggedIn()
  }

  getLocationList() {
    this.locationApiService.getLocationList().subscribe(
      (locationDTO: LocationDTO[]) => {
        this.locations = locationDTO;
      },
    error => this.errorMessageComponent.defaultServiceErrorHandling(error));
  }

  searchLocations() {
      const searchLocationDTO: SearchLocationDTO = Object.assign({}, this.searchForm.value);

      this.locationApiService.searchLocations(searchLocationDTO).subscribe(
      (locationDTO: LocationDTO[]) => {
        this.locations = locationDTO;
      },
      error => this.errorMessageComponent.defaultServiceErrorHandling(error));
  }


}
