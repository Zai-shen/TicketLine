package at.ac.tuwien.sepm.groupphase.backend.controller.mapper;

import at.ac.tuwien.sepm.groupphase.backend.dto.BookingDTO;
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

    public List<Ticket> fromDto(BookingDTO bookingDTO) {
        List<Ticket> tickets = new ArrayList<>();

        for(SeatgroupSeatDTO seated : bookingDTO.getFixedSeats()) {
            SeatGroupArea area = seatService.getArea(seated.getSeatgroupId());
            Seat s = seatService.byPosition(area,seated.getX().doubleValue(),seated.getY().doubleValue());
            SeatedTicket t = new SeatedTicket();
            t.setSeat(s);
            t.setPrice(BigDecimal.valueOf(3.50));
            tickets.add(t);
        }
        for(FreeSeatgroupBookingDTO free : bookingDTO.getFreeSeats()) {
            StandingArea area = seatService.getStandingArea(free.getSeatGroupId());
            StandingTicket t = new StandingTicket();
            t.setAmount(free.getAmount().longValue());
            t.setStandingArea(area);
            t.setPrice(BigDecimal.valueOf(3.50));
            tickets.add(t);
        }
        return tickets;
    }


}
