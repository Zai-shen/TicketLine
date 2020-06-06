package at.ac.tuwien.sepm.groupphase.backend.controller.mapper;

import at.ac.tuwien.sepm.groupphase.backend.dto.BookingDTO;
import at.ac.tuwien.sepm.groupphase.backend.dto.SeatgroupSeatDTO;
import at.ac.tuwien.sepm.groupphase.backend.entity.SeatedTicket;
import at.ac.tuwien.sepm.groupphase.backend.entity.Ticket;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class TicketMapper {

    public List<Ticket> fromDto(BookingDTO bookingDTO) {
        List<Ticket> tickets = new ArrayList<>();

        int freeTicketAmount =
            bookingDTO.getFreeSeats().getAmount() != null ? bookingDTO.getFreeSeats().getAmount() : 0;

        for (int i = 0; i < freeTicketAmount; i++) {
            Ticket ticket = new Ticket();
            ticket.setPrice(BigDecimal.valueOf(23.50)); // TODO: get price from SeatgroupPerformance
            tickets.add(ticket);
        }

        for (SeatgroupSeatDTO fixedSeatgroupBookingDTO : bookingDTO.getFixedSeats()) {
            SeatedTicket seatedTicket = new SeatedTicket();
            seatedTicket.setSeatGroupId(fixedSeatgroupBookingDTO.getSeatgroupId());
            seatedTicket.setSeatColumn(fixedSeatgroupBookingDTO.getY());
            seatedTicket.setSeatRow(fixedSeatgroupBookingDTO.getX());
            seatedTicket.setPrice(BigDecimal.valueOf(31.70)); // TODO: get price from SeatgroupPerformance
            tickets.add(seatedTicket);
        }
        return tickets;
    }


}
