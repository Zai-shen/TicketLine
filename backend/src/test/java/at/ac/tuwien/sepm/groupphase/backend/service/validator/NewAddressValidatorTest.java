package at.ac.tuwien.sepm.groupphase.backend.service.validator;

import at.ac.tuwien.sepm.groupphase.backend.entity.Address;
import at.ac.tuwien.sepm.groupphase.backend.exception.BusinessValidationException;
import at.ac.tuwien.sepm.groupphase.backend.util.DomainTestObjectFactory;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class NewAddressValidatorTest {
    @Test
    public void testValidAddress() {
        final Address address = DomainTestObjectFactory.getAddress();
        assertThatCode(() -> new NewAddressValidator().build(address).validate()).doesNotThrowAnyException();
    }

    @Test
    public void testAllNullAddress() {
        final Address address = new Address();
        Throwable thrown = catchThrowable(() -> new NewAddressValidator().build(address).validate());

        assertThat(thrown).isExactlyInstanceOf(BusinessValidationException.class);
        BusinessValidationException businessValidationException = (BusinessValidationException) thrown;
        assertThat(businessValidationException.getValidationMessages()).containsExactly("Straße ist leer",
            "Hausnummer ist leer", "PLZ ist leer", "Stadt ist leer", "Staat ist leer");
    }

}
