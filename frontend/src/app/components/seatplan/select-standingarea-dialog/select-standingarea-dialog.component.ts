import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { StandingAreaSelection } from '../entities/standing-area-selection';

@Component({
  selector: 'tl-select-standingarea-dialog',
  templateUrl: './select-standingarea-dialog.component.html',
  styleUrls: ['./select-standingarea-dialog.component.scss']
})
export class SelectStandingareaDialogComponent implements OnInit {

  constructor(@Inject(MAT_DIALOG_DATA) private standingAreaSelection: StandingAreaSelection,
    private readonly dialogRef: MatDialogRef<SelectStandingareaDialogComponent>) { }

  ngOnInit(): void {
  }

  get availablePlaces() {
    const totalPlaces = this.standingAreaSelection.standingArea.maxPeople;
    const soldPlaces = this.standingAreaSelection.standingArea.sold;
    const reservedPlaces = this.standingAreaSelection.standingArea.sold;
    const selectedPlaces = this.standingAreaSelection.selectedPositions;
    return  totalPlaces - soldPlaces - reservedPlaces - selectedPlaces;
  }

  get placesAvailabe() {
    return this.availablePlaces > 0;
  }

  get placesSelected() {
    return this.standingAreaSelection.selectedPositions > 0;
  }

  add() {
    if (this.placesAvailabe) {
      this.standingAreaSelection.selectedPositions++;
    }
  }

  remove() {
    if (this.placesSelected) {
      this.standingAreaSelection.selectedPositions--;
    }
  }

  close() {
    this.dialogRef.close();
  }

}
