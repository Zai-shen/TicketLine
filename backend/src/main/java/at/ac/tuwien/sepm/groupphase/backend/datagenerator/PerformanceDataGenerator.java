package at.ac.tuwien.sepm.groupphase.backend.datagenerator;

import at.ac.tuwien.sepm.groupphase.backend.controller.mapper.LocationMapper;
import at.ac.tuwien.sepm.groupphase.backend.controller.mapper.LocationMapperImpl;
import at.ac.tuwien.sepm.groupphase.backend.dto.SeatmapDTO;
import at.ac.tuwien.sepm.groupphase.backend.entity.*;
import at.ac.tuwien.sepm.groupphase.backend.repository.EventRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.LocationRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.PerformanceRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.invoke.MethodHandles;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

@Profile("generateData")
@Component
public class PerformanceDataGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static final int NUMBER_OF_EVENTS_TO_GENERATE = 30;
    private static final String RAW_SEATMAP = "{\"seatGroupAreas\":[{\"x\":181.0,\"y\":223.0,\"width\":102.0,\"height\":105.0,\"price\":15.0,\"name\":\"Bereich-1\",\"seats\":[{\"x\":23.0,\"y\":23.0,\"rowLabel\":\"a\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":23.0,\"rowLabel\":\"a\",\"colLabel\":\"2\",\"radius\":5.0},{\"x\":51.0,\"y\":23.0,\"rowLabel\":\"a\",\"colLabel\":\"3\",\"radius\":5.0},{\"x\":65.0,\"y\":23.0,\"rowLabel\":\"a\",\"colLabel\":\"4\",\"radius\":5.0},{\"x\":79.0,\"y\":23.0,\"rowLabel\":\"a\",\"colLabel\":\"5\",\"radius\":5.0},{\"x\":93.0,\"y\":23.0,\"rowLabel\":\"a\",\"colLabel\":\"6\",\"radius\":5.0},{\"x\":23.0,\"y\":37.0,\"rowLabel\":\"b\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":37.0,\"rowLabel\":\"b\",\"colLabel\":\"2\",\"radius\":5.0},{\"x\":51.0,\"y\":37.0,\"rowLabel\":\"b\",\"colLabel\":\"3\",\"radius\":5.0},{\"x\":65.0,\"y\":37.0,\"rowLabel\":\"b\",\"colLabel\":\"4\",\"radius\":5.0},{\"x\":79.0,\"y\":37.0,\"rowLabel\":\"b\",\"colLabel\":\"5\",\"radius\":5.0},{\"x\":93.0,\"y\":37.0,\"rowLabel\":\"b\",\"colLabel\":\"6\",\"radius\":5.0},{\"x\":23.0,\"y\":51.0,\"rowLabel\":\"c\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":51.0,\"rowLabel\":\"c\",\"colLabel\":\"2\",\"radius\":5.0},{\"x\":51.0,\"y\":51.0,\"rowLabel\":\"c\",\"colLabel\":\"3\",\"radius\":5.0},{\"x\":65.0,\"y\":51.0,\"rowLabel\":\"c\",\"colLabel\":\"4\",\"radius\":5.0},{\"x\":79.0,\"y\":51.0,\"rowLabel\":\"c\",\"colLabel\":\"5\",\"radius\":5.0},{\"x\":93.0,\"y\":51.0,\"rowLabel\":\"c\",\"colLabel\":\"6\",\"radius\":5.0},{\"x\":23.0,\"y\":65.0,\"rowLabel\":\"d\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":65.0,\"rowLabel\":\"d\",\"colLabel\":\"2\",\"radius\":5.0},{\"x\":51.0,\"y\":65.0,\"rowLabel\":\"d\",\"colLabel\":\"3\",\"radius\":5.0},{\"x\":65.0,\"y\":65.0,\"rowLabel\":\"d\",\"colLabel\":\"4\",\"radius\":5.0},{\"x\":79.0,\"y\":65.0,\"rowLabel\":\"d\",\"colLabel\":\"5\",\"radius\":5.0},{\"x\":93.0,\"y\":65.0,\"rowLabel\":\"d\",\"colLabel\":\"6\",\"radius\":5.0},{\"x\":23.0,\"y\":79.0,\"rowLabel\":\"e\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":79.0,\"rowLabel\":\"e\",\"colLabel\":\"2\",\"radius\":5.0},{\"x\":51.0,\"y\":79.0,\"rowLabel\":\"e\",\"colLabel\":\"3\",\"radius\":5.0},{\"x\":65.0,\"y\":79.0,\"rowLabel\":\"e\",\"colLabel\":\"4\",\"radius\":5.0},{\"x\":79.0,\"y\":79.0,\"rowLabel\":\"e\",\"colLabel\":\"5\",\"radius\":5.0},{\"x\":93.0,\"y\":79.0,\"rowLabel\":\"e\",\"colLabel\":\"6\",\"radius\":5.0},{\"x\":23.0,\"y\":93.0,\"rowLabel\":\"f\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":93.0,\"rowLabel\":\"f\",\"colLabel\":\"2\",\"radius\":5.0},{\"x\":51.0,\"y\":93.0,\"rowLabel\":\"f\",\"colLabel\":\"3\",\"radius\":5.0},{\"x\":65.0,\"y\":93.0,\"rowLabel\":\"f\",\"colLabel\":\"4\",\"radius\":5.0},{\"x\":79.0,\"y\":93.0,\"rowLabel\":\"f\",\"colLabel\":\"5\",\"radius\":5.0},{\"x\":93.0,\"y\":93.0,\"rowLabel\":\"f\",\"colLabel\":\"6\",\"radius\":5.0}],\"seatLabels\":[{\"x\":23.0,\"y\":9.0,\"size\":5.0,\"text\":\"1\"},{\"x\":37.0,\"y\":9.0,\"size\":5.0,\"text\":\"2\"},{\"x\":51.0,\"y\":9.0,\"size\":5.0,\"text\":\"3\"},{\"x\":65.0,\"y\":9.0,\"size\":5.0,\"text\":\"4\"},{\"x\":79.0,\"y\":9.0,\"size\":5.0,\"text\":\"5\"},{\"x\":93.0,\"y\":9.0,\"size\":5.0,\"text\":\"6\"},{\"x\":9.0,\"y\":23.0,\"size\":5.0,\"text\":\"a\"},{\"x\":9.0,\"y\":37.0,\"size\":5.0,\"text\":\"b\"},{\"x\":9.0,\"y\":51.0,\"size\":5.0,\"text\":\"c\"},{\"x\":9.0,\"y\":65.0,\"size\":5.0,\"text\":\"d\"},{\"x\":9.0,\"y\":79.0,\"size\":5.0,\"text\":\"e\"},{\"x\":9.0,\"y\":93.0,\"size\":5.0,\"text\":\"f\"}]},{\"x\":284.0,\"y\":111.0,\"width\":53.0,\"height\":219.0,\"price\":11.0,\"name\":\"Bereich-2\",\"seats\":[{\"x\":23.0,\"y\":23.0,\"rowLabel\":\"a\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":23.0,\"rowLabel\":\"a\",\"colLabel\":\"2\",\"radius\":5.0},{\"x\":23.0,\"y\":37.0,\"rowLabel\":\"b\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":37.0,\"rowLabel\":\"b\",\"colLabel\":\"2\",\"radius\":5.0},{\"x\":23.0,\"y\":51.0,\"rowLabel\":\"c\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":51.0,\"rowLabel\":\"c\",\"colLabel\":\"2\",\"radius\":5.0},{\"x\":23.0,\"y\":65.0,\"rowLabel\":\"d\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":65.0,\"rowLabel\":\"d\",\"colLabel\":\"2\",\"radius\":5.0},{\"x\":23.0,\"y\":79.0,\"rowLabel\":\"e\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":79.0,\"rowLabel\":\"e\",\"colLabel\":\"2\",\"radius\":5.0},{\"x\":23.0,\"y\":93.0,\"rowLabel\":\"f\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":93.0,\"rowLabel\":\"f\",\"colLabel\":\"2\",\"radius\":5.0},{\"x\":23.0,\"y\":107.0,\"rowLabel\":\"g\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":107.0,\"rowLabel\":\"g\",\"colLabel\":\"2\",\"radius\":5.0},{\"x\":23.0,\"y\":121.0,\"rowLabel\":\"h\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":121.0,\"rowLabel\":\"h\",\"colLabel\":\"2\",\"radius\":5.0},{\"x\":23.0,\"y\":135.0,\"rowLabel\":\"i\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":135.0,\"rowLabel\":\"i\",\"colLabel\":\"2\",\"radius\":5.0},{\"x\":23.0,\"y\":149.0,\"rowLabel\":\"j\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":149.0,\"rowLabel\":\"j\",\"colLabel\":\"2\",\"radius\":5.0},{\"x\":23.0,\"y\":163.0,\"rowLabel\":\"k\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":163.0,\"rowLabel\":\"k\",\"colLabel\":\"2\",\"radius\":5.0},{\"x\":23.0,\"y\":177.0,\"rowLabel\":\"l\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":177.0,\"rowLabel\":\"l\",\"colLabel\":\"2\",\"radius\":5.0},{\"x\":23.0,\"y\":191.0,\"rowLabel\":\"m\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":191.0,\"rowLabel\":\"m\",\"colLabel\":\"2\",\"radius\":5.0},{\"x\":23.0,\"y\":205.0,\"rowLabel\":\"n\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":205.0,\"rowLabel\":\"n\",\"colLabel\":\"2\",\"radius\":5.0}],\"seatLabels\":[{\"x\":23.0,\"y\":9.0,\"size\":5.0,\"text\":\"1\"},{\"x\":37.0,\"y\":9.0,\"size\":5.0,\"text\":\"2\"},{\"x\":9.0,\"y\":23.0,\"size\":5.0,\"text\":\"a\"},{\"x\":9.0,\"y\":37.0,\"size\":5.0,\"text\":\"b\"},{\"x\":9.0,\"y\":51.0,\"size\":5.0,\"text\":\"c\"},{\"x\":9.0,\"y\":65.0,\"size\":5.0,\"text\":\"d\"},{\"x\":9.0,\"y\":79.0,\"size\":5.0,\"text\":\"e\"},{\"x\":9.0,\"y\":93.0,\"size\":5.0,\"text\":\"f\"},{\"x\":9.0,\"y\":107.0,\"size\":5.0,\"text\":\"g\"},{\"x\":9.0,\"y\":121.0,\"size\":5.0,\"text\":\"h\"},{\"x\":9.0,\"y\":135.0,\"size\":5.0,\"text\":\"i\"},{\"x\":9.0,\"y\":149.0,\"size\":5.0,\"text\":\"j\"},{\"x\":9.0,\"y\":163.0,\"size\":5.0,\"text\":\"k\"},{\"x\":9.0,\"y\":177.0,\"size\":5.0,\"text\":\"l\"},{\"x\":9.0,\"y\":191.0,\"size\":5.0,\"text\":\"m\"},{\"x\":9.0,\"y\":205.0,\"size\":5.0,\"text\":\"n\"}]},{\"x\":129.0,\"y\":107.0,\"width\":51.0,\"height\":225.0,\"price\":11.0,\"name\":\"Bereich-3\",\"seats\":[{\"x\":23.0,\"y\":23.0,\"rowLabel\":\"a\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":23.0,\"rowLabel\":\"a\",\"colLabel\":\"2\",\"radius\":5.0},{\"x\":23.0,\"y\":37.0,\"rowLabel\":\"b\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":37.0,\"rowLabel\":\"b\",\"colLabel\":\"2\",\"radius\":5.0},{\"x\":23.0,\"y\":51.0,\"rowLabel\":\"c\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":51.0,\"rowLabel\":\"c\",\"colLabel\":\"2\",\"radius\":5.0},{\"x\":23.0,\"y\":65.0,\"rowLabel\":\"d\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":65.0,\"rowLabel\":\"d\",\"colLabel\":\"2\",\"radius\":5.0},{\"x\":23.0,\"y\":79.0,\"rowLabel\":\"e\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":79.0,\"rowLabel\":\"e\",\"colLabel\":\"2\",\"radius\":5.0},{\"x\":23.0,\"y\":93.0,\"rowLabel\":\"f\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":93.0,\"rowLabel\":\"f\",\"colLabel\":\"2\",\"radius\":5.0},{\"x\":23.0,\"y\":107.0,\"rowLabel\":\"g\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":107.0,\"rowLabel\":\"g\",\"colLabel\":\"2\",\"radius\":5.0},{\"x\":23.0,\"y\":121.0,\"rowLabel\":\"h\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":121.0,\"rowLabel\":\"h\",\"colLabel\":\"2\",\"radius\":5.0},{\"x\":23.0,\"y\":135.0,\"rowLabel\":\"i\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":135.0,\"rowLabel\":\"i\",\"colLabel\":\"2\",\"radius\":5.0},{\"x\":23.0,\"y\":149.0,\"rowLabel\":\"j\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":149.0,\"rowLabel\":\"j\",\"colLabel\":\"2\",\"radius\":5.0},{\"x\":23.0,\"y\":163.0,\"rowLabel\":\"k\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":163.0,\"rowLabel\":\"k\",\"colLabel\":\"2\",\"radius\":5.0},{\"x\":23.0,\"y\":177.0,\"rowLabel\":\"l\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":177.0,\"rowLabel\":\"l\",\"colLabel\":\"2\",\"radius\":5.0},{\"x\":23.0,\"y\":191.0,\"rowLabel\":\"m\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":191.0,\"rowLabel\":\"m\",\"colLabel\":\"2\",\"radius\":5.0},{\"x\":23.0,\"y\":205.0,\"rowLabel\":\"n\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":205.0,\"rowLabel\":\"n\",\"colLabel\":\"2\",\"radius\":5.0},{\"x\":23.0,\"y\":219.0,\"rowLabel\":\"o\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":219.0,\"rowLabel\":\"o\",\"colLabel\":\"2\",\"radius\":5.0}],\"seatLabels\":[{\"x\":23.0,\"y\":9.0,\"size\":5.0,\"text\":\"1\"},{\"x\":37.0,\"y\":9.0,\"size\":5.0,\"text\":\"2\"},{\"x\":9.0,\"y\":23.0,\"size\":5.0,\"text\":\"a\"},{\"x\":9.0,\"y\":37.0,\"size\":5.0,\"text\":\"b\"},{\"x\":9.0,\"y\":51.0,\"size\":5.0,\"text\":\"c\"},{\"x\":9.0,\"y\":65.0,\"size\":5.0,\"text\":\"d\"},{\"x\":9.0,\"y\":79.0,\"size\":5.0,\"text\":\"e\"},{\"x\":9.0,\"y\":93.0,\"size\":5.0,\"text\":\"f\"},{\"x\":9.0,\"y\":107.0,\"size\":5.0,\"text\":\"g\"},{\"x\":9.0,\"y\":121.0,\"size\":5.0,\"text\":\"h\"},{\"x\":9.0,\"y\":135.0,\"size\":5.0,\"text\":\"i\"},{\"x\":9.0,\"y\":149.0,\"size\":5.0,\"text\":\"j\"},{\"x\":9.0,\"y\":163.0,\"size\":5.0,\"text\":\"k\"},{\"x\":9.0,\"y\":177.0,\"size\":5.0,\"text\":\"l\"},{\"x\":9.0,\"y\":191.0,\"size\":5.0,\"text\":\"m\"},{\"x\":9.0,\"y\":205.0,\"size\":5.0,\"text\":\"n\"},{\"x\":9.0,\"y\":219.0,\"size\":5.0,\"text\":\"o\"}]}],\"standingAreas\":[{\"x\":182.0,\"y\":112.0,\"width\":100.0,\"height\":109.0,\"name\":\"Bereich-0\",\"maxPeople\":10,\"price\":10.0}]}";

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
    private void generatePerformances() throws JsonProcessingException {
        Faker f = new Faker();
        if (!performanceRepository.findAll().isEmpty()) {
            LOGGER.debug("message already generated");
        } else {
            LOGGER.debug("generating {} message entries", NUMBER_OF_EVENTS_TO_GENERATE);

            final Random random = ThreadLocalRandom.current();

            for (int i = 0; i < NUMBER_OF_EVENTS_TO_GENERATE; i++) {
                Event event = new Event();
                event.setTitle(f.book().title());
                event.setDescription(f.hitchhikersGuideToTheGalaxy().quote());
                event.setCategory(CategoryEnum.values()[random.nextInt(CategoryEnum.values().length)]);
                event.setDuration(f.random().nextLong(180)+20);

                Address locaddr = new Address();
                locaddr.setCity(f.pokemon().location());
                locaddr.setCountry(f.gameOfThrones().city());
                locaddr.setStreet(f.address().streetName());
                locaddr.setHousenr(f.address().buildingNumber());
                locaddr.setPostalcode(f.address().zipCode());

                Location location = new Location();
                location.setDescription(f.lordOfTheRings().location());
                location.setAddress(locaddr);

                Seatmap sm = getSeatmap();
                location.setSeatmap(sm);

                Performance performance = new Performance();
                performance.setDateTime(OffsetDateTime.now().plusDays(i).plusHours(i));
                performance.setEvent(event);
                performance.setLocation(location);

                Performance performance2 = new Performance();
                performance2.setDateTime(OffsetDateTime.now().plusDays(i+1).plusHours(i));
                performance2.setEvent(event);
                performance2.setLocation(location);

                eventRepository.save(event);
                locationRepository.save(location);
                performanceRepository.save(performance);
                performanceRepository.save(performance2);
            }
        }
    }

    private Seatmap getSeatmap() throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        LocationMapper lm = new LocationMapperImpl();
        SeatmapDTO sdto = om.readValue(RAW_SEATMAP, SeatmapDTO.class);
        Seatmap sm = lm.fromDto(sdto);
        for(SeatGroupArea sga : sm.getSeatGroupAreas())
            for(Seat s : sga.getSeats())
                s.setSeatGroupArea(sga);
        return sm;
    }

}
