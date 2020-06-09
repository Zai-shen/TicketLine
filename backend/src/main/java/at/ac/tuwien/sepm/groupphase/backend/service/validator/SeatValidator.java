package at.ac.tuwien.sepm.groupphase.backend.service.validator;

import at.ac.tuwien.sepm.groupphase.backend.entity.Seat;

public class SeatValidator extends Validator<Seat> {
    @Override
    protected void doValidation(Seat seat) {
        notNullOrName(seat.getX(),"Sitzplatz X-Koordinate");
        notNullOrName(seat.getY(),"Sitzplatz Y-Koordinate");
        notNullOrName(seat.getRadius(),"Sitzplatz Größe");
        notNullOrName(seat.getRowLabel(),"Sitzplatzreihe-Bezeichnung");
        notNullOrName(seat.getColLabel(),"Sitzplatzspalte-Bezeichnung");
        notNullOrName(seat.getPrice(),"Sitzplatz Preis");
        validOrMessage(seat.getPrice() >= 0,"Sitplatz Preis muss positiv sein");
        validOrMessage(seat.getRadius() > 0,"Sitzplatz Größe muss positiv sein");
    }
}
