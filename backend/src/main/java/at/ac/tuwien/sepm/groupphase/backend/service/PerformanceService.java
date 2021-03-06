package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.dto.SeatmapOccupationDTO;
import at.ac.tuwien.sepm.groupphase.backend.entity.Performance;
import at.ac.tuwien.sepm.groupphase.backend.entity.SearchPerformance;
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

    /**
     * Get the seatmap for a specific performance
     * @param performanceId id of the performance
     * @return SeatmapOccupationDTO containing free, reserved and sold seats as well as the amount of tickets sold for standing areas
     */
    SeatmapOccupationDTO getSeatmap(Long performanceId);

    /**
     * Searches for performances based on performance example
     * @param performance object to be searched
     * @param pageable Pagination information
     * @return List of Performances that fit search object of specified size by pagination
     */
    Page<Performance> searchPerformances(SearchPerformance performance, Pageable pageable);
}
