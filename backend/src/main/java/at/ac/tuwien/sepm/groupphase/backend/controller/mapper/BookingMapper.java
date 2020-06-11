package at.ac.tuwien.sepm.groupphase.backend.controller.mapper;

import at.ac.tuwien.sepm.groupphase.backend.dto.*;
import at.ac.tuwien.sepm.groupphase.backend.entity.*;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Mapper
public interface BookingMapper {
    BookingDTO toDto(Booking booking);

    default List<BookingDTO> toDto(List<Booking> bookings) {
        List<BookingDTO> outlist = new ArrayList<>(bookings.size());
        for (Booking booking : bookings) {
            BookingDTO outbooking = toDto(booking);
            outbooking.setFixedSeats(new LinkedList<>());
            outbooking.setFreeSeats(new LinkedList<>());
            for (Ticket t : booking.getTickets()) {
                if(t instanceof StandingTicket) {
                    outbooking.getFreeSeats().add(getFreeSeatDTO((StandingTicket)t));
                }
                if(t instanceof SeatedTicket) {
                    outbooking.getFixedSeats().add(getSeatgroupSeatDTO(((SeatedTicket) t).getSeat()));
                }
            }
            outlist.add(outbooking);
        }
        return outlist;
    }


    default SeatgroupSeatDTO getSeatgroupSeatDTO(Seat seat) {
        SeatgroupSeatDTO seatgroupSeatDTO = new SeatgroupSeatDTO();
        seatgroupSeatDTO.setSeatgroupId(seat.getId());
        seatgroupSeatDTO.setX(seat.getX().intValue());
        seatgroupSeatDTO.setY(seat.getY().intValue());
        return seatgroupSeatDTO;
    }

    default FreeSeatgroupBookingDTO getFreeSeatDTO(StandingTicket t) {
        FreeSeatgroupBookingDTO freeSeatgroupBookingDTO = new FreeSeatgroupBookingDTO();
        freeSeatgroupBookingDTO.setAmount(t.getAmount());
        freeSeatgroupBookingDTO.setSeatGroupId(t.getSeatGroupArea().getId());
        return freeSeatgroupBookingDTO;
    }
}
