package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Event;

import java.util.Optional;

public interface EventService {

    /**
     * @param eventid Id of the event
     * @return returns the event identified by the eventid.
     */
    Optional<Event> getEvent(long eventid);
}
