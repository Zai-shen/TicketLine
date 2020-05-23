package at.ac.tuwien.sepm.groupphase.backend.service.validator;

import at.ac.tuwien.sepm.groupphase.backend.entity.Performance;

public class NewPerformanceValidator extends Validator<Performance> {
    @Override
    protected void doValidation(Performance performance) {

        notNullOrName(performance.getLocation(), "Ort");
        notNullOrName(performance.getLocation().getId(), "Ort muss zuerst gespeichert werden");
        notNullOrName(performance.getEvent(), "Event");
        notNullOrName(performance.getEvent().getId(), "Event muss zuerst gespeichert werden");
        notNullOrName(performance.getDateTime(), "Datum/Uhrzeit");
    }
}
