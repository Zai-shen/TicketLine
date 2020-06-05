package at.ac.tuwien.sepm.groupphase.backend.service.validator;

import at.ac.tuwien.sepm.groupphase.backend.entity.StandingArea;

public class StandingAreaValidator extends Validator<StandingArea> {
    @Override
    protected void doValidation(StandingArea standingArea) {
        notNullOrName(standingArea.getX(),"Stehplätze X-Koordinate");
        notNullOrName(standingArea.getY(),"Stehplätze Y-Koordinate");
        notNullOrName(standingArea.getName(),"Stehplätze Name");
        notNullOrName(standingArea.getPrice(),"Stehplätze Preis");
        notNullOrName(standingArea.getMaxPeople(),"Stehplätze Personenanzahl");
        validOrMessage(standingArea.getPrice() >= 0,"Stehplätze Preis muss positiv sein");
        validOrMessage(standingArea.getMaxPeople() > 0, "anzahl der Stehplätze muss > 0 sein");
    }
}
