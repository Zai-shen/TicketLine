package at.ac.tuwien.sepm.groupphase.backend.service.validator;

import at.ac.tuwien.sepm.groupphase.backend.entity.Author;

public class NewAuthorValidator extends Validator<Author>{
    @Override
    protected void doValidation(Author author) {
        callValidatorOnChild(new EmailValidator(), author.getEmail());
        notEmptyOrName(author.getFirstName(), "First Name");
        notEmptyOrName(author.getLastName(), "Last Name");
    }
}
