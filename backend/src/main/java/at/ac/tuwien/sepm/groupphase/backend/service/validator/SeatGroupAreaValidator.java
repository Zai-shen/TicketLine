package at.ac.tuwien.sepm.groupphase.backend.service.validator;

import at.ac.tuwien.sepm.groupphase.backend.entity.SeatGroupArea;

public class SeatGroupAreaValidator extends Validator<SeatGroupArea> {
    @Override
    protected void doValidation(SeatGroupArea seatGroupArea) {
        notNullOrName(seatGroupArea.getX(),"Sitzgruppe X-Koordinate");
        notNullOrName(seatGroupArea.getY(),"Sitzgruppe Y-Koordinate");
        notNullOrName(seatGroupArea.getWidth(),"Sitzgruppe Höhe");
        notNullOrName(seatGroupArea.getHeight(),"Sitzgruppe Breite");
        notNullOrName(seatGroupArea.getName(),"Sitzgruppe Name");
        callValidatorOnChildColleciton(new SeatValidator(), seatGroupArea.getSeats());
        callValidatorOnChildColleciton(new SeatLabelValidator(), seatGroupArea.getSeatLabels());
    }
}
