package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.dto.ByteArrayFile;
import at.ac.tuwien.sepm.groupphase.backend.dto.TicketData;
import at.ac.tuwien.sepm.groupphase.backend.entity.*;
import at.ac.tuwien.sepm.groupphase.backend.repository.BookingRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.PerformanceRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.BookingService;
import at.ac.tuwien.sepm.groupphase.backend.service.TicketService;
import at.ac.tuwien.sepm.groupphase.backend.service.UserService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Service
public class BookingServiceImpl implements BookingService {
    private final PerformanceRepository performanceRepository;
    private final UserService userService;
    private final BookingRepository bookingRepository;
    private final TicketService ticketService;

    public BookingServiceImpl(PerformanceRepository performanceRepository, UserService userService,
        BookingRepository bookingRepository, TicketService ticketService) {
        this.performanceRepository = performanceRepository;
        this.userService = userService;
        this.bookingRepository = bookingRepository;
        this.ticketService = ticketService;
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

    @Override
    public ByteArrayFile renderBooking(Booking booking) {
        List<TicketData> tickets = new LinkedList<>();
        for(Ticket ticket : booking.getTickets()) {
            String seat = "Freie Platzwahl";
            if (ticket instanceof SeatedTicket) {
                seat = String.format("Reihe %d Platz %d",((SeatedTicket) ticket).getSeatRow(),((SeatedTicket) ticket).getSeatColumn());
            }
            tickets.add(new TicketData(
                booking.getPerformance().getEvent(),
                seat,
                booking.getPerformance(),
                UUID.randomUUID(),
                BigDecimal.valueOf(3.50)));
        }
        return ticketService.renderTickets(tickets);
    }
}
