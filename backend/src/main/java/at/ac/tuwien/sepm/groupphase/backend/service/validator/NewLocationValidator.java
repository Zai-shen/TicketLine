package at.ac.tuwien.sepm.groupphase.backend.service.validator;

import at.ac.tuwien.sepm.groupphase.backend.entity.Location;

public class NewLocationValidator extends Validator<Location> {
    @Override
    protected void doValidation(Location location) {
        notEmptyOrName(location.getDescription(),"Bezeichnung");
        validOrMessage(location.getDescription().length() <= 100, "Bezeichnung darf nicht größer als 100 Zeichen sein");
        notNullOrName(location.getAddress(),"Adresse");
        callValidatorOnChild(new AddressValidator(), location.getAddress());
    }
}
