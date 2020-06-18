import interact from 'interactjs';
import { SeatElement, SeatGroupChild, SeatLabel } from './seat';
import {Selection} from 'd3';
import { pull } from 'lodash-es';
import { SeatgroupDTO, StandingAreaDTO } from '../../../generated';

export class SeatgroupElement {

  position = { x: 0, y: 0 };
  width: number;
  height: number;
  id: string;
  private svgRect: Selection<any, any, any, any>;
  private group: Selection<any, any, any, any>;
  private rect: Selection<any, any, any, any>;
  private restrictElement: string;
  private seats: SeatGroupChild[] = [];

  public seatRadius: number = 5;
  public seatRowDistance: number = 1.4;
  public seatColDistance: number = 1.4;

  private seatRows = 0;
  private seatCols = 0;

  private selectionChangedCallback: (selected: SeatgroupElement) => void;
  private seatgroupRemoveCallback: (remove: SeatgroupElement) => void;

  private _price = 10;
  private _isStandingArea = true;
  private _standingPlaceCount = 10;
  name: string;

  constructor(id: string,
    name: string,
    parent: Selection<any, any, any, any>,
    x: number,
    y: number,
    width: number,
    height: number,
    restrictElement: string,
    selectionChangedCallback: (selected: SeatgroupElement) => void,
    seatgroupRemoveCallback: (remove: SeatgroupElement) => void) {
    this.position = { x: x, y: y };
    this.width = width;
    this.height = height;
    this.id = id;
    this.name = name;
    this.restrictElement = restrictElement;
    this.selectionChangedCallback = selectionChangedCallback;
    this.seatgroupRemoveCallback = seatgroupRemoveCallback;
    this.createDragResizeRect(parent, x, y, width, height, restrictElement);
  }

  get standingPlaceCount() {
    return this._standingPlaceCount;
  }

  set standingPlaceCount(standingPlaceCount: number) {
    if (standingPlaceCount != null && standingPlaceCount > 0) {
      this._standingPlaceCount = standingPlaceCount;
    }
  }

  get price() {
    return this._price;
  }

  set price(price: number) {
    if (price != null && price >= 0) {
      this._price = price;
    }
  }

  get isStandingArea() {
    return this._isStandingArea;
  }

  public setAsStandingArea() {
    this._isStandingArea = true;
    this.removeAllSeats();
  }

  public setAsSeatingArea() {
    this._isStandingArea = false;
    this.updateSeats(true);
  }

  private createDragResizeRect(parent: Selection<any, any, any, any>,
    x: number,
    y: number,
    width: number,
    height: number,
    restrictElement: string) {
    this.svgRect = parent.append('svg');
    this.group = this.svgRect.append('g')
                     .attr('id', this.id)
                     .attr('class', 'resize-drag');
    this.rect = this.group.append('rect')
                    .attr('x', x)
                    .attr('y', y)
                    .attr('width', width)
                    .attr('height', height)
                    .attr('stroke-width', 2)
                    .attr('stroke', 'red')
                    .attr('fill', 'transparent');
    this.registerInteractions();
  }

  removeSeatgroup() {
    this.unregisterInteractions();
    this.removeAllSeats();
    this.svgRect.remove();
    this.seatgroupRemoveCallback(this);
  }

  unregisterInteractions() {
    interact('#' + this.id).unset();
  }

  registerInteractions() {
    interact('#' + this.id)
      .draggable({
        listeners: {
          start: (event) => {
            this.position.x = +this.svgRect.attr('x');
            this.position.y = +this.svgRect.attr('y');
          },
          move: (event) => {
            this.position.x += event.dx;
            this.position.y += event.dy;
            this.svgRect.attr('x', this.position.x).attr('y', this.position.y);
          }
        },
        modifiers: [interact.modifiers.restrict({ restriction: this.restrictElement })]
      })
      .resizable({
        edges: { left: true, right: true, bottom: true, top: true },
        modifiers: [interact.modifiers.restrict({ restriction: this.restrictElement })]
      })
      .on('resizemove', (event) => {
        for (const attr of ['width', 'height']) {
          let v = Number(this.rect.attr(attr));
          v += event.deltaRect[attr];
          this.rect.attr(attr, v);
        }
        for (const attr of ['top', 'left']) {
          const a = attr === 'left' ? 'x' : 'y';
          let v = Number(this.svgRect.attr(a));
          v += event.deltaRect[attr];
          this.svgRect.attr(a, v);
        }
        this.position.x = Number(this.svgRect.attr('x'));
        this.position.y = Number(this.svgRect.attr('y'));
        this.width = Number(this.rect.attr('width'));
        this.height = Number(this.rect.attr('height'));
        this.updateSeats();
      })
      .on('down', (event) => {
        this.selectionChangedCallback(this);
      });
  }

