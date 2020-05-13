package at.ac.tuwien.sepm.groupphase.backend.service.validator;

import at.ac.tuwien.sepm.groupphase.backend.entity.User;
import at.ac.tuwien.sepm.groupphase.backend.exception.BusinessValidationException;
import at.ac.tuwien.sepm.groupphase.backend.util.DomainTestObjectFactory;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class NewUserValidatorTest {

    @Test
    public void testWithValidUser() {
        final User user = DomainTestObjectFactory.getUser();
        assertThatCode(() -> new NewUserValidator().build(user).validate()).doesNotThrowAnyException();
    }

    @Test
    public void testWithNullValues() {
        final User user = new User();
        Throwable thrown = catchThrowable(() -> new NewUserValidator().build(user).validate());

        assertThat(thrown).isExactlyInstanceOf(BusinessValidationException.class);
        BusinessValidationException businessValidationException = (BusinessValidationException) thrown;
        assertThat(businessValidationException.getValidationMessages()).containsExactly("E-Mail ist nicht gesetzt",
            "Passwort ist nicht gesetzt", "Adresse ist nicht gesetzt", "Vorname ist leer", "Nachname ist leer");
    }
}
