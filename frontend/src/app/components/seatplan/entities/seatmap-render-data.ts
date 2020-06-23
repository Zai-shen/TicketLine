import {
  SeatgroupOccupationDTO,
  SeatLabelDTO,
  SeatmapOccupationDTO, SeatOccupationDTO,
  StandingAreaOccupationDTO
} from '../../../../generated';
import { SeatingAreaRenderDTO } from './seating-area-render-dto';
import { StandingAreaRenderDTO } from './standing-area-render-dto';
import { SeatLabelRenderDTO } from './seat-label-render-dto';
import { SeatRenderDTO } from './seat-render-dto';

export class SeatmapRenderData {
  id: number;
  seatGroupAreas: SeatingAreaRenderDTO[];
  standingAreas: StandingAreaRenderDTO[];

  constructor(seatmap: SeatmapOccupationDTO, postfixId: string) {
    if (seatmap.seatGroupAreas != null) {
      this.seatGroupAreas = seatmap.seatGroupAreas.map(
        (seatgroup: SeatgroupOccupationDTO): SeatingAreaRenderDTO => {
          return {
            id: seatgroup.id || -1,
            x: seatgroup.x || 0,
            y: seatgroup.y || 0,
            height: seatgroup.height || 0,
            width: seatgroup.width || 0,
            name: seatgroup.name || '',
            seatLabels: this.mapSeatLabels(seatgroup),
            seats: this.mapSeats(seatgroup, postfixId),
            renderId: 'seatgroup-' + (seatgroup.id || -1) + postfixId,
          };
        });
    }
    if (seatmap.standingAreas != null) {
      this.standingAreas = seatmap.standingAreas.map(
        (standingArea: StandingAreaOccupationDTO): StandingAreaRenderDTO => {
          return {id: standingArea.id || -1,
            x: standingArea.x || 0,
            y: standingArea.y || 0,
            height: standingArea.height || 0,
            width: standingArea.width || 0,
            name: standingArea.name || '',
            maxPeople: standingArea.maxPeople || 0,
            price: standingArea.price || 0,
            sold: standingArea.sold || 0,
            selected: 0,
            reserved: standingArea.reserved || 0,
            soldOut: ((standingArea.maxPeople || 0 )
              - (standingArea.sold || 0)
              - (standingArea.reserved || 0)) <= 0,
            renderId: 'standing-area-' + (standingArea.id || -1) + postfixId,
          };
        });
    }
  }

  private mapSeatLabels(seatgroup: SeatgroupOccupationDTO): SeatLabelRenderDTO[] {
    if (seatgroup.seatLabels == null) {
      return [];
    }
    return seatgroup.seatLabels.map(
      (seatLabel: SeatLabelDTO): SeatLabelRenderDTO => {
        return {
          x: seatLabel.x || 0,
          y: seatLabel.y || 0,
          size: seatLabel.size || 0,
          text: seatLabel.text || ''
        };
      });
  }

  private mapSeats(seatgroup: SeatgroupOccupationDTO, postfixId: string): SeatRenderDTO[] {
    if (seatgroup == null || seatgroup.seats == null) {
      return [];
    }
    return seatgroup.seats.map(
      (seat: SeatOccupationDTO): SeatRenderDTO => {
        return {
          id: seat.id || -1,
          x: seat.x || 0,
          y: seat.y || 0,
          radius: seat.radius || 0,
          colLabel: seat.colLabel || '',
          rowLabel: seat.rowLabel || '',
          reserved: seat.reserved || false,
          sold: seat.sold || false,
          selected: false,
          renderId: 'seat-' + (seatgroup.id || -1) + '-' + (seat.id || -1) + postfixId,
          area: seatgroup,
        };
      });
  }
}