  updateSeats(fullUpdate?: boolean) {
    if (this.isStandingArea) {
      return;
    }
    if (this.seatRadius <= 0 || this.seatColDistance <= 0 || this.seatRowDistance <=0) {
      return;
    }
    const diameter = this.seatRadius * 2;
    const rows = Math.floor(this.height / (diameter * this.seatRowDistance));
    const cols = Math.floor(this.width / (diameter * this.seatColDistance));

    const addSeats = this.seatRows < rows ||
      this.seatCols < cols;
    const removeSeats = this.seatRows > rows ||
      this.seatCols > cols;
    const oldSeatRows = this.seatRows;
    const oldSeatCols = this.seatCols;
    this.seatRows = rows;
    this.seatCols = cols;
    if (fullUpdate) {
      this.fullRedrawSeats();
    } else if (addSeats) {
      for (let row = 1; row <= rows; row++) {
        for (let col = 1; col <= cols; col++) {
          if (row > oldSeatRows || col > oldSeatCols) {
            this.createChildElementForRowCol(row, col);
          }
        }
      }
    } else if (removeSeats) {
      const seatsToRemove = this.seats.filter(seat => seat.rowNr > rows || seat.colNr > cols);
      seatsToRemove.forEach(seat => seat.selector.remove());
      pull(this.seats, ...seatsToRemove);
    }
  }

  setSelected() {
    this.rect.attr('stroke', 'green');
  }

  unsetSelected() {
    this.rect.attr('stroke', 'red');
  }

  private fullRedrawSeats() {
    this.removeAllSeats();
    for (let row = 1; row <= this.seatRows; row++) {
      for (let col = 1; col <= this.seatCols; col++) {
        this.createChildElementForRowCol(row, col);
      }
    }
  }
  private removeAllSeats() {
    this.seats.forEach(seat => seat.selector.remove());
    this.seats.length = 0;
  }

  createChildElementForRowCol(row: number, col: number) {
    const rowLabel = this.getStringIdFromNumber(row - 1);
    const colLabel = (col - 1).toString();
    if (row === 1 && col === 1) {
      // nothing in the upper left corner
    } else if (row === 1) {
      this.seats.push(this.createSeatLabel(this.group,
        col * (this.seatRadius * 2 * this.seatColDistance) - this.seatRadius,
        row * (this.seatRadius * 2 * this.seatRowDistance) - this.seatRadius,
        this.seatRadius,
        row,
        col,
        rowLabel,
        colLabel,
        this.id + '-' +
        rowLabel + '-' + colLabel,
        colLabel));
    } else if (col === 1) {
      this.seats.push(this.createSeatLabel(this.group,
        col * (this.seatRadius * 2 * this.seatColDistance) - this.seatRadius,
        row * (this.seatRadius * 2 * this.seatRowDistance) - this.seatRadius,
        this.seatRadius,
        row,
        col,
        rowLabel,
        colLabel,
        this.id + '-' +
        rowLabel + '-' + colLabel,
        rowLabel));
    } else {
      this.seats.push(this.createSeat(this.group,
        col * (this.seatRadius * 2 * this.seatColDistance) - this.seatRadius,
        row * (this.seatRadius * 2 * this.seatRowDistance) - this.seatRadius,
        this.seatRadius,
        row,
        col,
        rowLabel,
        colLabel,
        this.id + '-' +
        rowLabel + '-' + colLabel));
    }
  }

  createSeat(parent: Selection<any, any, any, any>,
    x: number,
    y: number,
    radius: number,
    row: number,
    col: number,
    rowLabel: string,
    colLabel: string,
    id: string): SeatElement {
    return new SeatElement(this.group,
      x,
      y,
      radius,
      row,
      col,
      rowLabel,
      colLabel,
      id,
      (item) => this.removeSeatChild(item)
    );
  }

  createSeatLabel(parent: Selection<any, any, any, any>,
    x: number,
    y: number,
    radius: number,
    row: number,
    col: number,
    rowLabel: string,
    colLabel: string,
    id: string,
    text: string): SeatLabel {
    return new SeatLabel(this.group,
      x,
      y,
      radius,
      row,
      col,
      rowLabel,
      colLabel,
      id,
      text,
      (item) => this.removeSeatChild(item)
    );
  }

  removeSeatChild(item: SeatGroupChild) {
    const index = this.seats.findIndex(seat => seat.id === item.id);
    this.seats.splice(index, 1);
  }

  getStringIdFromNumber(num: number): string {
    let result = '';
    let workNumber = num;
    while (workNumber > 0) {
      workNumber--;
      const remaining = (workNumber % 26);
      result = String.fromCharCode(97 + remaining) + result;
      workNumber = (workNumber - remaining) / 26;
    }
    return result;
  }

  convertToDTO(): SeatgroupDTO | StandingAreaDTO {
    if (this.isStandingArea) {
      return {
        x: this.position.x,
        y: this.position.y,
        width: this.width,
        height: this.height,
        name: this.name,
        maxPeople: this.standingPlaceCount,
        price: this.price
      };
    } else {
      return {
        x: this.position.x,
        y: this.position.y,
        width: this.width,
        height: this.height,
        name: this.name,
        seats: this.seats.filter(seat => seat instanceof SeatElement).map(seat => (seat as SeatElement).convertToSeatDto(this.price)),
        seatLabels: this.seats.filter(seat => seat instanceof SeatLabel).map(seat => (seat as SeatLabel).convertToSeatLabelDto())
      };
    }
  }

}
