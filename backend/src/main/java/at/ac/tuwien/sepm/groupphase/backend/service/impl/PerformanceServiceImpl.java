package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.controller.mapper.SeatMapper;
import at.ac.tuwien.sepm.groupphase.backend.dto.*;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.Performance;
import at.ac.tuwien.sepm.groupphase.backend.entity.Seat;
import at.ac.tuwien.sepm.groupphase.backend.entity.Seatmap;
import at.ac.tuwien.sepm.groupphase.backend.exception.BusinessValidationException;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.PerformanceRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.SeatRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.EventService;
import at.ac.tuwien.sepm.groupphase.backend.service.LocationService;
import at.ac.tuwien.sepm.groupphase.backend.service.PerformanceService;
import at.ac.tuwien.sepm.groupphase.backend.service.validator.NewPerformanceValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PerformanceServiceImpl implements PerformanceService {
    private final PerformanceRepository performanceRepository;
    private final EventService eventService;
    private final LocationService locationService;
    private final SeatRepository seatRepository;
    private final SeatMapper seatMapper;

    public PerformanceServiceImpl(PerformanceRepository performanceRepository, EventService eventService,
        LocationService locationService, SeatRepository seatRepository, SeatMapper seatMapper) {
        this.performanceRepository = performanceRepository;
        this.seatRepository = seatRepository;
        this.eventService = eventService;
        this.locationService = locationService;
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
        sdto.setStandingAreas(sm.getStandingAreas().stream().map(x -> new StandingAreaOccupationDTO()
            .x(x.getX())
            .y(x.getY())
            .height(x.getHeight())
            .width(x.getWidth())
            .name(x.getName())
            .maxPeople(x.getMaxPeople())
            .sold((int) (x.getMaxPeople()/3))
            .reserved((int) (x.getMaxPeople()/3))
            .id(x.getId())
        )
            .collect(
                Collectors.toList()));
        sdto.setSeatGroupAreas(sm.getSeatGroupAreas().stream().map(x -> {
                Set<Seat> reserved = seatRepository.findReservedForPerformance(x, performance);
                Set<Seat> sold = seatRepository.findSoldForPerformance(x, performance);
                Set<Seat> free = seatRepository.findFreeForPerformance(x, performance);
                SeatgroupOccupationDTO sgo = new SeatgroupOccupationDTO().seatLabels(seatMapper.fromEntity(x.getSeatLabels()))
                    .x(x.getX())
                    .y(x.getY())
                    .height(x.getHeight())
                    .width(x.getWidth())
                    .name(x.getName())
                    .id(x.getId());
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
            }
        ).collect(Collectors.toList()));
        return sdto;
    }
}
