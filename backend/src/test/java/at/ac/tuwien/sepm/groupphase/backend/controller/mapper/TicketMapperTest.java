package at.ac.tuwien.sepm.groupphase.backend.controller.mapper;

import at.ac.tuwien.sepm.groupphase.backend.dto.BookingDTO;
import at.ac.tuwien.sepm.groupphase.backend.dto.FreeSeatgroupBookingDTO;
import at.ac.tuwien.sepm.groupphase.backend.dto.SeatgroupSeatDTO;
import at.ac.tuwien.sepm.groupphase.backend.entity.*;
import at.ac.tuwien.sepm.groupphase.backend.service.SeatService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TicketMapperTest {

    @Mock
    private SeatService seatService;
    @InjectMocks
    private TicketMapper ticketMapper;

    @Test
    void fromDto() {
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setFreeSeats(Collections.singletonList(new FreeSeatgroupBookingDTO().amount(3L)));
        bookingDTO.setFixedSeats(Collections.singletonList(new SeatgroupSeatDTO().x(1).y(1).seatgroupId(1L)));

        SeatedTicket seatedTicket = getSeatedTicket();

        when(seatService.byPosition(any(),any(),any())).thenReturn(new Seat());
        SeatGroupArea sga = new SeatGroupArea();
        sga.setPrice(3.50);
        StandingArea sa = new StandingArea();
        sa.setPrice(3.50);
        when(seatService.getArea(any())).thenReturn(sga);
        when(seatService.getStandingArea(any())).thenReturn(sa);
        List<Ticket> ticketList = ticketMapper.fromDto(bookingDTO);

        // We expect one standing and one seated ticket
        assertThat(ticketList.size()).isEqualTo(2);
        assertThat(ticketList).contains(seatedTicket);
        verify(seatService,times(1)).byPosition(any(),any(),any());
        verify(seatService,times(1)).getArea(any());
        verify(seatService,times(1)).getStandingArea(any());
    }

    private SeatedTicket getSeatedTicket() {
        SeatedTicket seatedTicket = new SeatedTicket();
        Seat s = new Seat();
        s.setX(1D);
        s.setY(1D);
        seatedTicket.setSeat(s);
        return seatedTicket;
    }
}