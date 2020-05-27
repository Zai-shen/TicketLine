package at.ac.tuwien.sepm.groupphase.backend.controller.mapper;

import at.ac.tuwien.sepm.groupphase.backend.dto.*;
import at.ac.tuwien.sepm.groupphase.backend.entity.Booking;
import at.ac.tuwien.sepm.groupphase.backend.entity.SeatedTicket;
import at.ac.tuwien.sepm.groupphase.backend.entity.Ticket;
import org.junit.jupiter.api.Test;

import java.awt.print.Book;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class TicketMapperTest {

    TicketMapper ticketMapper = new TicketMapperImpl();

    @Test
    void fromDto() {
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setFreeSeats(new FreeSeatgroupBookingDTO().amount(3));
        bookingDTO.setFixedSeats(Collections.singletonList(new SeatgroupSeatDTO().x(1).y(1).seatgroupId(1L)));

        SeatedTicket seatedTicket = getSeatedTicket();

        List<Ticket> ticketList = ticketMapper.fromDto(bookingDTO);

        assertThat(ticketList.size()).isEqualTo(4);
        assertThat(ticketList).contains(seatedTicket);

    }

    private SeatedTicket getSeatedTicket() {
        SeatedTicket seatedTicket = new SeatedTicket();
        seatedTicket.setSeatRow(1);
        seatedTicket.setSeatColumn(1);
        seatedTicket.setSeatGroupId(1L);
        return seatedTicket;
    }

    private Ticket getTicket() {
        Ticket ticket = new Ticket();
        ticket.setId(2L);
        return ticket;
    }

    @Test
    void toDot() {
        Booking reservation = new Booking();
        reservation.setReservation(true);
        SeatedTicket seatedTicket = getSeatedTicket();
        seatedTicket.setId(1L);
        reservation.setTickets(Collections.singletonList(seatedTicket));

        Booking booking = new Booking();
        booking.setReservation(false);
        booking.setTickets(Collections.singletonList(getTicket()));


        TicketResponseDTO ticketResponseDTO = ticketMapper.toDot(Arrays.asList(reservation, booking));
        assertThat(ticketResponseDTO.getFreeTickets()).containsExactly(new FreeTicketDTO().id(2).reserved(false));
        assertThat(ticketResponseDTO.getSeatedTickets()).containsExactly(new SeatedTicketDTO().id(1).seat(new SeatgroupSeatDTO().seatgroupId(1L).x(1).y(1)).reserved(true));

    }
}