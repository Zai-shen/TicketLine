package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.config.properties.CompanyProperties;
import at.ac.tuwien.sepm.groupphase.backend.dto.InvoiceData;
import at.ac.tuwien.sepm.groupphase.backend.entity.Booking;
import at.ac.tuwien.sepm.groupphase.backend.entity.Performance;
import at.ac.tuwien.sepm.groupphase.backend.entity.Ticket;
import at.ac.tuwien.sepm.groupphase.backend.dto.ByteArrayFile;
import at.ac.tuwien.sepm.groupphase.backend.dto.TicketData;
import at.ac.tuwien.sepm.groupphase.backend.entity.*;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.BookingRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.PerformanceRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.BookingService;
import at.ac.tuwien.sepm.groupphase.backend.service.TicketService;
import at.ac.tuwien.sepm.groupphase.backend.service.UserService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
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
    private CompanyProperties companyProperties;

    public BookingServiceImpl(PerformanceRepository performanceRepository, UserService userService,
        BookingRepository bookingRepository, TicketService ticketService, CompanyProperties companyProperties) {
        this.performanceRepository = performanceRepository;
        this.userService = userService;
        this.bookingRepository = bookingRepository;
        this.ticketService = ticketService;
        this.companyProperties = companyProperties;
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
        booking.setDate(LocalDate.now());
        booking.setCanceled(false);
        for (Ticket t : tickets) {
            t.setUuid(UUID.randomUUID());
            t.setBooking(booking);
        }
        User currentUser = userService.getCurrentLoggedInUser();
        booking.setUser(currentUser);
        currentUser.getBookings().add(booking);
    }

    @Override
    public List<Booking> getAllBookingsOfUser() {
        return userService.getCurrentLoggedInUser().getBookings();
    }

    @Override
    @Transactional(readOnly = true)
    public Booking getBookingOfCurrentUser(Long bookingId) throws AccessDeniedException,NotFoundException {
        var b = bookingRepository.findById(bookingId).orElseThrow(NotFoundException::new);
        if(b != null && !userService.getCurrentLoggedInUser().getId().equals(b.getUser().getId()))
            throw new AccessDeniedException("Nur eigene Buchungen können abgerufen werden");
        return b;
    }

    @Override
    public ByteArrayFile renderBooking(Booking booking) {
        List<TicketData> tickets = new LinkedList<>();
        for(Ticket ticket : booking.getTickets()) {
            String area = null;
            String seat = null;
            if (ticket instanceof SeatedTicket) {
                Seat s = ((SeatedTicket) ticket).getSeat();
                seat = String.format("Reihe %s Platz %s",s.getRowLabel(),s.getColLabel());
                area = s.getSeatGroupArea().getName();
            } else if (ticket instanceof StandingTicket) {
                area = ((StandingTicket) ticket).getStandingArea().getName();
                seat = String.format("%dx Freie Platzwahl", ((StandingTicket) ticket).getAmount());
            }
            tickets.add(new TicketData(
                booking.getPerformance().getEvent(),
                seat,
                area,
                booking.getPerformance(),
                ticket.getUuid(),
                ticket.getPrice()));
        }
        return ticketService.renderTickets(tickets);
    }

    @Override
    public ByteArrayFile renderInvoice(Booking booking) {
        User user = userService.getCurrentLoggedInUser();
        InvoiceData invoice = new InvoiceData(booking, user, booking.getCanceled(), companyProperties);
        return ticketService.renderInvoice(invoice);
    }

    @Override
    public List<Booking> getBookingsForPerformance(@NotNull Performance p) {
        return bookingRepository.findAllByPerformance(p);
    }
}
