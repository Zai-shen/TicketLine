import interact from 'interactjs';
import { SeatplannerComponent } from './seatplanner.component';
import { Selection } from 'd3';
import { SeatDTO, SeatLabelDTO } from '../../../generated';

export abstract class SeatGroupChild {
  x: number;
  y: number;
  size: number;
  id: string;
  parent: SeatplannerComponent;
  rowNr: number;
  colNr: number;
  rowLabel: string;
  colLabel: string;
  selector: Selection<any, any, any, any>;

  constructor(parent: Selection<any, any, any, any>,
    x: number,
    y: number,
    size: number,
    rowNr: number,
    colNr: number,
    rowLabel: string,
    colLabel: string,
    id: string) {
    this.x = x;
    this.y = y;
    this.id = id;
    this.size = size;
    this.rowNr = rowNr;
    this.colNr = colNr;
    this.rowLabel = rowLabel;
    this.colLabel = colLabel;
  }
}

export class SeatElement extends SeatGroupChild {
  radius: number;

  constructor(parent: Selection<any, any, any, any>,
    x: number,
    y: number,
    r: number,
    rowNr: number,
    colNr: number,
    rowLabel: string,
    colLabel: string,
    id: string,
    onDeleteCallback: (item: SeatGroupChild) => void) {
    super(parent, x, y, r * 2, rowNr, colNr, rowLabel, colLabel, id);
    this.radius = r;
    this.selector = parent.append('circle')
                          .attr('id', id)
                          .attr('cx', x)
                          .attr('cy', y)
                          .attr('r', r)
                          .attr('stroke-width', r / 5)
                          .attr('stroke', 'gray')
                          .attr('fill', 'black');
    interact('#' + id)
      .on('doubletap', (event) => {
        this.selector.remove();
        onDeleteCallback(this);
      });
  }

  convertToSeatDto(price: number): SeatDTO {
    return {
      x: this.x,
      y: this.y,
      rowLabel: this.rowLabel,
      colLabel: this.colLabel,
      radius: this.radius,
      price: price
    };
  }
}

export class SeatLabel extends SeatGroupChild {
  constructor(parent: Selection<any, any, any, any>,
    x: number,
    y: number,
    size: number,
    rowNr: number,
    colNr: number,
    rowLabel: string,
    colLabel: string,
    id: string,
    private text: string,
    onDeleteCallback: (item: SeatGroupChild) => void) {
    super(parent, x, y, size, rowNr, colNr, rowLabel, colLabel, id);
    this.selector = parent.append('text')
                          .attr('id', id)
                          .attr('x', x)
                          .attr('y', y)
                          .attr('class', 'seat-label')
                          .attr('text-anchor', 'middle')
                          .attr('dominant-baseline', 'middle')
                          .attr('font-size', size * 1.5)
                          .text(text);
    interact('#' + id)
      .on('doubletap', (event) => {
        this.selector.remove();
        onDeleteCallback(this);
      });
  }
  convertToSeatLabelDto(): SeatLabelDTO {
    return {
      x: this.x,
      y: this.y,
      size: this.size,
      text: this.text
    };
  }

}
