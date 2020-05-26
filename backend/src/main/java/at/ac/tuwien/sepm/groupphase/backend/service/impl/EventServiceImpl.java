package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.exception.BusinessValidationException;
import at.ac.tuwien.sepm.groupphase.backend.repository.EventRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.EventService;
import at.ac.tuwien.sepm.groupphase.backend.service.validator.NewEventValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EventServiceImpl  implements EventService {
    private final EventRepository eventRepository;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
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
}
