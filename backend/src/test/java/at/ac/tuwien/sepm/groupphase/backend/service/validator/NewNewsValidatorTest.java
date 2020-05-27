package at.ac.tuwien.sepm.groupphase.backend.service.validator;

import at.ac.tuwien.sepm.groupphase.backend.entity.News;
import at.ac.tuwien.sepm.groupphase.backend.entity.User;
import at.ac.tuwien.sepm.groupphase.backend.exception.BusinessValidationException;
import at.ac.tuwien.sepm.groupphase.backend.util.DomainTestObjectFactory;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class NewNewsValidatorTest {

    @Test
    public void testWithValidNews() {
        final News news = DomainTestObjectFactory.getNews();
        assertThatCode(() -> new NewNewsValidator().build(news).validate()).doesNotThrowAnyException();
    }

    @Test
    public void testWithNullValues() {
        final News news = new News();
        Throwable thrown = catchThrowable(() -> new NewNewsValidator().build(news).validate());

        assertThat(thrown).isExactlyInstanceOf(BusinessValidationException.class);
        BusinessValidationException businessValidationException = (BusinessValidationException) thrown;
        assertThat(businessValidationException.getValidationMessages()).containsExactly(
            "Publiziert am ist nicht gesetzt", "Titel ist leer", "Kurzfassung ist leer", "Text ist leer", "Author ist leer");
    }
}
