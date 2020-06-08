package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.Performance;
import at.ac.tuwien.sepm.groupphase.backend.entity.SearchPerformance;
import at.ac.tuwien.sepm.groupphase.backend.exception.BusinessValidationException;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.PerformanceRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.SearchPerformanceSpecification;
import at.ac.tuwien.sepm.groupphase.backend.service.EventService;
import at.ac.tuwien.sepm.groupphase.backend.service.PerformanceService;
import at.ac.tuwien.sepm.groupphase.backend.service.validator.NewPerformanceValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;

@Service
public class PerformanceServiceImpl implements PerformanceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

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

    @Override
    public Page<Performance> searchPerformances(SearchPerformance performance, Pageable pageable) {
        LOGGER.debug("search performances");

        Specification<Performance> specification = new SearchPerformanceSpecification(performance);
        return performanceRepository.findAll(specification, pageable);
    }
}
