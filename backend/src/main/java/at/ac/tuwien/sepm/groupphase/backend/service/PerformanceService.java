package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Performance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PerformanceService {

    /**
     * @param pageable Pagination information
     * @return returns a list with all Performances. Restricted by the pagination.
     */
    Page<Performance> getAllPerformances(Pageable pageable);
}
