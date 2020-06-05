package at.ac.tuwien.sepm.groupphase.backend.datagenerator;

import at.ac.tuwien.sepm.groupphase.backend.entity.*;
import at.ac.tuwien.sepm.groupphase.backend.repository.ArtistRepository;
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
import java.time.OffsetDateTime;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Profile("generateData")
@Component
public class PerformanceDataGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static final int NUMBER_OF_EVENTS_TO_GENERATE = 30;
    private static final int NUMBER_OF_ARTISTS_TO_GENERATE = 100;

    private final PerformanceRepository performanceRepository;
    private final EventRepository eventRepository;
    private final LocationRepository locationRepository;
    private final ArtistRepository artistRepository;

    public PerformanceDataGenerator(PerformanceRepository performanceRepository, EventRepository eventRepository,
        LocationRepository locationRepository, ArtistRepository artistRepository) {
        this.performanceRepository = performanceRepository;
        this.eventRepository = eventRepository;
        this.locationRepository = locationRepository;
        this.artistRepository = artistRepository;
    }

    private void generateArtists() {
        if (artistRepository.findAll().size() > 0) {
            LOGGER.debug("artists already generated");
        } else {
            LOGGER.debug("generating {} artist entries", NUMBER_OF_ARTISTS_TO_GENERATE);
            Faker faker = new Faker();
            for (int i = 0; i < NUMBER_OF_ARTISTS_TO_GENERATE; i++) {
                Artist artist = new Artist();
                artist.setFirstname(faker.name().firstName());
                artist.setLastname(faker.name().lastName());
                LOGGER.debug("saving artist {}", artist);
                artistRepository.save(artist);
            }
        }
    }

    @PostConstruct
    private void generatePerformances() {
        generateArtists();
        Faker f = new Faker();
        if (!performanceRepository.findAll().isEmpty()) {
            LOGGER.debug("message already generated");
        } else {
            LOGGER.debug("generating {} message entries", NUMBER_OF_EVENTS_TO_GENERATE);

            final Random random = ThreadLocalRandom.current();

            for (int i = 0; i < NUMBER_OF_EVENTS_TO_GENERATE; i++) {
                Artist artist = artistRepository.findById(f.random().nextLong(NUMBER_OF_ARTISTS_TO_GENERATE)).get();

                Event event = new Event();
                event.setTitle(f.book().title());
                event.setDescription(f.hitchhikersGuideToTheGalaxy().quote());
                event.setCategory(CategoryEnum.values()[random.nextInt(CategoryEnum.values().length)]);
                event.setDuration(f.random().nextLong(180)+20);
                event.setArtist(artist);

                Address locaddr = new Address();
                locaddr.setCity(f.pokemon().location());
                locaddr.setCountry(f.gameOfThrones().city());
                locaddr.setStreet(f.address().streetName());
                locaddr.setHousenr(f.address().buildingNumber());
                locaddr.setPostalcode(f.address().zipCode());

                Location location = new Location();
                location.setDescription(f.lordOfTheRings().location());
                location.setAddress(locaddr);

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
