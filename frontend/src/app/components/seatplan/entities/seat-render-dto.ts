export interface SeatRenderDTO {
  id: number;
  x: number;
  y: number;
  rowLabel: string;
  colLabel: string;
  radius: number;
  price: number;
  reserved: boolean;
  sold: boolean;
  renderId: string;
  selected: boolean;
}
