package at.ac.tuwien.sepm.groupphase.backend.datagenerator;

import at.ac.tuwien.sepm.groupphase.backend.entity.Address;
import at.ac.tuwien.sepm.groupphase.backend.entity.Location;
import at.ac.tuwien.sepm.groupphase.backend.repository.LocationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.invoke.MethodHandles;

@Profile("generateData")
@Component
public class LocationDataGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static final int NUMBER_OF_LOCATIONS_TO_GENERATE = 5;

    private final LocationRepository locationRepository;

    public LocationDataGenerator(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @PostConstruct
    private void generateLocations() {
        if (locationRepository.findAll().size() > 0) {
            LOGGER.debug("locations already generated");
        } else {
            LOGGER.debug("generating {} location entries", NUMBER_OF_LOCATIONS_TO_GENERATE);
            for (int i = 0; i < NUMBER_OF_LOCATIONS_TO_GENERATE; i++) {
                Address address = new Address();
                address.setStreet("Straße " + i);
                address.setHousenr("1");
                address.setPostalcode("1000");
                address.setCity("Stadt " + i);
                address.setCountry("Land " + i);

                Location location = new Location();
                location.setDescription("Verantstaltungsort " + i);
                location.setAddress(address);
                locationRepository.save(location);
            }
        }
    }
}
