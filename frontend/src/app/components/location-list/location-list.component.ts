import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ErrorMessageComponent } from '../error-message/error-message.component';
import { LocationApiService, LocationDTO, SearchLocationDTO } from '../../../generated';
import { AuthService } from '../../services/auth.service';
import { MatBottomSheet } from '@angular/material/bottom-sheet';
import { LocationPerformancesSheetComponent } from './location-performances-sheet/location-performances-sheet.component';

@Component({
  selector: 'tl-location',
  templateUrl: './location-list.component.html',
  styleUrls: ['./location-list.component.css']
})
export class LocationListComponent implements OnInit {

  locations: LocationDTO[] = [];
  searchForm: FormGroup;

  constructor(private formBuilder: FormBuilder, private locationApiService: LocationApiService,
              private authService: AuthService, private bottomSheet: MatBottomSheet) { }

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
    return this.authService.isAdminLoggedIn();
  }

  getLocationList(): void {
    this.locationApiService.getLocationList().subscribe(
      (locationDTO: LocationDTO[]) => {
        this.locations = locationDTO;
      },
    error => this.errorMessageComponent.defaultServiceErrorHandling(error));
  }

  searchLocations(): void {
      const searchLocationDTO: SearchLocationDTO = Object.assign({}, this.searchForm.value);

      this.locationApiService.searchLocations(searchLocationDTO).subscribe(
      (locationDTO: LocationDTO[]) => {
        this.locations = locationDTO;
      },
      error => this.errorMessageComponent.defaultServiceErrorHandling(error));
  }

  openDetails(location: LocationDTO): void {
    this.bottomSheet.open(LocationPerformancesSheetComponent,{
      data: location
    });
  }


}
