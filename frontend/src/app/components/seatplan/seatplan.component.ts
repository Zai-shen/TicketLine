import {
  AfterViewInit,
  ChangeDetectorRef,
  Component, ElementRef,
  EventEmitter,
  Input,
  OnChanges,
  OnInit,
  Output,
  SimpleChanges, ViewChild
} from '@angular/core';
import * as d3 from 'd3';
import { cloneDeep, remove } from 'lodash-es';
import { MatDialog } from '@angular/material/dialog';
import { SelectStandingareaDialogComponent } from './select-standingarea-dialog/select-standingarea-dialog.component';
import { StandingAreaRenderDTO } from './entities/standing-area-render-dto';
import { StandingAreaSelection } from './entities/standing-area-selection';
import { SeatmapRenderData } from './entities/seatmap-render-data';
import { SeatRenderDTO } from './entities/seat-render-dto';
import { SeatLabelRenderDTO } from './entities/seat-label-render-dto';
import { SeatingAreaRenderDTO } from './entities/seating-area-render-dto';
import { BookingDTO, SeatmapOccupationDTO } from '../../../generated';
import { Observable } from 'rxjs';

@Component({
  selector: 'tl-seatplan',
  templateUrl: './seatplan.component.html',
  styleUrls: ['./seatplan.component.scss']
})
export class SeatplanComponent implements OnInit, AfterViewInit {

  @Input()
  seatmap: Observable<SeatmapOccupationDTO>;

  @Input()
  postfixId: string = '';

  @Input()
  preSelected: BookingDTO;

  @Output()
  selectedSeatsChanged: EventEmitter<SeatRenderDTO[]> = new EventEmitter<SeatRenderDTO[]>();

  @Output()
  selectedStandingAreaChanged: EventEmitter<StandingAreaSelection[]> = new EventEmitter<StandingAreaSelection[]>();

  seatmapInternal: SeatmapRenderData;

  wrapperSelection: d3.Selection<d3.BaseType, unknown, null, any>;

  @ViewChild('seatmap')
  seatmapVal: ElementRef;

  fullyInitialized = false;
  drawMapAfterFullInit = false;

  constructor(private readonly dialog: MatDialog,
    private readonly cd: ChangeDetectorRef) {
  }

  seatGroups: SeatingAreaRenderDTO[];
  standingAreas: StandingAreaRenderDTO[];
  selectedSeats: SeatRenderDTO[] = [];
  selectedStandingAreaPlaces: StandingAreaSelection[];

  ngOnInit(): void {
    this.seatmap.subscribe(
      (value: SeatmapOccupationDTO) => {
        this.initSeatmap(value);
      }
    );
  }

  ngAfterViewInit() {
    this.fullyInitialized = true;
    if (this.drawMapAfterFullInit) {
      this.drawMap();
      this.registerClickHandlers();
    }
  }

  public initSeatmap(seatmap: SeatmapOccupationDTO) {
    this.seatmapInternal = new SeatmapRenderData(seatmap, this.postfixId);
    this.seatGroups = this.seatmapInternal.seatGroupAreas || [];
    this.standingAreas = this.seatmapInternal.standingAreas || [];
    this.selectedStandingAreaPlaces = this.standingAreas.map((area) => {
      return { standingArea: area, selectedPositions: 0 };
    });

    if (!!this.preSelected?.fixedSeats) {
      const fixedIds = this.preSelected.fixedSeats.map((it) => it.id);
      this.seatGroups.forEach(
        (it) => {
          it.seats.filter((seat) => fixedIds.includes(seat.id)).forEach((seat) => {
            this.selectedSeats.push(seat);
            seat.reserved = false;
            seat.selected = true;
          });
        });
      this.selectedSeatsChanged.emit(this.selectedSeats);
    }

    if (!!this.preSelected?.freeSeats) {
      this.selectedStandingAreaChanged.emit(this.preSelected.freeSeats.map((selected) => {
        const area = this.standingAreas.find((it) => it.id === selected.seatGroupId);

        const selectedStandingIndex = this.selectedStandingAreaPlaces.findIndex(
          (selectedArea) => selectedArea.standingArea.id === selected.seatGroupId );

        this.selectedStandingAreaPlaces[selectedStandingIndex].selectedPositions = selected.amount;

        const standingAreaSelection = new StandingAreaSelection();
        if (area !== undefined) {
          standingAreaSelection.standingArea = area;
        }
        standingAreaSelection.selectedPositions = selected.amount;
        return standingAreaSelection;
      }));
    }

    if (this.fullyInitialized) {
      this.drawMap();
      this.registerClickHandlers();
    } else {
      this.drawMapAfterFullInit = true;
    }
  }

