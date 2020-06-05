package at.ac.tuwien.sepm.groupphase.backend.controller.mapper;

import at.ac.tuwien.sepm.groupphase.backend.dto.*;
import at.ac.tuwien.sepm.groupphase.backend.entity.Booking;
import at.ac.tuwien.sepm.groupphase.backend.entity.SeatedTicket;
import at.ac.tuwien.sepm.groupphase.backend.entity.Ticket;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class TicketMapper {

    public Set<Ticket> fromDto(BookingDTO bookingDTO) {
        Set<Ticket> tickets = new HashSet<>();

        int freeTicketAmount =
            bookingDTO.getFreeSeats().getAmount() != null ? bookingDTO.getFreeSeats().getAmount() : 0;

        for (int i = 0; i < freeTicketAmount; i++) {
            Ticket ticket = new Ticket();
            tickets.add(ticket);
        }

        for (SeatgroupSeatDTO fixedSeatgroupBookingDTO : bookingDTO.getFixedSeats()) {
            SeatedTicket seatedTicket = new SeatedTicket();
            seatedTicket.setSeatGroupId(fixedSeatgroupBookingDTO.getSeatgroupId());
            seatedTicket.setSeatColumn(fixedSeatgroupBookingDTO.getY());
            seatedTicket.setSeatRow(fixedSeatgroupBookingDTO.getX());
            tickets.add(seatedTicket);
        }
        return tickets;
    }


}
