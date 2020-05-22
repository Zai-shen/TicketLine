package at.ac.tuwien.sepm.groupphase.backend.service.validator;

import at.ac.tuwien.sepm.groupphase.backend.entity.User;

public class UpdateUserValidator extends Validator<User> {
    @Override
    protected void doValidation(User user) {
        notNullOrName(user.getAddress(), "Adresse");
        notEmptyOrName(user.getFirstname(), "Vorname");
        notEmptyOrName(user.getLastname(), "Nachname");
        callValidatorOnChild(new NewAddressValidator(), user.getAddress());
    }
}
