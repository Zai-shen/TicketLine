package at.ac.tuwien.sepm.groupphase.backend.controller;

import at.ac.tuwien.sepm.groupphase.backend.api.EventApi;
import at.ac.tuwien.sepm.groupphase.backend.dto.CreateTicket;
import at.ac.tuwien.sepm.groupphase.backend.dto.EventCategory;
import at.ac.tuwien.sepm.groupphase.backend.dto.PerformanceDTO;
import at.ac.tuwien.sepm.groupphase.backend.controller.mapper.PerformanceMapper;
import at.ac.tuwien.sepm.groupphase.backend.service.PerformanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
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

    private static final int PAGE_SIZE = 10;

    public EventController(PerformanceService performanceService, PerformanceMapper performanceMapper) {
        this.performanceService = performanceService;
        this.performanceMapper = performanceMapper;
    }

    @Override
    public ResponseEntity<List<PerformanceDTO>> getTopTenEvents(@Valid Optional<Integer> page,
        @Valid Optional<EventCategory> category) {
        LOGGER.info("Show top events");
        return ResponseEntity.ok(performanceMapper.toDto(
            performanceService.getAllPerformances(PageRequest.of(page.orElse(0), PAGE_SIZE)).getContent()));
    }

    @Override
    public ResponseEntity<Long> createTicket(Long eventId, Long performanceId, @Valid Optional<Boolean> reserve,
        @Valid List<CreateTicket> createTicket) {
        LOGGER.info("Creat Ticket");
        performanceService.createTicket(performanceId, reserve.orElse(false));
        return ResponseEntity.ok(0L);
    }
}
