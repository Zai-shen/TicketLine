package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Location;
import at.ac.tuwien.sepm.groupphase.backend.entity.Performance;
import at.ac.tuwien.sepm.groupphase.backend.exception.BusinessValidationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LocationService {

    /**
     * Create a new Location
     * @param location the location object containing the address and seatmap
     * @throws BusinessValidationException if location doesn't comply with business validation
     */
    void createLocation(Location location) throws BusinessValidationException;

    /**
     * Get all locations ordered by published at date (descending).
     * Results are paginated (e.g. 25 results per page)
     *
     * @param page the page number (default: 0)
     * @return list of locations at requested page number
     */
    Page<Location> getLocationList(Pageable page);

    /**
     * Get all locations without pagination.
     * Used for dropdown search when creating new event.
     * @return list of all locations
     */
    List<Location> getAllLocations();
    /**
     * Search locations base on description and address.
     * Results are paginated (e.g. 25 results per page)
     *
     * @param location example of a location to be queried
     * @param page the page number (default: 0)
     * @return queried location entries
     */
    Page<Location> searchLocations(Location location, Pageable page);

    /**
     * Get performances of a specific location
     *
     * @param locationId id of the location to fetch the performances for
     * @return list of perforrmances at this location
     */
    List<Performance> performancesForLocation(Long locationId);
}
