package at.ac.tuwien.sepm.groupphase.backend.controller;

import at.ac.tuwien.sepm.groupphase.backend.api.EventApi;
import at.ac.tuwien.sepm.groupphase.backend.controller.mapper.EventMapper;
import at.ac.tuwien.sepm.groupphase.backend.dto.EventCategory;
import at.ac.tuwien.sepm.groupphase.backend.dto.EventDTO;
import at.ac.tuwien.sepm.groupphase.backend.dto.PerformanceDTO;
import at.ac.tuwien.sepm.groupphase.backend.controller.mapper.PerformanceMapper;
import at.ac.tuwien.sepm.groupphase.backend.service.EventService;
import at.ac.tuwien.sepm.groupphase.backend.service.PerformanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
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

    private static final int PAGE_SIZE = 10;

    public EventController(PerformanceService performanceService, EventService eventService,
        PerformanceMapper performanceMapper, EventMapper eventMapper) {
        this.performanceService = performanceService;
        this.eventService = eventService;
        this.performanceMapper = performanceMapper;
        this.eventMapper = eventMapper;
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
}
