package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.Location;
import at.ac.tuwien.sepm.groupphase.backend.entity.Performance;
import at.ac.tuwien.sepm.groupphase.backend.entity.SearchPerformance;
import at.ac.tuwien.sepm.groupphase.backend.util.DomainTestObjectFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
public class SearchPerformanceTest {

    @Autowired
    private PerformanceRepository performanceRepository;

    @Autowired
    private EventRepository eventRepository;

    @Test
    void testSearchPerformanceSpecification() {
        Performance performancesToFind = insertTestPerformances();

        SearchPerformance filter = searchPerformanceFrom(null, null, "Event-Title", null, null);
        Specification<Performance> specification = new SearchPerformanceSpecification(filter);

        Page<Performance> result = performanceRepository.findAll(specification, Pageable.unpaged());

        assertThat(result.getContent()).contains(performancesToFind);
    }

    private Performance insertTestPerformances() {
        Event event = DomainTestObjectFactory.getEvent();
        Performance performancesToFind = performanceFrom(OffsetDateTime.now(), event, null);
        eventRepository.saveAndFlush(event);
        performanceRepository.saveAndFlush(performancesToFind);

        Event otherEvents = DomainTestObjectFactory.getEvent();
        otherEvents.setId(2L);
        otherEvents.setTitle("Other-Title");
        eventRepository.saveAndFlush(otherEvents);
        performanceRepository.saveAndFlush(performanceFrom(OffsetDateTime.now(), otherEvents, null));
        performanceRepository.saveAndFlush(performanceFrom(OffsetDateTime.now(), otherEvents, null));

        return performancesToFind;
    }

    private SearchPerformance searchPerformanceFrom(LocalDate date, String time, String event, BigDecimal price, String location) {
        final SearchPerformance performance = new SearchPerformance();
        performance.setDate(date);
        performance.setTime(time);
        performance.setEvent(event);
        performance.setPrice(price);
        performance.setLocation(location);
        return performance;
    }

    private Performance performanceFrom(OffsetDateTime dateTime, Event event, Location location) {
        final Performance performance = new Performance();
        performance.setDateTime(dateTime);
        performance.setEvent(event);
        performance.setLocation(location);
        return performance;
    }
}
