package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.dto.*;
import at.ac.tuwien.sepm.groupphase.backend.entity.Booking;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.Performance;
import at.ac.tuwien.sepm.groupphase.backend.entity.Seatmap;
import at.ac.tuwien.sepm.groupphase.backend.exception.BusinessValidationException;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.PerformanceRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.BookingService;
import at.ac.tuwien.sepm.groupphase.backend.service.EventService;
import at.ac.tuwien.sepm.groupphase.backend.service.LocationService;
import at.ac.tuwien.sepm.groupphase.backend.service.PerformanceService;
import at.ac.tuwien.sepm.groupphase.backend.service.validator.NewPerformanceValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PerformanceServiceImpl implements PerformanceService {
    private final PerformanceRepository performanceRepository;
    private final BookingService bookingService;
    private final EventService eventService;
    private final LocationService locationService;

    public PerformanceServiceImpl(PerformanceRepository performanceRepository, BookingService bookingService,
        EventService eventService, LocationService locationService) {
        this.performanceRepository = performanceRepository;
        this.bookingService = bookingService;
        this.eventService = eventService;
        this.locationService = locationService;
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
        List<Booking> bookings = bookingService.getBookingsForPerformance(performance);
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
        )
            .collect(
                Collectors.toList()));
        sdto.setSeatGroupAreas(sm.getSeatGroupAreas().stream().map(x -> {
                return new SeatgroupOccupationDTO().seatLabels(x.getSeatLabels()
                    .stream()
                    .map(y -> new SeatLabelDTO().size(y.getSize()).x(y.getX()).y(y.getY()).text(y.getText()))
                    .collect(Collectors.toList()))
                    .height(x.getHeight())
                    .width(x.getWidth())
                    .name(x.getName())
                    .seats(x.getSeats().stream().map(s -> new SeatOccupationDTO().x(s.getX()).y(s.getY()).colLabel(s.getColLabel()).rowLabel(s.getRowLabel()).reserved(false).sold(false).price(x.getPrice())).collect(Collectors.toList()));
            }
        ).collect(Collectors.toList()));
        return sdto;
    }
}
