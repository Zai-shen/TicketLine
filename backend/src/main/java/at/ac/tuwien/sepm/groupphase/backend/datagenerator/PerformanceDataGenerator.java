package at.ac.tuwien.sepm.groupphase.backend.datagenerator;

import at.ac.tuwien.sepm.groupphase.backend.entity.CategoryEnum;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.Location;
import at.ac.tuwien.sepm.groupphase.backend.entity.Performance;
import at.ac.tuwien.sepm.groupphase.backend.repository.EventRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.LocationRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.PerformanceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.invoke.MethodHandles;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;

@Profile("generateData")
@Component
public class PerformanceDataGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static final int NUMBER_OF_EVENTS_TO_GENERATE = 20;
    private static final String TEST_EVENT_TITLE = "Title";
    private static final String TEST_EVENT_DESCRIPTION = "Description of the event";
    private static final String TEST_EVENT_TEXT = "This is the text of the message";

    private final PerformanceRepository performanceRepository;
    private final EventRepository eventRepository;
    private final LocationRepository locationRepository;

    public PerformanceDataGenerator(PerformanceRepository performanceRepository, EventRepository eventRepository,
        LocationRepository locationRepository) {
        this.performanceRepository = performanceRepository;
        this.eventRepository = eventRepository;
        this.locationRepository = locationRepository;
    }

    @PostConstruct
    private void generateMessage() {
        if (performanceRepository.findAll().size() > 0) {
            LOGGER.debug("message already generated");
        } else {
            LOGGER.debug("generating {} message entries", NUMBER_OF_EVENTS_TO_GENERATE);
            for (int i = 0; i < NUMBER_OF_EVENTS_TO_GENERATE; i++) {
                Event event = new Event();
                event.setTitle(TEST_EVENT_TITLE + "" + i);
                event.setDescription(TEST_EVENT_DESCRIPTION + " " + i);
                event.setCategory(CategoryEnum.ADVENTURE);
                event.setDuration(200L);

                Location location = new Location();
                location.setDescription("Location" + "1");

                Performance performance = new Performance();
                performance.setDateTime(OffsetDateTime.now().plusDays(i).plusHours(i));
                performance.setEvent(event);
                performance.setLocation(location);

                eventRepository.save(event);
                locationRepository.save(location);
                performanceRepository.save(performance);
            }
        }
    }

}
