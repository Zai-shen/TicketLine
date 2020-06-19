package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.*;
import at.ac.tuwien.sepm.groupphase.backend.repository.PerformanceRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.impl.BookingServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private PerformanceRepository performanceRepository;

    @InjectMocks
    private BookingServiceImpl bookingService;

    @Test
    void bookTickets() {
        User user = new User();
        user.setId(1L);
        user.setBookings(new ArrayList<>());
        when(userService.getCurrentLoggedInUser()).thenReturn(user);
        when(performanceRepository.findById(1L)).thenReturn(java.util.Optional.of(new Performance()));

        bookingService.bookTickets(1L, false, getTickets());
        List<Ticket> createdTickets = user.getBookings().get(0).getTickets();
        assertThat(user.getBookings().get(0).getReservation()).isFalse();
        assertThat(createdTickets.size()).isEqualTo(getTickets().size());
    }

    @Test
    void getAllBookingsOfUser() {
        User user = new User();
        user.setId(1L);
        user.setBookings(getBookings());
        when(userService.getCurrentLoggedInUser()).thenReturn(user);

        assertThat(bookingService.getAllBookingsOfUser()).isEqualTo(getBookings());
    }

    @Test
    void testPerformanceNotFound() {
        when(performanceRepository.findById(1L)).thenReturn(java.util.Optional.empty());
        assertThatThrownBy(() -> bookingService.bookTickets(1L,true, Collections.emptyList())).isExactlyInstanceOf(
            IllegalArgumentException.class);    }

    private List<Ticket> getTickets() {
        StandingTicket ticket = new StandingTicket();
        ticket.setAmount(2L);
        ticket.setId(1L);
        return Collections.singletonList(ticket);
    }

    private List<Booking> getBookings() {
        Booking booking = new Booking();
        booking.setTickets(getTickets());
        booking.setReservation(false);
        return Collections.singletonList(booking);
    }
}