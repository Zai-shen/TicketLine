package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.Booking;
import at.ac.tuwien.sepm.groupphase.backend.entity.Performance;
import at.ac.tuwien.sepm.groupphase.backend.entity.Ticket;
import at.ac.tuwien.sepm.groupphase.backend.repository.PerformanceRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.BookingService;
import at.ac.tuwien.sepm.groupphase.backend.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {
    private final PerformanceRepository performanceRepository;
    private final UserService userService;

    public BookingServiceImpl(PerformanceRepository performanceRepository, UserService userService) {
        this.performanceRepository = performanceRepository;
        this.userService = userService;
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
        userService.getCurrentLoggedInUser().getBookings().add(booking);
    }

    @Override
    public List<Booking> getAllBookingsOfUser() {
        return userService.getCurrentLoggedInUser().getBookings();
    }
}
