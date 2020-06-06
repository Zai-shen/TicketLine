import { Component, Input, OnInit } from '@angular/core';
import * as d3 from 'd3';
import { Selection } from 'd3';

import '@interactjs/types';
import { SeatgroupElement } from './seatgroup';
import {remove} from 'lodash-es';
import { SeatmapDTO } from '../../../generated';

@Component({
  selector: 'tl-seatplanner',
  templateUrl: './seatplanner.component.html',
  styleUrls: ['./seatplanner.component.scss']
})
export class SeatplannerComponent implements OnInit {

  @Input()
  width = 500;

  @Input()
  height = 500;

  seatGroups: SeatgroupElement[] = [];
  svgSelector: Selection<any, any, any, any>;
  boardSelector: Selection<any, any, any, any>;

  selectedSeatgroup: SeatgroupElement;

  seatGroupCounter = 0;

  constructor() {
  }

  ngOnInit(): void {
    this.svgSelector = d3.select('#planner-svg')
                         .attr('stroke', 'black')
                         .attr('width', this.width)
                         .attr('height', this.height)
                         .attr('stroke-width', 1);
    this.boardSelector = d3.select('#planner-board');
    this.boardSelector.append('rect')
        .attr('fill', 'transparent')
        .attr('width', this.width)
        .attr('height', this.height);
  }

  addSeatGroup() {
    const seatGroupId = 'Bereich-' + this.seatGroupCounter++;
    const newSeatgroup = new SeatgroupElement(
      seatGroupId,
      seatGroupId,
      this.boardSelector,
      1,
      1,
      100,
      100,
      '#planner-svg',
      (sel) => this.changeSelectedSeatgroup(sel),
      (rem) => this.removeSelectedSeatgroup(rem));
    this.seatGroups.push(newSeatgroup);
    this.changeSelectedSeatgroup(newSeatgroup);
  }

  removeSelectedSeatgroup(removeElement: SeatgroupElement) {
    remove(this.seatGroups, (group: SeatgroupElement) => group.id === removeElement.id);
  }

  changeSelectedSeatgroup(selection: SeatgroupElement) {
    if (this.selectedSeatgroup != null) {
      this.selectedSeatgroup.unsetSelected();
    }
    this.selectedSeatgroup = selection;
    if (this.selectedSeatgroup != null) {
      this.selectedSeatgroup.setSelected();
    }
  }

  getDTO(): SeatmapDTO {
    return {
      seatGroupAreas: this.seatGroups.filter(group => !group.isStandingArea).map(group => group.convertToDTO()),
      standingAreas: this.seatGroups.filter(group => group.isStandingArea).map(group => group.convertToDTO())
    };
  }
}
