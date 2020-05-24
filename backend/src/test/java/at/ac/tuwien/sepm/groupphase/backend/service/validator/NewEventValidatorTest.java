package at.ac.tuwien.sepm.groupphase.backend.service.validator;

import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.exception.BusinessValidationException;
import at.ac.tuwien.sepm.groupphase.backend.util.DomainTestObjectFactory;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

public class NewEventValidatorTest {

    @Test
    public void testValidEvent() {
        final Event event = DomainTestObjectFactory.getEvent();
        assertThatCode(() -> new NewEventValidator().build(event).validate()).doesNotThrowAnyException();
    }

    @Test
    public void testAllNullEvent() {
        final Event event = new Event();
        Throwable thrown = catchThrowable(() -> new NewEventValidator().build(event).validate());

        assertThat(thrown).isExactlyInstanceOf(BusinessValidationException.class);
        BusinessValidationException businessValidationException = (BusinessValidationException) thrown;
        assertThat(businessValidationException.getValidationMessages()).containsExactly("Titel ist leer",
            "Kategorie ist nicht gesetzt",
            "Beschreibung ist nicht gesetzt",
            "Dauer ist nicht gesetzt");
    }
}
