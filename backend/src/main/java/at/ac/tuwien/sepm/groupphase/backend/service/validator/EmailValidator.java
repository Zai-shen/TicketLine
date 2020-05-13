package at.ac.tuwien.sepm.groupphase.backend.service.validator;

public class EmailValidator extends Validator<String> {

    private static final String EMAIL_REGEX = "^\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";

    @Override
    protected void doValidation(String email) {
        notEmptyOrName(email, "E-Mail");
        if (email != null) {
            validOrMessage(email.matches(EMAIL_REGEX), "Ist keine valide E-Mail Adresse");
        }
    }
}
