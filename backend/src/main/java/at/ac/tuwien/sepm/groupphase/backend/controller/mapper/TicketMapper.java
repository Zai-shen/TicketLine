package at.ac.tuwien.sepm.groupphase.backend.controller.mapper;

import at.ac.tuwien.sepm.groupphase.backend.dto.*;
import at.ac.tuwien.sepm.groupphase.backend.entity.Booking;
import at.ac.tuwien.sepm.groupphase.backend.entity.SeatedTicket;
import at.ac.tuwien.sepm.groupphase.backend.entity.Ticket;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class TicketMapper {

    public List<Ticket> fromDto(BookingDTO bookingDTO) {
        List<Ticket> tickets = new ArrayList<>();

        int freeTicketAmount =
            bookingDTO.getFreeSeats().getAmount() != null ? bookingDTO.getFreeSeats().getAmount() : 0;

        for (int i = 0; i < freeTicketAmount; i++) {
            Ticket ticket = new Ticket();
            ticket.setPrice(BigDecimal.valueOf(23.50));
            tickets.add(ticket);
        }

        for (SeatgroupSeatDTO fixedSeatgroupBookingDTO : bookingDTO.getFixedSeats()) {
            SeatedTicket seatedTicket = new SeatedTicket();
            seatedTicket.setSeatGroupId(fixedSeatgroupBookingDTO.getSeatgroupId());
            seatedTicket.setSeatColumn(fixedSeatgroupBookingDTO.getY());
            seatedTicket.setSeatRow(fixedSeatgroupBookingDTO.getX());
            seatedTicket.setPrice(BigDecimal.valueOf(31.70));
            tickets.add(seatedTicket);
        }
        return tickets;
    }

    public TicketResponseDTO toDto(List<Booking> bookings) {
        TicketResponseDTO ticketResponseDTO = new TicketResponseDTO();

        for (Booking booking : bookings) {
            for (Ticket ticket : booking.getTickets()) {
                if (ticket instanceof SeatedTicket) {
                    SeatedTicket seatedTicket = (SeatedTicket) ticket;
                    SeatgroupSeatDTO seatgroupSeatDTO = getSeatgroupSeatDTO(seatedTicket);
                    SeatedTicketDTO seatedTicketDTO = new SeatedTicketDTO();
                    seatedTicketDTO.setSeat(seatgroupSeatDTO);
                    seatedTicketDTO.setReserved(booking.getReservation());
                    seatedTicketDTO.setId(ticket.getId());
                    ticketResponseDTO.addSeatedTicketsItem(seatedTicketDTO);
                } else {
                    FreeTicketDTO freeTicketDTO = new FreeTicketDTO();
                    freeTicketDTO.setId(ticket.getId());
                    freeTicketDTO.setReserved(booking.getReservation());
                    ticketResponseDTO.addFreeTicketsItem(freeTicketDTO);
                }
            }
        }
        return ticketResponseDTO;
    }

    private SeatgroupSeatDTO getSeatgroupSeatDTO(SeatedTicket seatedTicket) {
        SeatgroupSeatDTO seatgroupSeatDTO = new SeatgroupSeatDTO();
        seatgroupSeatDTO.setSeatgroupId(seatedTicket.getSeatGroupId());
        seatgroupSeatDTO.setX(seatedTicket.getSeatColumn());
        seatgroupSeatDTO.setY(seatedTicket.getSeatColumn());
        return seatgroupSeatDTO;
    }

}
