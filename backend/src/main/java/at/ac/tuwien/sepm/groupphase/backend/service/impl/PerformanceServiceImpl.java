package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.Booking;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.Performance;
import at.ac.tuwien.sepm.groupphase.backend.repository.BookingRepository;
import at.ac.tuwien.sepm.groupphase.backend.exception.BusinessValidationException;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.PerformanceRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.EventService;
import at.ac.tuwien.sepm.groupphase.backend.service.PerformanceService;
import at.ac.tuwien.sepm.groupphase.backend.service.validator.NewPerformanceValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PerformanceServiceImpl implements PerformanceService {
    private final PerformanceRepository performanceRepository;
    private final EventService eventService;

    public PerformanceServiceImpl(PerformanceRepository performanceRepository,
        EventService eventService) {
        this.performanceRepository = performanceRepository;
        this.eventService = eventService;
    }

    @Override
    public Page<Performance> getAllPerformances(Pageable pageable) {
        return performanceRepository.findAll(pageable);
    }

    @Override
    public Long createPerformance(Long eventId, Performance performance) throws BusinessValidationException {
        if(eventId != null) {
            Event event = eventService.getEvent(eventId).orElseThrow(() ->
                new NotFoundException(String.format("Event mit id=%d existiert nicht", eventId)));
            performance.setEvent(event);
        }
        new NewPerformanceValidator().build(performance).validate();
        return performanceRepository.saveAndFlush(performance).getId();
    }
}
