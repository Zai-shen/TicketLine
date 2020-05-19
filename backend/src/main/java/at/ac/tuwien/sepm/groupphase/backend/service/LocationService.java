package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Location;
import at.ac.tuwien.sepm.groupphase.backend.exception.BusinessValidationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LocationService {

    /**
     * Create a new Location
     * @param location the location object containing the address
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
     * Search locations base on description and address.
     *
     * @param location example of a location to be queried
     * @return queried location entries
     */
    List<Location> searchLocations(Location location);
}
