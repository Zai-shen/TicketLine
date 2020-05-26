package at.ac.tuwien.sepm.groupphase.backend.controller.mapper;

import at.ac.tuwien.sepm.groupphase.backend.util.DTOTestObjectFactory;
import at.ac.tuwien.sepm.groupphase.backend.util.DomainTestObjectFactory;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AddressMapperTest {

    private AddressMapperImpl addressMapper = new AddressMapperImpl();

    @Test
    public void testFromDTO() {
        assertThat(addressMapper.fromDto(DTOTestObjectFactory.getAddressDTO()))
            .isEqualToIgnoringGivenFields(DomainTestObjectFactory.getAddress());
    }
}
