package at.ac.tuwien.sepm.groupphase.backend.controller.mapper;

import at.ac.tuwien.sepm.groupphase.backend.util.DTOTestObjectFactory;
import at.ac.tuwien.sepm.groupphase.backend.util.DomainTestObjectFactory;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserMapperTest {

    private UserMapperImpl userMapper = new UserMapperImpl();

    @Test
    public void testToEntity() {
        assertThat(userMapper.toEntity(DTOTestObjectFactory.getUserDTO()))
            .isEqualToIgnoringGivenFields(DomainTestObjectFactory.getUser(), "role");
    }
}
