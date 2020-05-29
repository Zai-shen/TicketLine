package at.ac.tuwien.sepm.groupphase.backend.controller.mapper;

import at.ac.tuwien.sepm.groupphase.backend.dto.*;
import at.ac.tuwien.sepm.groupphase.backend.entity.Booking;
import at.ac.tuwien.sepm.groupphase.backend.entity.SeatedTicket;
import at.ac.tuwien.sepm.groupphase.backend.entity.Ticket;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Mapper
public interface BookingMapper {
    BookingDTO fromEntity(Booking booking);

    default List<BookingDTO> fromEntity(List<Booking> bookings) {
        List<BookingDTO> outlist = new ArrayList<>();
        for (Booking booking : bookings) {
            BookingDTO outbooking = fromEntity(booking);
            outbooking.setFixedSeats(new LinkedList<>());
            int amount = 0;
            for (Ticket ticket : booking.getTickets()) {
                if (ticket instanceof SeatedTicket) {
                    SeatedTicket seatedTicket = (SeatedTicket) ticket;
                    outbooking.getFixedSeats().add(getSeatgroupSeatDTO(seatedTicket));
                } else {
                    amount++;
                }
            }
            outbooking.setFreeSeats(new FreeSeatgroupBookingDTO().amount(amount));
            outlist.add(outbooking);
        }
        return outlist;
    }


    default SeatgroupSeatDTO getSeatgroupSeatDTO(SeatedTicket seatedTicket) {
        SeatgroupSeatDTO seatgroupSeatDTO = new SeatgroupSeatDTO();
        seatgroupSeatDTO.setSeatgroupId(seatedTicket.getSeatGroupId());
        seatgroupSeatDTO.setX(seatedTicket.getSeatColumn());
        seatgroupSeatDTO.setY(seatedTicket.getSeatColumn());
        return seatgroupSeatDTO;
    }
}
