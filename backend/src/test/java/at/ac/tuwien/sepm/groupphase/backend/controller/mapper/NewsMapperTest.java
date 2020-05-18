package at.ac.tuwien.sepm.groupphase.backend.controller.mapper;

import at.ac.tuwien.sepm.groupphase.backend.util.DTOTestObjectFactory;
import at.ac.tuwien.sepm.groupphase.backend.util.DomainTestObjectFactory;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class NewsMapperTest {

    private NewsMapperImpl newsMapper = new NewsMapperImpl();

    @Test
    public void testToEntity() {
        assertThat(newsMapper.toEntity(DTOTestObjectFactory.getNewsDTO()))
            .isEqualToIgnoringGivenFields(DomainTestObjectFactory.getNews());
    }

    @Test
    public void testToDTO() {
        assertThat(newsMapper.toDTO(DomainTestObjectFactory.getNews()))
        //.isEqualToComparingOnlyGivenFields(DTOTestObjectFactory.getNewsDTO(), "summary");
    .isEqualToIgnoringGivenFields(DTOTestObjectFactory.getNewsDTO());
    }
}
