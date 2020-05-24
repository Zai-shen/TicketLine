package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.exception.BusinessValidationException;

import java.util.Optional;

public interface EventService {

    /**
     * @param eventid Id of the event
     * @return returns the event identified by the eventid.
     */
    Optional<Event> getEvent(long eventid);

    /**
     * @param event the event to be created
     * @return returns the id of the created event
     * @throws BusinessValidationException thrown when validation fails
     */
    Long createEvent(Event event) throws BusinessValidationException;
}
