package at.ac.tuwien.sepm.groupphase.backend.service.validator;

import at.ac.tuwien.sepm.groupphase.backend.exception.BusinessValidationException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class PasswordValidatorTest {

    private static final String PASSWORD_EMPTY_MESSAGE = "Passwort ist leer";
    private static final String PASSWORD_TOO_SHORT_MESSAGE = "Das Passwort muss mindestens 8 Zeichen lang sein";

    @Test
    public void testWithValidPassword() {
        assertThatCode(() -> new PasswordValidator().build("1234567890")
            .build("abcdefgh")
            .build("#234o9n#ä--mxc qwe4234^^")
            .validate()).doesNotThrowAnyException();
    }

    @Test
    public void testWithEmptyValue() {
        assertThatThrowsExceptionWithMessage("", PASSWORD_EMPTY_MESSAGE, PASSWORD_TOO_SHORT_MESSAGE);
        assertThatThrowsExceptionWithMessage(" ", PASSWORD_EMPTY_MESSAGE, PASSWORD_TOO_SHORT_MESSAGE);
        assertThatThrowsExceptionWithMessage(" \t", PASSWORD_EMPTY_MESSAGE, PASSWORD_TOO_SHORT_MESSAGE);
    }

    @Test
    public void testWithShortPassword() {
        assertThatThrowsExceptionWithMessage("1234567", PASSWORD_TOO_SHORT_MESSAGE);
    }

    private void assertThatThrowsExceptionWithMessage(String password, String... messages) {
        Throwable thrown = catchThrowable(() -> new PasswordValidator().build(password).validate());

        assertThat(thrown).isExactlyInstanceOf(BusinessValidationException.class);
        BusinessValidationException businessValidationException = (BusinessValidationException) thrown;
        assertThat(businessValidationException.getValidationMessages()).containsExactly(messages);
    }
}
