package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.Performance;
import at.ac.tuwien.sepm.groupphase.backend.exception.BusinessValidationException;
import at.ac.tuwien.sepm.groupphase.backend.repository.EventRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.PerformanceRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.EventService;
import at.ac.tuwien.sepm.groupphase.backend.service.validator.NewEventValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final PerformanceRepository performanceRepository;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository, PerformanceRepository performanceRepository) {
        this.eventRepository = eventRepository;
        this.performanceRepository = performanceRepository;
    }

    @Override
    public Long createEvent(Event event) throws BusinessValidationException {
        new NewEventValidator().build(event).validate();
        return this.eventRepository.saveAndFlush(event).getId();
    }

    @Override
    public Optional<Event> getEvent(long eventid) {
        return eventRepository.findById(eventid);
    }

    @Override
    public List<Performance> getPerformances(long eventid) {
        return performanceRepository.findByEvent(
            eventRepository.findById(eventid).orElseThrow(() -> new IllegalArgumentException("Event not found")));
    }
}
