package at.ac.tuwien.sepm.groupphase.backend.datagenerator;

import at.ac.tuwien.sepm.groupphase.backend.entity.*;
import at.ac.tuwien.sepm.groupphase.backend.repository.AddressRepository;
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
import java.time.LocalDate;
import java.time.LocalTime;

@Profile("generateData")
@Component
public class PerformanceDataGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static final int NUMBER_OF_EVENTS_TO_GENERATE = 20;

    private final PerformanceRepository performanceRepository;
    private final EventRepository eventRepository;
    private final LocationRepository locationRepository;
    private final AddressRepository addressRepository;

    public PerformanceDataGenerator(PerformanceRepository performanceRepository, EventRepository eventRepository,
        LocationRepository locationRepository, AddressRepository addressRepository) {
        this.performanceRepository = performanceRepository;
        this.eventRepository = eventRepository;
        this.locationRepository = locationRepository;
        this.addressRepository = addressRepository;
    }

    @PostConstruct
    private void generatePerformances() {
        Faker f = new Faker();
        if (!performanceRepository.findAll().isEmpty()) {
            LOGGER.debug("message already generated");
        } else {
            LOGGER.debug("generating {} message entries", NUMBER_OF_EVENTS_TO_GENERATE);
            for (int i = 0; i < NUMBER_OF_EVENTS_TO_GENERATE; i++) {
                Event event = new Event();
                event.setTitle(f.book().title());
                event.setDescription(f.hitchhikersGuideToTheGalaxy().quote());
                event.setCategory(CategoryEnum.ADVENTURE);
                event.setDuration(f.random().nextInt(20,200));

                Address locaddr = new Address();
                locaddr.setCity(f.pokemon().location());
                locaddr.setCountry(f.gameOfThrones().city());
                locaddr.setStreet(f.address().streetName());
                locaddr.setHousenr(f.address().buildingNumber());
                locaddr.setPostalcode(f.address().zipCode());
                Location location = new Location();
                location.setDescription(f.rickAndMorty().quote());
                location.setAddress(locaddr);

                Performance performance = new Performance();
                performance.setDate(LocalDate.now().plusDays(i));
                performance.setLocalTime(LocalTime.NOON.plusHours(i));
                performance.setEvent(event);
                performance.setLocation(location);

                eventRepository.save(event);
                locationRepository.save(location);
                performanceRepository.save(performance);
            }
        }
    }

}
