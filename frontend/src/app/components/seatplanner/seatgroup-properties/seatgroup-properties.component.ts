import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { SeatgroupElement } from '../seatgroup';

@Component({
  selector: 'tl-seatgroup-properties',
  templateUrl: './seatgroup-properties.component.html',
  styleUrls: ['./seatgroup-properties.component.scss']
})
export class SeatgroupPropertiesComponent implements OnInit, OnChanges {

  @Input()
  seatGroup: SeatgroupElement;

  seatRadius: number;
  seatRowDistance: number;
  seatColDistance: number;
  isStandingArea: boolean;

  constructor() { }

  ngOnInit(): void {
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['seatGroup'] != null && changes['seatGroup'].currentValue != null) {
      this.seatRadius = changes['seatGroup'].currentValue.seatRadius;
      this.seatRowDistance = changes['seatGroup'].currentValue.seatRowDistance;
      this.seatColDistance = changes['seatGroup'].currentValue.seatColDistance;
      this.isStandingArea = changes['seatGroup'].currentValue.isStandingArea;
    }
  }

  updateSeatgroup() {
    this.seatGroup.seatRadius = this.seatRadius;
    this.seatGroup.seatRowDistance = this.seatRowDistance;
    this.seatGroup.seatColDistance = this.seatColDistance;
    this.seatGroup.updateSeats(true);
  }

  get seatGroupPropertiesSelectedIndex() {
    return this.seatGroup.isStandingArea ? 1 : 0;
  }

  set seatGroupPropertiesSelectedIndex(index) {
    if (index === 1) {
      if (this.seatGroup.isStandingArea) {
        return;
      }
      this.seatGroup.setAsStandingArea();
    } else {
      if (!this.seatGroup.isStandingArea) {
        return;
      }
      this.seatGroup.setAsSeatingArea();
    }
  }

  removeSeatgroup() {
    this.seatGroup.removeSeatgroup();
  }
}
