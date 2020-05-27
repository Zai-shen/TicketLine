package at.ac.tuwien.sepm.groupphase.backend.controller.mapper;

import at.ac.tuwien.sepm.groupphase.backend.dto.*;
import at.ac.tuwien.sepm.groupphase.backend.entity.Booking;
import at.ac.tuwien.sepm.groupphase.backend.entity.SeatedTicket;
import at.ac.tuwien.sepm.groupphase.backend.entity.Ticket;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface TicketMapper {

    default List<Ticket> fromDto(BookingDTO bookingDTO) {
        List<Ticket> tickets = new ArrayList<>();

        for (int i = 0; i < bookingDTO.getFreeSeats().getAmount(); i++) {
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

    default TicketResponseDTO toDot(List<Booking> bookings) {
        TicketResponseDTO ticketResponseDTO = new TicketResponseDTO();

        for (Booking booking : bookings) {
            for (Ticket ticket : booking.getTickets()) {
                if (ticket instanceof SeatedTicket) {
                    SeatedTicket seatedTicket = (SeatedTicket) ticket;
                    SeatgroupSeatDTO seatgroupSeatDTO = getSeatgroupSeatDTO(seatedTicket);
                    SeatedTicketDTO seatedTicketDTO = new SeatedTicketDTO();
                    seatedTicketDTO.setSeat(seatgroupSeatDTO);
                    seatedTicketDTO.setReserved(booking.getReservation());
                    seatedTicketDTO.setId((ticket.getId().intValue()));
                    ticketResponseDTO.addSeatedTicketsItem(seatedTicketDTO);
                } else {
                    FreeTicketDTO freeTicketDTO = new FreeTicketDTO();
                    freeTicketDTO.setId(ticket.getId().intValue());
                    freeTicketDTO.setReserved(booking.getReservation());
                    ticketResponseDTO.addFreeTicketsItem(freeTicketDTO);
                }
            }
        }
        return ticketResponseDTO;
    }

    default SeatgroupSeatDTO getSeatgroupSeatDTO(SeatedTicket seatedTicket) {
        SeatgroupSeatDTO seatgroupSeatDTO = new SeatgroupSeatDTO();
        seatgroupSeatDTO.setSeatgroupId(seatedTicket.getSeatGroupId());
        seatgroupSeatDTO.setX(seatedTicket.getSeatColumn());
        seatgroupSeatDTO.setY(seatedTicket.getSeatColumn());
        return seatgroupSeatDTO;
    }

}
