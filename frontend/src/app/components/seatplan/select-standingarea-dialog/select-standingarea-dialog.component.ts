import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { StandingAreaRenderDTO } from '../entities/standing-area-render-dto';

@Component({
  selector: 'tl-select-standingarea-dialog',
  templateUrl: './select-standingarea-dialog.component.html',
  styleUrls: ['./select-standingarea-dialog.component.scss']
})
export class SelectStandingareaDialogComponent implements OnInit {

  constructor(@Inject(MAT_DIALOG_DATA) private standingAreaRenderDTO: StandingAreaRenderDTO,
    private readonly dialogRef: MatDialogRef<SelectStandingareaDialogComponent>) { }

  ngOnInit(): void {
  }

  get availablePlaces() {
    const totalPlaces = this.standingAreaRenderDTO.maxPeople;
    const soldPlaces = this.standingAreaRenderDTO.sold;
    const reservedPlaces = this.standingAreaRenderDTO.reserved;
    const selectedPlaces = this.standingAreaRenderDTO.selected;
    return  totalPlaces - soldPlaces - reservedPlaces - selectedPlaces;
  }

  get placesAvailabe() {
    return this.availablePlaces > 0;
  }

  get placesSelected() {
    return this.standingAreaRenderDTO.selected > 0;
  }

  add() {
    if (this.placesAvailabe) {
      this.standingAreaRenderDTO.selected++;
    }
  }

  remove() {
    if (this.placesSelected) {
      this.standingAreaRenderDTO.selected--;
    }
  }

  close() {
    this.dialogRef.close();
  }

}
