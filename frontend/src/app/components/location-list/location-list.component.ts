import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ErrorMessageComponent } from '../error-message/error-message.component';
import { LocationApiService, LocationDTO, SearchLocationDTO } from '../../../generated';
import { AuthService } from '../../services/auth.service';
import { MatBottomSheet } from '@angular/material/bottom-sheet';
import { LocationPerformancesSheetComponent } from './location-performances-sheet/location-performances-sheet.component';
import { MatPaginator, PageEvent } from '@angular/material/paginator';

@Component({
  selector: 'tl-location',
  templateUrl: './location-list.component.html',
  styleUrls: ['./location-list.component.css']
})
export class LocationListComponent implements OnInit {

  readonly LIST_PAGE_SIZE = 25;
  locations: LocationDTO[];
  searchForm: FormGroup;
  private currentPage = 0;
  searched: boolean;
  locationsFound = 0;

  constructor(private formBuilder: FormBuilder, private locationApiService: LocationApiService,
              private authService: AuthService, private bottomSheet: MatBottomSheet) { }

  @ViewChild(MatPaginator)
  private paginator: MatPaginator;

  @ViewChild(ErrorMessageComponent)
  private errorMessageComponent: ErrorMessageComponent;

  ngOnInit(): void {
    this.getLocationList();
    this.searchForm = this.formBuilder.group({
      description: [''],
          address: this.formBuilder.group({
            street: [''],
            housenr: [''],
            postalcode: [''],
            city: [''],
            country: ['']
          })
      });
  }

    onPaginationChange(event: PageEvent): void {
      this.currentPage = event.pageIndex;
      if (this.searched) {
        this.searchLocations();
      } else {
        this.getLocationList();
      }
    }

  isAdmin(): boolean {
    return this.authService.isAdminLoggedIn();
  }

  getLocationList(): void {
    this.locationApiService.getLocationList(this.currentPage, 'response').subscribe(locations => {
      if (locations.body != null) {
        this.locations = locations.body;
        this.locationsFound = Number(locations.headers.get('X-Total-Count')) || 0;
      }
    },
    error => this.errorMessageComponent.defaultServiceErrorHandling(error));
  }

  searchLocations(): void {
      const searchLocationDTO: SearchLocationDTO = Object.assign({}, this.searchForm.value);
      this.searched = true;

      this.locationApiService.searchLocations(searchLocationDTO, this.currentPage, 'response').subscribe(locations => {
        if (locations.body != null) {
          this.locations = locations.body;
          this.locationsFound = Number(locations.headers.get('X-Total-Count')) || 0;
        }
      },
      error => this.errorMessageComponent.defaultServiceErrorHandling(error));
  }

  openDetails(location: LocationDTO): void {
    this.bottomSheet.open(LocationPerformancesSheetComponent,{
      data: location
    });
  }


}
