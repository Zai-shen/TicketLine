package at.ac.tuwien.sepm.groupphase.backend.controller.mapper;

import at.ac.tuwien.sepm.groupphase.backend.dto.BookingDTO;
import at.ac.tuwien.sepm.groupphase.backend.dto.FreeSeatgroupBookingDTO;
import at.ac.tuwien.sepm.groupphase.backend.dto.SeatgroupSeatDTO;
import at.ac.tuwien.sepm.groupphase.backend.entity.Seat;
import at.ac.tuwien.sepm.groupphase.backend.entity.SeatedTicket;
import at.ac.tuwien.sepm.groupphase.backend.entity.Ticket;
import at.ac.tuwien.sepm.groupphase.backend.service.SeatService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

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

        List<Ticket> ticketList = ticketMapper.fromDto(bookingDTO);

        // We expect one standing and one seated ticket
        assertThat(ticketList.size()).isEqualTo(2);
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