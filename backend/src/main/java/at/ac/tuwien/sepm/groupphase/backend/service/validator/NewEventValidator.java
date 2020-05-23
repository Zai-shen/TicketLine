package at.ac.tuwien.sepm.groupphase.backend.service.validator;

import at.ac.tuwien.sepm.groupphase.backend.entity.Event;

public class NewEventValidator extends Validator<Event> {
    @Override
    protected void doValidation(Event event) {
        notEmptyOrName(event.getTitle(),"Titel");
        notNullOrName(event.getCategory(),"Kategorie");
        notNullOrName(event.getDescription(),"Beschreibung");
        notNullOrName(event.getDuration(),"Dauer");
        validOrMessage(event.getDuration() > 0,"Dauer muss positiv sein");
        notNullOrName(event.getDescription(),"Beschreibung");
    }
}
