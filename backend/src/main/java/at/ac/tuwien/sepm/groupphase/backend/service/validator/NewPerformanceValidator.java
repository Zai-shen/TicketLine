package at.ac.tuwien.sepm.groupphase.backend.service.validator;

import at.ac.tuwien.sepm.groupphase.backend.entity.Performance;

public class NewPerformanceValidator extends Validator<Performance> {
    @Override
    protected void doValidation(Performance performance) {
        notNullOrName(performance.getLocation(), "Ort");
        notNullOrName(performance.getEvent(), "Event");
        if(performance.getLocation() != null) {
            validOrMessage(performance.getLocation().getId() != null, "Ort muss zuerst gespeichert werden");
        }
        if(performance.getEvent() != null) {
            validOrMessage(performance.getEvent().getId() != null, "Event muss zuerst gespeichert werden");
        }
        notNullOrName(performance.getDateTime(), "Datum/Uhrzeit");
    }
}
