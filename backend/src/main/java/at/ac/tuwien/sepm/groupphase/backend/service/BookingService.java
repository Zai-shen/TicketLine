package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Booking;
import at.ac.tuwien.sepm.groupphase.backend.entity.Ticket;

import java.util.List;

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
     * TODO:
     * @param bookingId
     * @return
     */
    Booking getBookingById(Long bookingId);
}
