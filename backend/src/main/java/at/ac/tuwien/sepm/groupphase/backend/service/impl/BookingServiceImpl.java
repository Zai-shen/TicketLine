package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.Booking;
import at.ac.tuwien.sepm.groupphase.backend.entity.Performance;
import at.ac.tuwien.sepm.groupphase.backend.entity.Ticket;
import at.ac.tuwien.sepm.groupphase.backend.entity.User;
import at.ac.tuwien.sepm.groupphase.backend.repository.BookingRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.PerformanceRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.BookingService;
import at.ac.tuwien.sepm.groupphase.backend.service.UserService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {
    private final PerformanceRepository performanceRepository;
    private final UserService userService;
    private final BookingRepository bookingRepository;

    public BookingServiceImpl(PerformanceRepository performanceRepository, UserService userService,
        BookingRepository bookingRepository) {
        this.performanceRepository = performanceRepository;
        this.userService = userService;
        this.bookingRepository = bookingRepository;
    }

    @Override
    @Transactional
    public void bookTickets(Long performanceId, boolean reserve, List<Ticket> tickets) {
        Performance performance = performanceRepository.findById(performanceId).
            orElseThrow(() -> new IllegalArgumentException("Performance not found"));

        Booking booking = new Booking();
        booking.setPerformance(performance);
        booking.setReservation(reserve);
        booking.setTickets(tickets);
        for (Ticket t : tickets) {
            t.setBooking(booking);
        }
        User currentuser = userService.getCurrentLoggedInUser();
        booking.setUser(currentuser);
        currentuser.getBookings().add(booking);
    }

    @Override
    public List<Booking> getAllBookingsOfUser() {
        return userService.getCurrentLoggedInUser().getBookings();
    }

    @Override
    public Booking getBooking(Long bookingId) throws AccessDeniedException {
        var b = bookingRepository.findById(bookingId).orElse(null);
        if(b != null && !userService.getCurrentLoggedInUser().getId().equals(b.getUser().getId()))
            throw new AccessDeniedException("Nur eigene Buchungen können abgerufen werden");
        return b;
    }
}
