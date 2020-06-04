package at.ac.tuwien.sepm.groupphase.backend.controller.mapper;

import at.ac.tuwien.sepm.groupphase.backend.dto.EventDTO;
import at.ac.tuwien.sepm.groupphase.backend.dto.SearchEventDTO;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.util.DTOTestObjectFactory;
import at.ac.tuwien.sepm.groupphase.backend.util.DomainTestObjectFactory;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EventMapperTest {

    private EventMapper eventMapper = new EventMapperImpl();

    @Test
    void testToDto() {
        Event event = DomainTestObjectFactory.getEvent();

        EventDTO result = eventMapper.toDto(event);

        assertThat(result).isEqualToComparingFieldByField(DTOTestObjectFactory.getEventDTO());
    }

    @Test
    void testFromDto() {
        EventDTO eventDTO = DTOTestObjectFactory.getEventDTO();

        Event result = eventMapper.fromDto(eventDTO);

        assertThat(result).isEqualToComparingFieldByField(DomainTestObjectFactory.getEvent());
    }

    @Test
    void testFromSearchDto() {
        SearchEventDTO searchEventDTO = DTOTestObjectFactory.getSearchEventDTO();

        Event result = eventMapper.fromSearchDto(searchEventDTO);

        assertThat(result).isEqualToIgnoringGivenFields(DomainTestObjectFactory.getEvent(), "id");
    }
}
