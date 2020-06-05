package at.ac.tuwien.sepm.groupphase.backend.service.validator;

import at.ac.tuwien.sepm.groupphase.backend.entity.SeatLabel;

public class SeatLabelValidator extends Validator<SeatLabel> {
    @Override
    protected void doValidation(SeatLabel seatLabel) {
        notNullOrName(seatLabel.getX(),"Sitzplatz-Label X-Koordinate");
        notNullOrName(seatLabel.getY(),"Sitzplatz-Label Y-Koordinate");
        notNullOrName(seatLabel.getSize(),"Sitzplatz-Label Größe");
        notNullOrName(seatLabel.getText(),"Sitzplatz-Label Text");
    }
}
