export interface StandingAreaRenderDTO {
  id: number;
  x: number;
  y: number;
  width: number;
  height: number;
  name: string;
  maxPeople: number;
  price: number;
  sold: number;
  reserved: number;
  renderId: string;
  soldOut: boolean;
  selected: number;
}
