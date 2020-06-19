import { SeatgroupOccupationDTO } from '../../../../generated/model/seatgroupOccupationDTO';

export interface SeatRenderDTO {
  area: SeatgroupOccupationDTO;
  id: number;
  x: number;
  y: number;
  rowLabel: string;
  colLabel: string;
  radius: number;
  reserved: boolean;
  sold: boolean;
  renderId: string;
  selected: boolean;
}
