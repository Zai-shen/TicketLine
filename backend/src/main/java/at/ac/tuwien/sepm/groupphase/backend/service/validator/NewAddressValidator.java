package at.ac.tuwien.sepm.groupphase.backend.service.validator;

import at.ac.tuwien.sepm.groupphase.backend.entity.Address;

public class NewAddressValidator extends Validator<Address> {
    @Override
    protected void doValidation(Address address) {
        notEmptyOrName(address.getStreet(), "Straße");
        notEmptyOrName(address.getHousenr(), "Hausnummer");
        notEmptyOrName(address.getPostalcode(), "PLZ");
        notEmptyOrName(address.getCity(), "Stadt");
        notEmptyOrName(address.getCountry(), "Staat");
    }
}
