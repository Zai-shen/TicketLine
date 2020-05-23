package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.Location;
import at.ac.tuwien.sepm.groupphase.backend.exception.BusinessValidationException;

public interface EventService {

    /**
     * Create a new Event
     * @param event the event object to be created
     * @throws BusinessValidationException if location doesn't comply with business validation
     */
    Long createEvent(Event event) throws BusinessValidationException;

    Event getEvent(Long eventId);
}
