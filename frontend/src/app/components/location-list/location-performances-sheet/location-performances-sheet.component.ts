import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { MAT_BOTTOM_SHEET_DATA, MatBottomSheetRef } from '@angular/material/bottom-sheet';
import { EventDTO, LocationApiService, LocationDTO, PerformanceDTO } from '../../../../generated';
import { Router } from '@angular/router';

@Component({
  selector: 'tl-location-performances-sheet',
  templateUrl: './location-performances-sheet.component.html',
  styleUrls: ['./location-performances-sheet.component.scss']
})
export class LocationPerformancesSheetComponent implements OnInit {
  private performances: PerformanceDTO[] = [];
  private loading = true;
  constructor(
    @Inject(MAT_BOTTOM_SHEET_DATA) public location: LocationDTO,
    private locationApiService: LocationApiService,
    private bottomSheetRef: MatBottomSheetRef<LocationPerformancesSheetComponent>,
    private router: Router
  ) {}

  ngOnInit(): void {
    if (this.location.id) {
      this.loading = true;
      this.locationApiService.getPerformancesOfLocation(this.location.id).subscribe(
        (performances: PerformanceDTO[]) => {
          this.performances = performances;
          this.loading = false;
        }
      );
    }
  }

  get performancesAvailable(): boolean {
    return this.performances.length > 0;
  }

  public navigateTo(target: EventDTO): void {
    this.bottomSheetRef.dismiss();
    this.router.navigate(['/events', target.id]);
  }
}
