package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.Performance;
import at.ac.tuwien.sepm.groupphase.backend.exception.BusinessValidationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PerformanceService {

    /**
     * @param pageable Pagination information
     * @return returns a list with all Performances. Restricted by the pagination.
     */
    Page<Performance> getAllPerformances(Pageable pageable);


    /**
     * Create a new Performance
     * @param performance the performance object to be created
     * @throws BusinessValidationException if location doesn't comply with business validation
     */
    Long createPerformance(Long eventId, Performance performance) throws BusinessValidationException;
}
