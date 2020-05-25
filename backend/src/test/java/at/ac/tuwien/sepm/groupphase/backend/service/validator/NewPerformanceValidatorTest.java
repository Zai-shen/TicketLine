package at.ac.tuwien.sepm.groupphase.backend.service.validator;

import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.Performance;
import at.ac.tuwien.sepm.groupphase.backend.exception.BusinessValidationException;
import at.ac.tuwien.sepm.groupphase.backend.util.DomainTestObjectFactory;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

public class NewPerformanceValidatorTest {
    @Test
    public void testValidPerformance() {
        final Performance performance = DomainTestObjectFactory.getPerformance();
        performance.getLocation().setId(1L);
        performance.getEvent().setId(1L);
        assertThatCode(() -> new NewPerformanceValidator().build(performance).validate()).doesNotThrowAnyException();
    }

    @Test
    public void testAllNullPerformance() {
        final Performance performance = new Performance();
        Throwable thrown = catchThrowable(() -> new NewPerformanceValidator().build(performance).validate());

        assertThat(thrown).isExactlyInstanceOf(BusinessValidationException.class);
        BusinessValidationException businessValidationException = (BusinessValidationException) thrown;
        assertThat(businessValidationException.getValidationMessages()).containsExactly("Ort ist nicht gesetzt",
            "Event ist nicht gesetzt",
            "Datum/Uhrzeit ist nicht gesetzt");
    }

    @Test
    public void testPerformanceWithUnsafedLocationAndEvent() {
        final Performance performance = DomainTestObjectFactory.getPerformance();
        performance.getLocation().setId(null);
        performance.getEvent().setId(null);
        Throwable thrown = catchThrowable(() -> new NewPerformanceValidator().build(performance).validate());

        assertThat(thrown).isExactlyInstanceOf(BusinessValidationException.class);
        BusinessValidationException businessValidationException = (BusinessValidationException) thrown;
        assertThat(businessValidationException.getValidationMessages()).containsExactly("Ort muss zuerst gespeichert werden", "Event muss zuerst gespeichert werden");
    }
}
