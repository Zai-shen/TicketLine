package at.ac.tuwien.sepm.groupphase.backend.controller;

import at.ac.tuwien.sepm.groupphase.backend.api.EventApi;
import at.ac.tuwien.sepm.groupphase.backend.controller.mapper.EventMapper;
import at.ac.tuwien.sepm.groupphase.backend.controller.mapper.TicketMapper;
import at.ac.tuwien.sepm.groupphase.backend.dto.*;
import at.ac.tuwien.sepm.groupphase.backend.controller.mapper.PerformanceMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.User;
import at.ac.tuwien.sepm.groupphase.backend.service.BookingService;
import at.ac.tuwien.sepm.groupphase.backend.service.EventService;
import at.ac.tuwien.sepm.groupphase.backend.security.AuthorizationRole;
import at.ac.tuwien.sepm.groupphase.backend.service.PerformanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Optional;

@RestController
public class EventController implements EventApi {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final PerformanceService performanceService;
    private final EventService eventService;
    private final PerformanceMapper performanceMapper;
    private final EventMapper eventMapper;
    private final BookingService bookingService;
    private final TicketMapper ticketMapper;

    private static final int PAGE_SIZE = 10;

    public EventController(PerformanceService performanceService, EventService eventService,
        PerformanceMapper performanceMapper, EventMapper eventMapper, BookingService bookingService,
        TicketMapper ticketMapper) {
        this.performanceService = performanceService;
        this.eventService = eventService;
        this.performanceMapper = performanceMapper;
        this.eventMapper = eventMapper;
        this.bookingService = bookingService;
        this.ticketMapper = ticketMapper;
    }

    @Override
    public ResponseEntity<List<PerformanceDTO>> getTopTenEvents(@Valid Optional<Integer> page,
        @Valid Optional<EventCategory> category) {
        LOGGER.info("Show top events");
        return ResponseEntity.ok(performanceMapper.toDto(
            performanceService.getAllPerformances(PageRequest.of(page.orElse(0), PAGE_SIZE)).getContent()));
    }

    @Override
    public ResponseEntity<EventDTO> getEvent(Long eventId) {
        var event = eventService.getEvent(eventId);
        return event.map(value -> ResponseEntity.ok(eventMapper.toDto(value)))
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<List<PerformanceDTO>> getPerformances(Long eventId) {
        LOGGER.info("Get performances for event {}", eventId);
        return ResponseEntity.ok(performanceMapper.toDto(eventService.getPerformances(eventId)));
    }

    @Override
    @Secured(AuthorizationRole.ADMIN_ROLE)
    public ResponseEntity<Long> createEvent(@Valid EventDTO eventDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
            this.eventService.createEvent(eventMapper.fromDto(eventDTO)));
    }

    @Override
    @Secured(AuthorizationRole.ADMIN_ROLE)
    public ResponseEntity<Long> createPerformance(Long eventId, @Valid PerformanceDTO performanceDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
            performanceService.createPerformance(eventId, performanceMapper.fromDto(performanceDTO)));
    }

    @Override
    @Secured(AuthorizationRole.USER_ROLE)
    public ResponseEntity<Long> createTicket(Long eventId, Long performanceId, @Valid Optional<Boolean> reserve,
        @Valid BookingDTO bookingDTO) {
        LOGGER.info("Create ticket for performance {}", performanceId);
        bookingService.bookTickets(performanceId, reserve.orElse(false), ticketMapper.fromDto(bookingDTO));
        return ResponseEntity.ok(0L);
    }
}
