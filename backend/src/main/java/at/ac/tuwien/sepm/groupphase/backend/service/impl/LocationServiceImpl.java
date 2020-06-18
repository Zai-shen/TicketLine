package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.*;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.LocationRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.PerformanceRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.SeatmapRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.LocationService;
import at.ac.tuwien.sepm.groupphase.backend.service.validator.NewLocationValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.lang.invoke.MethodHandles;
import java.time.OffsetDateTime;
import java.util.List;

@Service
public class LocationServiceImpl implements LocationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final LocationRepository locationRepository;
    private final PerformanceRepository performanceRepository;
    private final SeatmapRepository seatmapRepository;

    @Autowired
    public LocationServiceImpl(LocationRepository locationRepository, PerformanceRepository performanceRepository,
        SeatmapRepository seatmapRepository) {
        this.locationRepository = locationRepository;
        this.performanceRepository = performanceRepository;
        this.seatmapRepository = seatmapRepository;
    }

    @Override
    public Page<Location> getLocationList(Pageable page) {
        LOGGER.debug("find all locations per page");
        return locationRepository.findAll(page);
    }

    @Override
    public List<Location> getAllLocations() {
        LOGGER.debug("find all locations");
        return locationRepository.findAll();
    }

    @Override
    public void createLocation(Location location) {
        LOGGER.debug("create location");
        new NewLocationValidator().build(location).validate();
        for (SeatGroupArea sga : location.getSeatmap().getSeatGroupAreas()) {
            for (Seat s : sga.getSeats()) {
                s.setSeatGroupArea(sga);
            }
        }
        locationRepository.save(location);
    }

    @Override
    public Page<Location> searchLocations(Location location, Pageable page) {
        LOGGER.debug("search locations");
        ExampleMatcher matcher = ExampleMatcher
            .matchingAll()
            .withIgnoreCase()
            .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Location> probe = Example.of(location, matcher);

        return locationRepository.findAll(probe, page);
    }

    @Override
    public List<Performance> performancesForLocation(Long locationId) {
        LOGGER.debug("get performances for location {}",locationId);
        Location l = locationRepository.findById(locationId).orElseThrow(NotFoundException::new);
        return performanceRepository.findByLocationAndDateTimeIsAfter(l, OffsetDateTime.now());
    }

    @Override
    public Seatmap getSeatMapForLocation(@NotNull Location location) {
        LOGGER.debug("get seat map for location {}", location.getId());
        return seatmapRepository.findByLocation(location);
    }

}
