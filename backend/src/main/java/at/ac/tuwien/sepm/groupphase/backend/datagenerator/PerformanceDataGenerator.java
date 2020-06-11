package at.ac.tuwien.sepm.groupphase.backend.datagenerator;

import at.ac.tuwien.sepm.groupphase.backend.entity.*;
import at.ac.tuwien.sepm.groupphase.backend.repository.EventRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.LocationRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.PerformanceRepository;
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
    private void generatePerformances() {
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

                Seatmap sm = new Seatmap();
                SeatGroupArea sga = new SeatGroupArea();
                sga.setWidth(100.0);
                sga.setHeight(100.0);
                sga.setX(0.0);
                sga.setY(0.0);
                sga.setPrice(4.20);
                sga.setName("Stehplätze");
                Set<Seat> seats = new HashSet<>();
                for (int j = 0; j < 50 ; j++) {
                    for (int k = 0; k < 50 ; k++) {
                        Seat s = new Seat();
                        s.setX((double) j);
                        s.setY((double) k);
                        s.setRowLabel(String.valueOf(j));
                        s.setColLabel(String.valueOf(k));
                        s.setSeatGroupArea(sga);
                        s.setRadius(0.5);
                        seats.add(s);
                    }
                }
                sga.setSeats(seats);
                sm.setSeatGroupAreas(Set.of(sga));
                StandingArea sa = new StandingArea();
                sa.setWidth(100.0);
                sa.setHeight(100.0);
                sa.setMaxPeople(50L);
                sa.setName("Stehplätze");
                sa.setX(200.0);
                sa.setY(200.0);
                sa.setPrice(3.50);
                sm.setStandingAreas(Set.of(sa));
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

}
