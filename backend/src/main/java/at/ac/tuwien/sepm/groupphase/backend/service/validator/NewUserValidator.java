package at.ac.tuwien.sepm.groupphase.backend.service.validator;

import at.ac.tuwien.sepm.groupphase.backend.entity.User;

public class NewUserValidator extends Validator<User> {
    @Override
    protected void doValidation(User user) {
        notNullOrName(user.getEmail(), "E-Mail");
        callValidatorOnChild(new EmailValidator(), user.getEmail());
        notNullOrName(user.getPassword(), "Passwort");
        callValidatorOnChild(new PasswordValidator(), user.getPassword());
        notNullOrName(user.getAddress(), "Adresse");
        notEmptyOrName(user.getFirstname(), "Vorname");
        notEmptyOrName(user.getLastname(), "Nachname");
        callValidatorOnChild(new NewAddressValidator(), user.getAddress());
    }
}
