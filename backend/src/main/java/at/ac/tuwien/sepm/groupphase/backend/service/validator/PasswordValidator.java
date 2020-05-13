package at.ac.tuwien.sepm.groupphase.backend.service.validator;

public class PasswordValidator extends Validator<String> {
    @Override
    protected void doValidation(String password) {
        notEmptyOrName(password, "Passwort");
        validOrMessage(password.length() >= 8, "Das Passwort muss mindestens 8 Zeichen lang sein");
    }
}
