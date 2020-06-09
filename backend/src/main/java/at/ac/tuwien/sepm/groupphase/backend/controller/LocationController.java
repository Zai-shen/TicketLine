package at.ac.tuwien.sepm.groupphase.backend.controller;

import at.ac.tuwien.sepm.groupphase.backend.api.LocationApi;
import at.ac.tuwien.sepm.groupphase.backend.controller.mapper.LocationMapper;
import at.ac.tuwien.sepm.groupphase.backend.controller.mapper.PerformanceMapper;
import at.ac.tuwien.sepm.groupphase.backend.dto.*;
import at.ac.tuwien.sepm.groupphase.backend.security.AuthorizationRole;
import at.ac.tuwien.sepm.groupphase.backend.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class LocationController implements LocationApi {

    private final LocationService locationService;
    private final LocationMapper locationMapper;
    private final PerformanceMapper performanceMapper;

    private static final int PAGE_SIZE = 25;

    @Autowired
    public LocationController(LocationService locationService, LocationMapper locationMapper,
        PerformanceMapper performanceMapper) {
        this.locationService = locationService;
        this.locationMapper = locationMapper;
        this.performanceMapper = performanceMapper;
    }

    @Override
    @Secured(AuthorizationRole.ADMIN_ROLE)
    public ResponseEntity<Void> createLocation(@Valid LocationDTO locationDTO) {
        locationService.createLocation(locationMapper.locationDtoToLocation(locationDTO));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<LocationDTO>> getLocationList(@Valid Optional<Integer> page) {
        var list = locationMapper.locationsToLocationsDto(locationService.getLocationList(
            PageRequest.of(page.orElse(0), PAGE_SIZE)).getContent());
        return ResponseEntity.ok(list);
    }

    @Override
    public ResponseEntity<List<LocationDTO>> searchLocations(@Valid SearchLocationDTO searchLocationDTO) {
        var list = locationMapper.locationsToLocationsDto(locationService.searchLocations(
            locationMapper.searchLocationDtoToLocation(searchLocationDTO)));
        return ResponseEntity.ok(list);
    }

    @Override
    public ResponseEntity<List<PerformanceDTO>> getPerformancesOfLocation(Long locationId) {
        var list = locationService.performancesForLocation(locationId);
        return ResponseEntity.ok(performanceMapper.toDto(list));
    }
}
