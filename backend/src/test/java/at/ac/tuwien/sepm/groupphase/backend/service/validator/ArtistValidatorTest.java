package at.ac.tuwien.sepm.groupphase.backend.service.validator;

import at.ac.tuwien.sepm.groupphase.backend.entity.Artist;
import at.ac.tuwien.sepm.groupphase.backend.exception.BusinessValidationException;
import at.ac.tuwien.sepm.groupphase.backend.util.DomainTestObjectFactory;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class ArtistValidatorTest {
    @Test
    void testValidEvent() {
        final Artist artist = DomainTestObjectFactory.getArtist();
        assertThatCode(() -> new ArtistValidator().build(artist).validate()).doesNotThrowAnyException();
    }

    @Test
    public void testAllNullEvent() {
        final Artist artist = new Artist();
        Throwable thrown = catchThrowable(() -> new ArtistValidator().build(artist).validate());

        assertThat(thrown).isExactlyInstanceOf(BusinessValidationException.class);
        BusinessValidationException businessValidationException = (BusinessValidationException) thrown;
        assertThat(businessValidationException.getValidationMessages()).containsExactly("Vorname ist leer",
            "Nachname ist leer");
    }
}