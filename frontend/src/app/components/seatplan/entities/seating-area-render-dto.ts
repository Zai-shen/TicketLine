import { SeatRenderDTO } from './seat-render-dto';
import { SeatLabelRenderDTO } from './seat-label-render-dto';

export interface SeatingAreaRenderDTO {
  id: number;
  x: number;
  y: number;
  width: number;
  height: number;
  name: string;
  seats: SeatRenderDTO[];
  renderId: string;
  seatLabels: SeatLabelRenderDTO[];
}
