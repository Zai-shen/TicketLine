package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.dto.SeatmapOccupationDTO;
import at.ac.tuwien.sepm.groupphase.backend.entity.CategoryEnum;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.Performance;
import at.ac.tuwien.sepm.groupphase.backend.exception.BusinessValidationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

public interface EventService {

    /**
     * @param eventid Id of the event
     * @return returns the event identified by the eventid.
     */
    Optional<Event> getEvent(long eventid);

    /**
     * @param eventid Id of the event
     * @return returns a list of all performances of an event.
     */
    List<Performance> getPerformances(long eventid);

    /**
     * @param event the event to be created
     * @return returns the id of the created event
     * @throws BusinessValidationException thrown when validation fails
     */
    Long createEvent(Event event) throws BusinessValidationException;

    /**
     * Search for persisted events
     * @param searchEvent containing data to filter for
     * @param pageRequest pagination info
     * @return page of events that match the filter criteria
     */
    Page<Event> searchEvents(Event searchEvent, PageRequest pageRequest);


    /**
     * Get the top ten events by ticket sales
     * @param category category which to search for
     * @return ten (or less) events in this month ordered by their ticket sales
     */
    List<Event> getTopTen(CategoryEnum category);

    /**
     * cancels all tickets in a booking
     * @param bookingId if of the booking to be canceled
     * @throws BusinessValidationException when the booking does not belong to the user
     */
    void cancelBooking(Long bookingId);

}
