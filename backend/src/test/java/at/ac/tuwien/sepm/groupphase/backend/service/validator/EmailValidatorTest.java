package at.ac.tuwien.sepm.groupphase.backend.service.validator;

import at.ac.tuwien.sepm.groupphase.backend.exception.BusinessValidationException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EmailValidatorTest {

    private static final String EMAIL_EMPTY_MESSAGE = "E-Mail ist leer";
    private static final String WRONG_EMAIL_FORMAT_MESSAGE = "Ist keine valide E-Mail Adresse";

    @Test
    public void testWithValidEmail() {
        assertThatCode(() -> new EmailValidator().build("test@example.com")
            .build("my-test@this-is-an-example.wien")
            .build("another.test@this.should.be.ok")
            .build("nAmb3rs@work.too")
            .validate()).doesNotThrowAnyException();
    }

    @Test
    public void testWithNullValue() {
        assertThatThrowsExceptionWithMessage(null, EMAIL_EMPTY_MESSAGE);
    }

    @Test
    public void testWithEmptyValue() {
        assertThatThrowsExceptionWithMessage("", EMAIL_EMPTY_MESSAGE, WRONG_EMAIL_FORMAT_MESSAGE);
        assertThatThrowsExceptionWithMessage(" ", EMAIL_EMPTY_MESSAGE, WRONG_EMAIL_FORMAT_MESSAGE);
        assertThatThrowsExceptionWithMessage("\t", EMAIL_EMPTY_MESSAGE, WRONG_EMAIL_FORMAT_MESSAGE);
    }

    @Test
    public void testWithInvalidEmailAddresses() {
        assertThatThrowsExceptionWithMessage("@", WRONG_EMAIL_FORMAT_MESSAGE);
        assertThatThrowsExceptionWithMessage("@test.com", WRONG_EMAIL_FORMAT_MESSAGE);
        assertThatThrowsExceptionWithMessage("test.tuwien.at", WRONG_EMAIL_FORMAT_MESSAGE);
        assertThatThrowsExceptionWithMessage("whatatest!@ok.com", WRONG_EMAIL_FORMAT_MESSAGE);
        assertThatThrowsExceptionWithMessage("nothing@this@that", WRONG_EMAIL_FORMAT_MESSAGE);
        assertThatThrowsExceptionWithMessage("this@th()t", WRONG_EMAIL_FORMAT_MESSAGE);
    }

    private void assertThatThrowsExceptionWithMessage(String email, String... messages) {
        Throwable thrown = catchThrowable(() -> new EmailValidator().build(email).validate());

        assertThat(thrown).isExactlyInstanceOf(BusinessValidationException.class);
        BusinessValidationException businessValidationException = (BusinessValidationException) thrown;
        assertThat(businessValidationException.getValidationMessages()).containsExactly(messages);
    }

}
