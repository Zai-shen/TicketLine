package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.dto.ByteArrayFile;
import at.ac.tuwien.sepm.groupphase.backend.entity.Booking;
import at.ac.tuwien.sepm.groupphase.backend.entity.Ticket;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import org.springframework.security.access.AccessDeniedException;

import java.util.List;
import java.util.Set;

public interface BookingService {
    /**
     * Bookes a ticket for the current logged in user
     * @param performanceId Performance for which to book tickets
     * @param reserve should the tickets be reserved or bought
     * @param tickets a list of tickets to buy
     */
    void bookTickets(Long performanceId, boolean reserve, List<Ticket> tickets);

    /**
     * @return list of all bookings of a user
     */
    List<Booking> getAllBookingsOfUser();

    /**
     * Searches for a specific booking
     * @param bookingId id of the booking to fetch
     * @return booking made by the current user
     */
    Booking getBookingOfCurrentUser(Long bookingId) throws AccessDeniedException, NotFoundException;

    /**
     * Utilizes the ticket rendering engine to produce a pdf from all the tickets of a booking
     * @param booking booking to be printed
     * @return pdf document containing all tickets
     */
    ByteArrayFile renderBooking(Booking booking);

    /**
     * Utilizes the invoice rendering engine to produce a pdf from all the tickets of a booking
     * @param booking booking to be printed
     * @param cancel if true a cancellation of the invoice will be printed
     * @return pdf document contains invoice for all tickets
     */
    ByteArrayFile renderInvoice(Booking booking, boolean cancel);
}
