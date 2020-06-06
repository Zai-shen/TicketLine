package at.ac.tuwien.sepm.groupphase.backend.service.validator;

import at.ac.tuwien.sepm.groupphase.backend.entity.Location;
import at.ac.tuwien.sepm.groupphase.backend.exception.BusinessValidationException;
import at.ac.tuwien.sepm.groupphase.backend.util.DomainTestObjectFactory;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

public class NewLocationValidatorTest {
    @Test
    public void testValidLocation() {
        final Location location = DomainTestObjectFactory.getLocation();
        assertThatCode(() -> new NewLocationValidator().build(location).validate()).doesNotThrowAnyException();
    }

    @Test
    public void testLocationWithoutAddressAndWithoutSeatmap() {
        final Location location = new Location();
        location.setDescription("Veranstaltungsort");
        Throwable thrown = catchThrowable(() -> new NewLocationValidator().build(location).validate());

        assertThat(thrown).isExactlyInstanceOf(BusinessValidationException.class);
        BusinessValidationException businessValidationException = (BusinessValidationException) thrown;
        assertThat(businessValidationException.getValidationMessages()).containsExactly("Adresse ist nicht gesetzt", "Saalplan ist nicht gesetzt");
    }
}