  drawMap() {
    this.wrapperSelection = d3.select(this.seatmapVal.nativeElement)
                              .append('svg')
                              .attr('id', 'seating-map' + this.postfixId)
                              .attr('class', 'svg-container')
                              .attr('width', '100%')
                              .attr('height', '500px');
    const board = this.wrapperSelection.append('g')
                      .attr('id', 'board' + this.postfixId)
                      .attr('width', '100%')
                      .attr('height', '500px')
                      .attr('board', 'true');
    this.wrapperSelection.call(d3.zoom()
                                 .extent([[0, 0], [500, 500]])
                                 .scaleExtent([0.2, 8])
                                 .on('zoom', () => board.attr('transform', d3.event.transform)));

    board.selectAll('rect')
         .data(this.seatGroups).enter()
         .append('rect').each(
      function (d) {
        d3.select(this)
          .attr('id', d.renderId)
          .attr('class', 'seatgroup')
          .attr('width', d.width)
          .attr('height', d.height)
          .attr('x', d.x)
          .attr('y', d.y)
          .attr('rx', 5)
          .attr('ry', 5);
      });
    this.wrapperSelection.select('#board' + this.postfixId).selectAll('g')
        .data(this.seatGroups).enter()
        .append('g').each(
      function (d) {
        d3.select(this)
          .attr('class', 'seat-container')
          .attr('id', d.renderId)
          .selectAll('rect')
          .data(d.seats || []).enter().append('circle').each(
          function (s: SeatRenderDTO) {
            d3.select(this)
              .attr('class', 'seat')
              .attr('cx', s.x + d.x)
              .attr('cy', s.y + d.y)
              .attr('id', s.renderId)
              .attr('r', s.radius)
              .attr('sold', s.sold)
              .attr('reserved', s.reserved)
              .attr('seat', true)
              .attr('selected', s.selected);
          }
        );
        d3.select(this).selectAll('rect').data(d.seatLabels || []).enter().append('text').each(
          function (l: SeatLabelRenderDTO) {
            d3.select(this)
              .attr('x', l.x + d.x)
              .attr('y', l.y + d.y)
              .attr('class', 'seat-label')
              .attr('text-anchor', 'middle')
              .attr('dominant-baseline', 'middle')
              .attr('font-size', l.size * 1.5)
              .text(l.text);
          });
      });

    const standingAreas = board.append('g').attr('id', 'standingAreas' + this.postfixId);
    standingAreas.selectAll('rect')
                 .data(this.standingAreas).enter()
                 .append('rect').each(
      function (d) {
        d3.select(this)
          .attr('class', 'standing-area')
          .attr('zoom-control', 'standing-area')
          .attr('id', d.renderId)
          .attr('width', d.width)
          .attr('height', d.height)
          .attr('fill', '#098d05')
          .attr('x', d.x)
          .attr('y', d.y)
          .attr('rx', 5)
          .attr('ry', 5)
          .attr('sold-out', (d.maxPeople - d.sold - d.reserved) <= 1);
      });
  }

  registerClickHandlers() {
    const allStandingAreasSelector = this.wrapperSelection.selectAll('.standing-area');
    allStandingAreasSelector.on('click',
      (standingArea: StandingAreaRenderDTO) => {
        this.onClickStandingGroup(standingArea);
      });
    const allSeatsSelector = this.wrapperSelection.selectAll('.seat');
    allSeatsSelector.on('click',
      (seat: SeatRenderDTO) => {
        this.onClickSeat(seat);
      });
  }

  onClickSeat(seat: SeatRenderDTO) {
    if (seat.sold || seat.reserved) {
      return;
    }
    seat.selected = !seat.selected;
    this.wrapperSelection.select('#' + seat.renderId)
        .attr('selected', seat.selected);
    if (seat.selected) {
      this.selectedSeats.push(seat);
    } else {
      remove(this.selectedSeats, seat);
    }
    this.selectedSeatsChanged.emit(this.selectedSeats);
  }

  onClickStandingGroup(standingArea: any) {
    this.wrapperSelection.select('#' + standingArea.renderId);
    const selectedStandingIndex = this.selectedStandingAreaPlaces.findIndex(
      (area) => area.standingArea.id === standingArea.id);
    const dialogRef = this.dialog.open(SelectStandingareaDialogComponent, {
      data: this.selectedStandingAreaPlaces[selectedStandingIndex],
      position: {
        left: d3.event.clientX + 'px',
        top: d3.event.clientY + 'px'
      },
      autoFocus: false
    });
    dialogRef.afterClosed().subscribe(
      () => {
        this.wrapperSelection.select('#' + standingArea.renderId)
            .attr('selected', this.selectedStandingAreaPlaces[selectedStandingIndex].selectedPositions > 0);
        this.selectedStandingAreaChanged.emit(this.selectedStandingAreaPlaces);
      });
  }
}
