package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.CategoryEnum;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
public class SearchEventTest {

    @Autowired
    private EventRepository eventRepository;

    @Test
    void testSearchEventSpecification() {
        Event[] eventsToFind = insertTestEvents();

        Event filter = eventFrom("Test", CategoryEnum.FESTIVAL, 60L, "descr");
        Specification<Event> specification = new SearchEventSpecification(filter);

        Page<Event> result = eventRepository.findAll(specification, Pageable.unpaged());

        assertThat(result.getContent()).containsExactlyInAnyOrder(eventsToFind);
    }

    private Event[] insertTestEvents() {
        Event[] eventsToFind = {eventFrom("Testevent", CategoryEnum.FESTIVAL, 70L, "A description"),
            eventFrom("Event test that is", CategoryEnum.FESTIVAL, 40L, "This description describes")};
        eventRepository.saveAll(Arrays.asList(eventsToFind));

        eventRepository.save(eventFrom("Another event", CategoryEnum.FESTIVAL, 30L, "A description"));
        eventRepository.save(eventFrom("Test", CategoryEnum.ADVENTURE, 20L, "Description"));
        eventRepository.save(eventFrom("Test", CategoryEnum.FESTIVAL, 100L, "Descript"));

        return eventsToFind;
    }

    private Event eventFrom(String title, CategoryEnum category, long duration, String description) {
        final Event event = new Event();
        event.setTitle(title);
        event.setCategory(category);
        event.setDuration(duration);
        event.setDescription(description);
        return event;
    }
}
