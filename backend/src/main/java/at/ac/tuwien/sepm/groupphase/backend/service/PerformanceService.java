package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.Performance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PerformanceService {

    /**
     * @param pageable Pagination information
     * @return returns a list with all Performances. Restricted by the pagination.
     */
    Page<Performance> getAllPerformances(Pageable pageable);

    /**
     * Get all performances for a specific event
     * @param event event to retrieve the performances for
     * @return list of performances for this event
     */
    List<Performance> getPerformancesForEvent(Event event);
}
