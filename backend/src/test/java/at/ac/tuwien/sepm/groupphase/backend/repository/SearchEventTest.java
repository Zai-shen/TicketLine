package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
public class SearchEventTest {

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private PerformanceRepository performanceRepository;

    @Test
    void testSearchEventSpecification() {
        Event[] eventsToFind = insertTestEvents();

        Event filter = eventFrom("Test", CategoryEnum.FESTIVAL, 60L, "descr");
        Specification<Event> specification = new SearchEventSpecification(filter);

        Page<Event> result = eventRepository.findAll(specification, Pageable.unpaged());

        assertThat(result.getContent()).containsExactlyInAnyOrder(eventsToFind);
    }

    @Test
    void testTopTenOrdering() {
        eventRepository.deleteAll();
        for (int i = 0; i < 20 ; i++) {
            Event e = eventFrom("Event " + i,i%2 == 0 ? CategoryEnum.FESTIVAL : CategoryEnum.PARTY,200L,"foo");
            Performance p = new Performance();
            p.setEvent(e);
            p.setDateTime(OffsetDateTime.now().plusDays(1));
            eventRepository.save(e);
            performanceRepository.save(p);
            for (int j = 0; j < i ; j++) {
                Booking b = new Booking();
                b.setDate(LocalDate.now());
                b.setReservation(false);
                b.setPerformance(p);
                b.setCanceled(false);
                List<Ticket> tickets = new LinkedList<>();
                Ticket t = new Ticket();
                t.setPrice(BigDecimal.valueOf(20.0));
                t.setBooking(b);
                tickets.add(t);
                b.setTickets(tickets);
                bookingRepository.save(b);
            }
        }
        Page<Event> events = eventRepository.getOrderedEvents(OffsetDateTime.now(),OffsetDateTime.now().plusMonths(1L), PageRequest.of(0,10));
        assertThat(events.getContent().get(0)).extracting(Event::getTitle).isEqualTo("Event 19");
        assertThat(events.getContent().get(1)).extracting(Event::getTitle).isEqualTo("Event 18");
        assertThat(events.getContent().get(2)).extracting(Event::getTitle).isEqualTo("Event 17");
        assertThat(events.getContent().get(3)).extracting(Event::getTitle).isEqualTo("Event 16");
        events = eventRepository.getOrderedEvents(OffsetDateTime.now(),OffsetDateTime.now().plusMonths(1L), CategoryEnum.FESTIVAL, PageRequest.of(0,10));
        assertThat(events.getContent().get(0)).extracting(Event::getTitle).isEqualTo("Event 18");
        assertThat(events.getContent().get(1)).extracting(Event::getTitle).isEqualTo("Event 16");
        assertThat(events.getContent().get(2)).extracting(Event::getTitle).isEqualTo("Event 14");
        assertThat(events.getContent().get(3)).extracting(Event::getTitle).isEqualTo("Event 12");
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
