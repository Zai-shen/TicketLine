package at.ac.tuwien.sepm.groupphase.backend.controller;

import at.ac.tuwien.sepm.groupphase.backend.api.EventApi;
import at.ac.tuwien.sepm.groupphase.backend.controller.mapper.EventMapper;
import at.ac.tuwien.sepm.groupphase.backend.dto.EventCategory;
import at.ac.tuwien.sepm.groupphase.backend.dto.EventDTO;
import at.ac.tuwien.sepm.groupphase.backend.dto.PerformanceDTO;
import at.ac.tuwien.sepm.groupphase.backend.controller.mapper.PerformanceMapper;
import at.ac.tuwien.sepm.groupphase.backend.security.AuthorizationRole;
import at.ac.tuwien.sepm.groupphase.backend.service.EventService;
import at.ac.tuwien.sepm.groupphase.backend.service.PerformanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Optional;

@RestController
public class EventController implements EventApi {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final PerformanceService performanceService;
    private final PerformanceMapper performanceMapper;
    private final EventMapper eventMapper;
    private final EventService eventService;

    private static final int PAGE_SIZE = 10;

    public EventController(PerformanceService performanceService, PerformanceMapper performanceMapper, EventService eventService, EventMapper eventMapper) {
        this.performanceService = performanceService;
        this.performanceMapper = performanceMapper;
        this.eventMapper = eventMapper;
        this.eventService = eventService;
    }

    @Override
    public ResponseEntity<List<PerformanceDTO>> getTopTenEvents(@Valid Optional<Integer> page,
        @Valid Optional<EventCategory> category) {
        LOGGER.info("Show top events");
        return ResponseEntity.ok(performanceMapper.toDto(
            performanceService.getAllPerformances(PageRequest.of(page.orElse(0), PAGE_SIZE)).getContent()));
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
}
