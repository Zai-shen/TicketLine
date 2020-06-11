package at.ac.tuwien.sepm.groupphase.backend.controller.mapper;

import at.ac.tuwien.sepm.groupphase.backend.dto.*;
import at.ac.tuwien.sepm.groupphase.backend.entity.Seat;
import at.ac.tuwien.sepm.groupphase.backend.entity.SeatedTicket;
import at.ac.tuwien.sepm.groupphase.backend.entity.Ticket;
import at.ac.tuwien.sepm.groupphase.backend.service.SeatService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
class TicketMapperTest {

    private TicketMapper ticketMapper;

    TicketMapperTest(SeatService seatService) {
        ticketMapper = new TicketMapper(seatService);
    }

    @Test
    void fromDto() {
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setFreeSeats(Collections.singletonList(new FreeSeatgroupBookingDTO().amount(3L)));
        bookingDTO.setFixedSeats(Collections.singletonList(new SeatgroupSeatDTO().x(1).y(1).seatgroupId(1L)));

        SeatedTicket seatedTicket = getSeatedTicket();

        List<Ticket> ticketList = ticketMapper.fromDto(bookingDTO);

        assertThat(ticketList.size()).isEqualTo(4);
        assertThat(ticketList).contains(seatedTicket);
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