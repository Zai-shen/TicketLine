package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.controller.mapper.LocationMapper;
import at.ac.tuwien.sepm.groupphase.backend.controller.mapper.LocationMapperImpl;
import at.ac.tuwien.sepm.groupphase.backend.dto.SeatmapDTO;
import at.ac.tuwien.sepm.groupphase.backend.entity.*;
import at.ac.tuwien.sepm.groupphase.backend.service.EventService;
import at.ac.tuwien.sepm.groupphase.backend.service.LocationService;
import at.ac.tuwien.sepm.groupphase.backend.service.PerformanceService;
import at.ac.tuwien.sepm.groupphase.backend.util.DomainTestObjectFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
public class SeatmapOccupationTest {

    private static final String RAW_SEATMAP = "{\"seatGroupAreas\":[{\"x\":181.0,\"y\":223.0,\"width\":102.0,\"height\":105.0,\"price\":15.0,\"name\":\"Bereich-1\",\"seats\":[{\"x\":23.0,\"y\":23.0,\"rowLabel\":\"a\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":23.0,\"rowLabel\":\"a\",\"colLabel\":\"2\",\"radius\":5.0},{\"x\":51.0,\"y\":23.0,\"rowLabel\":\"a\",\"colLabel\":\"3\",\"radius\":5.0},{\"x\":65.0,\"y\":23.0,\"rowLabel\":\"a\",\"colLabel\":\"4\",\"radius\":5.0},{\"x\":79.0,\"y\":23.0,\"rowLabel\":\"a\",\"colLabel\":\"5\",\"radius\":5.0},{\"x\":93.0,\"y\":23.0,\"rowLabel\":\"a\",\"colLabel\":\"6\",\"radius\":5.0},{\"x\":23.0,\"y\":37.0,\"rowLabel\":\"b\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":37.0,\"rowLabel\":\"b\",\"colLabel\":\"2\",\"radius\":5.0},{\"x\":51.0,\"y\":37.0,\"rowLabel\":\"b\",\"colLabel\":\"3\",\"radius\":5.0},{\"x\":65.0,\"y\":37.0,\"rowLabel\":\"b\",\"colLabel\":\"4\",\"radius\":5.0},{\"x\":79.0,\"y\":37.0,\"rowLabel\":\"b\",\"colLabel\":\"5\",\"radius\":5.0},{\"x\":93.0,\"y\":37.0,\"rowLabel\":\"b\",\"colLabel\":\"6\",\"radius\":5.0},{\"x\":23.0,\"y\":51.0,\"rowLabel\":\"c\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":51.0,\"rowLabel\":\"c\",\"colLabel\":\"2\",\"radius\":5.0},{\"x\":51.0,\"y\":51.0,\"rowLabel\":\"c\",\"colLabel\":\"3\",\"radius\":5.0},{\"x\":65.0,\"y\":51.0,\"rowLabel\":\"c\",\"colLabel\":\"4\",\"radius\":5.0},{\"x\":79.0,\"y\":51.0,\"rowLabel\":\"c\",\"colLabel\":\"5\",\"radius\":5.0},{\"x\":93.0,\"y\":51.0,\"rowLabel\":\"c\",\"colLabel\":\"6\",\"radius\":5.0},{\"x\":23.0,\"y\":65.0,\"rowLabel\":\"d\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":65.0,\"rowLabel\":\"d\",\"colLabel\":\"2\",\"radius\":5.0},{\"x\":51.0,\"y\":65.0,\"rowLabel\":\"d\",\"colLabel\":\"3\",\"radius\":5.0},{\"x\":65.0,\"y\":65.0,\"rowLabel\":\"d\",\"colLabel\":\"4\",\"radius\":5.0},{\"x\":79.0,\"y\":65.0,\"rowLabel\":\"d\",\"colLabel\":\"5\",\"radius\":5.0},{\"x\":93.0,\"y\":65.0,\"rowLabel\":\"d\",\"colLabel\":\"6\",\"radius\":5.0},{\"x\":23.0,\"y\":79.0,\"rowLabel\":\"e\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":79.0,\"rowLabel\":\"e\",\"colLabel\":\"2\",\"radius\":5.0},{\"x\":51.0,\"y\":79.0,\"rowLabel\":\"e\",\"colLabel\":\"3\",\"radius\":5.0},{\"x\":65.0,\"y\":79.0,\"rowLabel\":\"e\",\"colLabel\":\"4\",\"radius\":5.0},{\"x\":79.0,\"y\":79.0,\"rowLabel\":\"e\",\"colLabel\":\"5\",\"radius\":5.0},{\"x\":93.0,\"y\":79.0,\"rowLabel\":\"e\",\"colLabel\":\"6\",\"radius\":5.0},{\"x\":23.0,\"y\":93.0,\"rowLabel\":\"f\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":93.0,\"rowLabel\":\"f\",\"colLabel\":\"2\",\"radius\":5.0},{\"x\":51.0,\"y\":93.0,\"rowLabel\":\"f\",\"colLabel\":\"3\",\"radius\":5.0},{\"x\":65.0,\"y\":93.0,\"rowLabel\":\"f\",\"colLabel\":\"4\",\"radius\":5.0},{\"x\":79.0,\"y\":93.0,\"rowLabel\":\"f\",\"colLabel\":\"5\",\"radius\":5.0},{\"x\":93.0,\"y\":93.0,\"rowLabel\":\"f\",\"colLabel\":\"6\",\"radius\":5.0}],\"seatLabels\":[{\"x\":23.0,\"y\":9.0,\"size\":5.0,\"text\":\"1\"},{\"x\":37.0,\"y\":9.0,\"size\":5.0,\"text\":\"2\"},{\"x\":51.0,\"y\":9.0,\"size\":5.0,\"text\":\"3\"},{\"x\":65.0,\"y\":9.0,\"size\":5.0,\"text\":\"4\"},{\"x\":79.0,\"y\":9.0,\"size\":5.0,\"text\":\"5\"},{\"x\":93.0,\"y\":9.0,\"size\":5.0,\"text\":\"6\"},{\"x\":9.0,\"y\":23.0,\"size\":5.0,\"text\":\"a\"},{\"x\":9.0,\"y\":37.0,\"size\":5.0,\"text\":\"b\"},{\"x\":9.0,\"y\":51.0,\"size\":5.0,\"text\":\"c\"},{\"x\":9.0,\"y\":65.0,\"size\":5.0,\"text\":\"d\"},{\"x\":9.0,\"y\":79.0,\"size\":5.0,\"text\":\"e\"},{\"x\":9.0,\"y\":93.0,\"size\":5.0,\"text\":\"f\"}]},{\"x\":284.0,\"y\":111.0,\"width\":53.0,\"height\":219.0,\"price\":11.0,\"name\":\"Bereich-2\",\"seats\":[{\"x\":23.0,\"y\":23.0,\"rowLabel\":\"a\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":23.0,\"rowLabel\":\"a\",\"colLabel\":\"2\",\"radius\":5.0},{\"x\":23.0,\"y\":37.0,\"rowLabel\":\"b\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":37.0,\"rowLabel\":\"b\",\"colLabel\":\"2\",\"radius\":5.0},{\"x\":23.0,\"y\":51.0,\"rowLabel\":\"c\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":51.0,\"rowLabel\":\"c\",\"colLabel\":\"2\",\"radius\":5.0},{\"x\":23.0,\"y\":65.0,\"rowLabel\":\"d\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":65.0,\"rowLabel\":\"d\",\"colLabel\":\"2\",\"radius\":5.0},{\"x\":23.0,\"y\":79.0,\"rowLabel\":\"e\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":79.0,\"rowLabel\":\"e\",\"colLabel\":\"2\",\"radius\":5.0},{\"x\":23.0,\"y\":93.0,\"rowLabel\":\"f\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":93.0,\"rowLabel\":\"f\",\"colLabel\":\"2\",\"radius\":5.0},{\"x\":23.0,\"y\":107.0,\"rowLabel\":\"g\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":107.0,\"rowLabel\":\"g\",\"colLabel\":\"2\",\"radius\":5.0},{\"x\":23.0,\"y\":121.0,\"rowLabel\":\"h\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":121.0,\"rowLabel\":\"h\",\"colLabel\":\"2\",\"radius\":5.0},{\"x\":23.0,\"y\":135.0,\"rowLabel\":\"i\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":135.0,\"rowLabel\":\"i\",\"colLabel\":\"2\",\"radius\":5.0},{\"x\":23.0,\"y\":149.0,\"rowLabel\":\"j\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":149.0,\"rowLabel\":\"j\",\"colLabel\":\"2\",\"radius\":5.0},{\"x\":23.0,\"y\":163.0,\"rowLabel\":\"k\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":163.0,\"rowLabel\":\"k\",\"colLabel\":\"2\",\"radius\":5.0},{\"x\":23.0,\"y\":177.0,\"rowLabel\":\"l\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":177.0,\"rowLabel\":\"l\",\"colLabel\":\"2\",\"radius\":5.0},{\"x\":23.0,\"y\":191.0,\"rowLabel\":\"m\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":191.0,\"rowLabel\":\"m\",\"colLabel\":\"2\",\"radius\":5.0},{\"x\":23.0,\"y\":205.0,\"rowLabel\":\"n\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":205.0,\"rowLabel\":\"n\",\"colLabel\":\"2\",\"radius\":5.0}],\"seatLabels\":[{\"x\":23.0,\"y\":9.0,\"size\":5.0,\"text\":\"1\"},{\"x\":37.0,\"y\":9.0,\"size\":5.0,\"text\":\"2\"},{\"x\":9.0,\"y\":23.0,\"size\":5.0,\"text\":\"a\"},{\"x\":9.0,\"y\":37.0,\"size\":5.0,\"text\":\"b\"},{\"x\":9.0,\"y\":51.0,\"size\":5.0,\"text\":\"c\"},{\"x\":9.0,\"y\":65.0,\"size\":5.0,\"text\":\"d\"},{\"x\":9.0,\"y\":79.0,\"size\":5.0,\"text\":\"e\"},{\"x\":9.0,\"y\":93.0,\"size\":5.0,\"text\":\"f\"},{\"x\":9.0,\"y\":107.0,\"size\":5.0,\"text\":\"g\"},{\"x\":9.0,\"y\":121.0,\"size\":5.0,\"text\":\"h\"},{\"x\":9.0,\"y\":135.0,\"size\":5.0,\"text\":\"i\"},{\"x\":9.0,\"y\":149.0,\"size\":5.0,\"text\":\"j\"},{\"x\":9.0,\"y\":163.0,\"size\":5.0,\"text\":\"k\"},{\"x\":9.0,\"y\":177.0,\"size\":5.0,\"text\":\"l\"},{\"x\":9.0,\"y\":191.0,\"size\":5.0,\"text\":\"m\"},{\"x\":9.0,\"y\":205.0,\"size\":5.0,\"text\":\"n\"}]},{\"x\":129.0,\"y\":107.0,\"width\":51.0,\"height\":225.0,\"price\":11.0,\"name\":\"Bereich-3\",\"seats\":[{\"x\":23.0,\"y\":23.0,\"rowLabel\":\"a\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":23.0,\"rowLabel\":\"a\",\"colLabel\":\"2\",\"radius\":5.0},{\"x\":23.0,\"y\":37.0,\"rowLabel\":\"b\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":37.0,\"rowLabel\":\"b\",\"colLabel\":\"2\",\"radius\":5.0},{\"x\":23.0,\"y\":51.0,\"rowLabel\":\"c\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":51.0,\"rowLabel\":\"c\",\"colLabel\":\"2\",\"radius\":5.0},{\"x\":23.0,\"y\":65.0,\"rowLabel\":\"d\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":65.0,\"rowLabel\":\"d\",\"colLabel\":\"2\",\"radius\":5.0},{\"x\":23.0,\"y\":79.0,\"rowLabel\":\"e\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":79.0,\"rowLabel\":\"e\",\"colLabel\":\"2\",\"radius\":5.0},{\"x\":23.0,\"y\":93.0,\"rowLabel\":\"f\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":93.0,\"rowLabel\":\"f\",\"colLabel\":\"2\",\"radius\":5.0},{\"x\":23.0,\"y\":107.0,\"rowLabel\":\"g\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":107.0,\"rowLabel\":\"g\",\"colLabel\":\"2\",\"radius\":5.0},{\"x\":23.0,\"y\":121.0,\"rowLabel\":\"h\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":121.0,\"rowLabel\":\"h\",\"colLabel\":\"2\",\"radius\":5.0},{\"x\":23.0,\"y\":135.0,\"rowLabel\":\"i\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":135.0,\"rowLabel\":\"i\",\"colLabel\":\"2\",\"radius\":5.0},{\"x\":23.0,\"y\":149.0,\"rowLabel\":\"j\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":149.0,\"rowLabel\":\"j\",\"colLabel\":\"2\",\"radius\":5.0},{\"x\":23.0,\"y\":163.0,\"rowLabel\":\"k\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":163.0,\"rowLabel\":\"k\",\"colLabel\":\"2\",\"radius\":5.0},{\"x\":23.0,\"y\":177.0,\"rowLabel\":\"l\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":177.0,\"rowLabel\":\"l\",\"colLabel\":\"2\",\"radius\":5.0},{\"x\":23.0,\"y\":191.0,\"rowLabel\":\"m\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":191.0,\"rowLabel\":\"m\",\"colLabel\":\"2\",\"radius\":5.0},{\"x\":23.0,\"y\":205.0,\"rowLabel\":\"n\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":205.0,\"rowLabel\":\"n\",\"colLabel\":\"2\",\"radius\":5.0},{\"x\":23.0,\"y\":219.0,\"rowLabel\":\"o\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":219.0,\"rowLabel\":\"o\",\"colLabel\":\"2\",\"radius\":5.0}],\"seatLabels\":[{\"x\":23.0,\"y\":9.0,\"size\":5.0,\"text\":\"1\"},{\"x\":37.0,\"y\":9.0,\"size\":5.0,\"text\":\"2\"},{\"x\":9.0,\"y\":23.0,\"size\":5.0,\"text\":\"a\"},{\"x\":9.0,\"y\":37.0,\"size\":5.0,\"text\":\"b\"},{\"x\":9.0,\"y\":51.0,\"size\":5.0,\"text\":\"c\"},{\"x\":9.0,\"y\":65.0,\"size\":5.0,\"text\":\"d\"},{\"x\":9.0,\"y\":79.0,\"size\":5.0,\"text\":\"e\"},{\"x\":9.0,\"y\":93.0,\"size\":5.0,\"text\":\"f\"},{\"x\":9.0,\"y\":107.0,\"size\":5.0,\"text\":\"g\"},{\"x\":9.0,\"y\":121.0,\"size\":5.0,\"text\":\"h\"},{\"x\":9.0,\"y\":135.0,\"size\":5.0,\"text\":\"i\"},{\"x\":9.0,\"y\":149.0,\"size\":5.0,\"text\":\"j\"},{\"x\":9.0,\"y\":163.0,\"size\":5.0,\"text\":\"k\"},{\"x\":9.0,\"y\":177.0,\"size\":5.0,\"text\":\"l\"},{\"x\":9.0,\"y\":191.0,\"size\":5.0,\"text\":\"m\"},{\"x\":9.0,\"y\":205.0,\"size\":5.0,\"text\":\"n\"},{\"x\":9.0,\"y\":219.0,\"size\":5.0,\"text\":\"o\"}]}],\"standingAreas\":[{\"x\":182.0,\"y\":112.0,\"width\":100.0,\"height\":109.0,\"name\":\"Bereich-0\",\"maxPeople\":10,\"price\":10.0}]}";

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private StandingAreaRepository standingAreaRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private PerformanceRepository performanceRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Test
    void testOccupation() {
        Location location = getLocation();
        locationRepository.save(location);
        Performance performance = new Performance();
        Event event = new Event();
        event.setDescription("Test Event");
        event.setTitle("Test Name");
        event.setCategory(CategoryEnum.FESTIVAL);
        event.setDuration(200L);
        performance.setLocation(location);
        performance.setDateTime(OffsetDateTime.now());
        performance.setEvent(event);
        eventRepository.save(event);
        performanceRepository.save(performance);

        SeatGroupArea sga = location.getSeatmap().getSeatGroupAreas().stream().findFirst().get();
        StandingArea sa = location.getSeatmap().getStandingAreas().stream().findFirst().get();
        Set<Seat> freeSeats = seatRepository.findFreeForPerformance(sga,performance);
        Set<Seat> soldSeats = seatRepository.findSoldForPerformance(sga,performance);
        Set<Seat> reservedSeats = seatRepository.findReservedForPerformance(sga,performance);
        Integer standingReserved = standingAreaRepository.sumReserved(sa,performance);
        Integer standingSold = standingAreaRepository.sumSold(sa,performance);
        assertThat(freeSeats.size()).isEqualTo(36);
        assertThat(soldSeats.size()).isEqualTo(0);
        assertThat(reservedSeats.size()).isEqualTo(0);
        assertThat(standingSold).isNull();
        assertThat(standingReserved).isNull();

        // Perform a ticket purchase
        Booking b = new Booking();
        b.setPerformance(performance);
        b.setReservation(false);
        b.setDate(LocalDate.now());
        b.setCanceled(false);
        List<Ticket> tickets = new ArrayList<>(2);
        SeatedTicket ticket1 = new SeatedTicket();
        ticket1.setBooking(b);
        ticket1.setPrice(BigDecimal.valueOf(3.50));
        ticket1.setSeat(sga.getSeats().stream().findFirst().get());
        tickets.add(ticket1);
        StandingTicket ticket2 = new StandingTicket();
        ticket2.setBooking(b);
        ticket2.setAmount(3L);
        ticket2.setPrice(BigDecimal.valueOf(3.50));
        ticket2.setStandingArea(sa);
        tickets.add(ticket2);
        b.setTickets(tickets);
        bookingRepository.save(b);
        freeSeats = seatRepository.findFreeForPerformance(sga,performance);
        soldSeats = seatRepository.findSoldForPerformance(sga,performance);
        reservedSeats = seatRepository.findReservedForPerformance(sga,performance);
        standingReserved = standingAreaRepository.sumReserved(sa,performance);
        standingSold = standingAreaRepository.sumSold(sa,performance);
        assertThat(freeSeats.size()).isEqualTo(35);
        assertThat(soldSeats.size()).isEqualTo(1);
        assertThat(reservedSeats.size()).isEqualTo(0);
        assertThat(standingSold).isEqualTo(3);
        assertThat(standingReserved).isNull();

        // Perform a ticket reservation
        b = new Booking();
        b.setReservation(true);
        b.setCanceled(false);
        b.setPerformance(performance);
        b.setDate(LocalDate.now());
        tickets = new ArrayList<>(2);
        ticket1 = new SeatedTicket();
        ticket1.setBooking(b);
        ticket1.setPrice(BigDecimal.valueOf(3.50));
        ticket1.setSeat(sga.getSeats().stream().skip(2).findFirst().get());
        tickets.add(ticket1);
        ticket2 = new StandingTicket();
        ticket2.setBooking(b);
        ticket2.setAmount(4L);
        ticket2.setPrice(BigDecimal.valueOf(3.50));
        ticket2.setStandingArea(sa);
        tickets.add(ticket2);
        b.setTickets(tickets);
        bookingRepository.save(b);

        freeSeats = seatRepository.findFreeForPerformance(sga,performance);
        soldSeats = seatRepository.findSoldForPerformance(sga,performance);
        reservedSeats = seatRepository.findReservedForPerformance(sga,performance);
        standingReserved = standingAreaRepository.sumReserved(sa,performance);
        standingSold = standingAreaRepository.sumSold(sa,performance);
        assertThat(freeSeats.size()).isEqualTo(34);
        assertThat(soldSeats.size()).isEqualTo(1);
        assertThat(reservedSeats.size()).isEqualTo(1);
        assertThat(standingSold).isEqualTo(3);
        assertThat(standingReserved).isEqualTo(4);
    }

    private Location getLocation() {
        Address address = new Address();
        address.setCountry("Austria");
        address.setCity("Wien");
        address.setPostalcode("1030");
        address.setHousenr("80");
        address.setStreet("Baumgasse");
        Location location = new Location();
        location.setDescription("Arena");
        location.setAddress(address);
        Seatmap sm = DomainTestObjectFactory.getSeatmap();
        location.setSeatmap(sm);
        return location;
    }
}
