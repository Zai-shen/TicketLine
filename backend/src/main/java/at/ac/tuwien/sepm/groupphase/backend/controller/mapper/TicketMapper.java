package at.ac.tuwien.sepm.groupphase.backend.controller.mapper;

import at.ac.tuwien.sepm.groupphase.backend.dto.BookingDTO;
import at.ac.tuwien.sepm.groupphase.backend.dto.BookingRequestDTO;
import at.ac.tuwien.sepm.groupphase.backend.dto.FreeSeatgroupBookingDTO;
import at.ac.tuwien.sepm.groupphase.backend.dto.SeatgroupSeatDTO;
import at.ac.tuwien.sepm.groupphase.backend.entity.*;
import at.ac.tuwien.sepm.groupphase.backend.service.SeatService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class TicketMapper {
    private final SeatService seatService;

    public TicketMapper(SeatService seatService) {
        this.seatService = seatService;
    }

    public List<Ticket> fromDto(BookingRequestDTO bookingDTO) {
        List<Ticket> tickets = new ArrayList<>();

        for(Long i : bookingDTO.getSeats()) {
            Seat s = seatService.getSeat(i);
            SeatedTicket t = new SeatedTicket();
            t.setSeat(s);
            t.setPrice(BigDecimal.valueOf(s.getSeatGroupArea().getPrice()));
            tickets.add(t);
        }
        for(FreeSeatgroupBookingDTO free : bookingDTO.getAreas()) {
            StandingArea area = seatService.getStandingArea(free.getSeatGroupId());
            StandingTicket t = new StandingTicket();
            t.setAmount(free.getAmount());
            t.setStandingArea(area);
            t.setPrice(BigDecimal.valueOf(area.getPrice()));
            tickets.add(t);
        }
        return tickets;
    }


}
