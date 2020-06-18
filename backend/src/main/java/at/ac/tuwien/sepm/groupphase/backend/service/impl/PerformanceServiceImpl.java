package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.controller.mapper.SeatMapper;
import at.ac.tuwien.sepm.groupphase.backend.dto.SeatOccupationDTO;
import at.ac.tuwien.sepm.groupphase.backend.dto.SeatgroupOccupationDTO;
import at.ac.tuwien.sepm.groupphase.backend.dto.SeatmapOccupationDTO;
import at.ac.tuwien.sepm.groupphase.backend.dto.StandingAreaOccupationDTO;
import at.ac.tuwien.sepm.groupphase.backend.entity.*;
import at.ac.tuwien.sepm.groupphase.backend.exception.BusinessValidationException;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.PerformanceRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.SearchPerformanceSpecification;
import at.ac.tuwien.sepm.groupphase.backend.repository.SeatRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.StandingAreaRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.EventService;
import at.ac.tuwien.sepm.groupphase.backend.service.LocationService;
import at.ac.tuwien.sepm.groupphase.backend.service.PerformanceService;
import at.ac.tuwien.sepm.groupphase.backend.service.validator.NewPerformanceValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.invoke.MethodHandles;
import java.util.LinkedList;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PerformanceServiceImpl implements PerformanceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final PerformanceRepository performanceRepository;
    private final EventService eventService;
    private final LocationService locationService;
    private final SeatRepository seatRepository;
    private final StandingAreaRepository standingAreaRepository;
    private final SeatMapper seatMapper;

    public PerformanceServiceImpl(PerformanceRepository performanceRepository, EventService eventService,
        LocationService locationService, SeatRepository seatRepository, StandingAreaRepository standingAreaRepository,
        SeatMapper seatMapper) {
        this.performanceRepository = performanceRepository;
        this.seatRepository = seatRepository;
        this.eventService = eventService;
        this.locationService = locationService;
        this.standingAreaRepository = standingAreaRepository;
        this.seatMapper = seatMapper;
    }

    @Override
    public Page<Performance> getAllPerformances(Pageable pageable) {
        return performanceRepository.findAll(pageable);
    }

    @Override
    public Long createPerformance(Long eventId, Performance performance) throws BusinessValidationException {
        if(eventId != null) {
            Event event = eventService.getEvent(eventId).orElseThrow(() ->
                new NotFoundException(String.format("Event mit id=%d existiert nicht", eventId)));
            performance.setEvent(event);
        }
        new NewPerformanceValidator().build(performance).validate();
        return performanceRepository.saveAndFlush(performance).getId();
    }

    @Override
    @Transactional(readOnly = true)
    public SeatmapOccupationDTO getSeatmap(Long performanceId) {
        Performance performance = performanceRepository.findById(performanceId).orElseThrow(NotFoundException::new);
        Seatmap sm = locationService.getSeatMapForLocation(performance.getLocation());
        SeatmapOccupationDTO sdto = new SeatmapOccupationDTO();
        sdto.setStandingAreas(sm.getStandingAreas().stream().map(x -> {
            StandingAreaOccupationDTO sao = seatMapper.fromEntity(x);
            Integer reserved = standingAreaRepository.sumReserved(x, performance);
            Integer sold = standingAreaRepository.sumSold(x, performance);
            sao.setReserved(reserved != null ? reserved : 0);
            sao.setSold(sold != null ? sold : 0);
            return sao;
        }).collect(Collectors.toList()));
        sdto.setSeatGroupAreas(sm.getSeatGroupAreas().stream().map(x -> {
            Set<Seat> reserved = seatRepository.findReservedForPerformance(x, performance);
            Set<Seat> sold = seatRepository.findSoldForPerformance(x, performance);
            Set<Seat> free = seatRepository.findFreeForPerformance(x, performance);
            SeatgroupOccupationDTO sgo = seatMapper.fromEntity(x);
            sgo.setSeats(new LinkedList<>());
            for (Seat s : reserved) {
                SeatOccupationDTO so = seatMapper.fromEntity(s);
                so.setReserved(true);
                sgo.addSeatsItem(so);
            }
            for (Seat s : sold) {
                SeatOccupationDTO so = seatMapper.fromEntity(s);
                so.setSold(true);
                sgo.addSeatsItem(so);
            }
            for (Seat s : free) {
                SeatOccupationDTO so = seatMapper.fromEntity(s);
                sgo.addSeatsItem(so);
            }
            return sgo;
        }).collect(Collectors.toList()));
        return sdto;
    }
    @Override
    public Page<Performance> searchPerformances(SearchPerformance performance, Pageable pageable) {
        LOGGER.debug("search performances");

        Specification<Performance> specification = new SearchPerformanceSpecification(performance);
        return performanceRepository.findAll(specification, pageable);
    }
}
