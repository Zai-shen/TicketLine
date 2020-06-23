package at.ac.tuwien.sepm.groupphase.backend.datagenerator;


import at.ac.tuwien.sepm.groupphase.backend.dto.SeatOccupationDTO;
import at.ac.tuwien.sepm.groupphase.backend.dto.SeatmapOccupationDTO;
import at.ac.tuwien.sepm.groupphase.backend.dto.StandingAreaOccupationDTO;
import at.ac.tuwien.sepm.groupphase.backend.entity.*;
import at.ac.tuwien.sepm.groupphase.backend.repository.*;
import at.ac.tuwien.sepm.groupphase.backend.service.BookingService;
import at.ac.tuwien.sepm.groupphase.backend.service.LocationService;
import at.ac.tuwien.sepm.groupphase.backend.service.PerformanceService;
import at.ac.tuwien.sepm.groupphase.backend.service.TicketService;
import com.github.javafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.lang.invoke.MethodHandles;
import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Profile("generateData")
@DependsOn({"PerformanceDataGenerator", "UserDataGenerator"})
@Component
public class SalesDataGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final UserRepository userRepository;
    private final PerformanceRepository performanceRepository;
    private BookingRepository bookingRepository;
    private PerformanceService performanceService;
    private final SeatRepository seatRepository;
    private final StandingAreaRepository standingAreaRepository;
    private Random rand = new Random();

    private static final int NUMBER_OF_SALES_TO_GENERATE = 1000;
    private static final int NUMBER_OF_RESERVATIONS_TO_GENERATE = 200;

    public SalesDataGenerator(UserRepository userRepository,
        PerformanceRepository performanceRepository,
        BookingRepository bookingRepository,
        PerformanceService performanceService,
        SeatRepository seatRepository,
        StandingAreaRepository standingAreaRepository) {
        this.userRepository = userRepository;
        this.performanceRepository = performanceRepository;
        this.bookingRepository = bookingRepository;
        this.performanceService = performanceService;
        this.seatRepository = seatRepository;
        this.standingAreaRepository = standingAreaRepository;
    }

    @PostConstruct
    private void generateSales() {
        generateBookings(NUMBER_OF_SALES_TO_GENERATE, false);
        generateBookings(NUMBER_OF_RESERVATIONS_TO_GENERATE, true);
    }

    private void generateBookings(int amount, boolean reservations) {
        if (!bookingRepository.findAll().isEmpty()) {
            LOGGER.debug("events already generated");
        }
        List<User> users = userRepository.findAll();
        List<Performance> performances = performanceRepository.findAll();
        for (int i = 0; i < amount; i++) {
            Performance bookPerformance = performances.get(rand.nextInt(performances.size()));
            SeatmapOccupationDTO seatmap = performanceService.getSeatmap(bookPerformance.getId());
            List<Ticket> tickets = new ArrayList<>();
            seatmap.getSeatGroupAreas().forEach(seatgroupOccupationDTO -> seatgroupOccupationDTO.getSeats()
                .forEach(seatOccupationDTO -> seatgroupOccupationDTO.setPrice(seatgroupOccupationDTO.getPrice())));
            List<SeatOccupationDTO> freeSeats = seatmap.getSeatGroupAreas().stream()
                .flatMap(seatGroupArea -> seatGroupArea.getSeats().stream()
                    .filter(seat -> (seat.getReserved() == null || !seat.getReserved())
                        && (seat.getSold() == null || !seat.getSold()))
                    .limit(5)).collect(Collectors.toList());
            List<StandingAreaOccupationDTO> freeStandingAreas = seatmap.getStandingAreas().stream()
                .filter(standingAreaOccupationDTO ->
                    (standingAreaOccupationDTO.getMaxPeople()
                        - standingAreaOccupationDTO.getSold()
                        - standingAreaOccupationDTO.getReserved()) > 0).collect(Collectors.toList());
            if(freeSeats.size() > 0) {
                freeSeats.forEach(seatOccupationDTO -> tickets.add(createSeatTicket(seatOccupationDTO)));
            }
            if(freeStandingAreas.size() > 0) {
                freeStandingAreas.forEach(standingAreaOccupationDTO -> tickets.add(createStandingTicket(standingAreaOccupationDTO)));
            }
            if(tickets.size() > 0) {
                bookTickets(bookPerformance, reservations, tickets, users.get(rand.nextInt(users.size())));
            } else {
                i--;
            }
        }
    }

    SeatedTicket createSeatTicket(SeatOccupationDTO seatOccupationDTO) {
        Seat seat = seatRepository.findById(seatOccupationDTO.getId()).get();
        SeatedTicket ticket = new SeatedTicket();
        ticket.setSeat(seat);
        ticket.setPrice(new BigDecimal(seat.getSeatGroupArea().getPrice(), MathContext.DECIMAL64));
        return ticket;
    }
    StandingTicket createStandingTicket(StandingAreaOccupationDTO standingAreaOccupationDTO) {
        StandingArea standingArea = standingAreaRepository.findById(standingAreaOccupationDTO.getId()).get();
        StandingTicket ticket = new StandingTicket();
        ticket.setStandingArea(standingArea);
        ticket.setAmount(1L);
        ticket.setPrice(new BigDecimal(standingArea.getPrice(), MathContext.DECIMAL64));
        return ticket;
    }

    void bookTickets(Performance performance, boolean reserve, List<Ticket> tickets, User user) {
        Booking booking = new Booking();
        booking.setPerformance(performance);
        booking.setReservation(reserve);
        booking.setTickets(tickets);
        booking.setDate(LocalDate.now());
        booking.setCanceled(false);
        for (Ticket t : tickets) {
            t.setUuid(UUID.randomUUID());
            t.setBooking(booking);
        }
        booking.setUser(user);
        bookingRepository.saveAndFlush(booking);
    }
}
