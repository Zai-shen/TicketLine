package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.controller.mapper.SeatMapper;
import at.ac.tuwien.sepm.groupphase.backend.controller.mapper.SeatMapperImpl;
import at.ac.tuwien.sepm.groupphase.backend.dto.SeatOccupationDTO;
import at.ac.tuwien.sepm.groupphase.backend.dto.SeatgroupOccupationDTO;
import at.ac.tuwien.sepm.groupphase.backend.dto.StandingAreaDTO;
import at.ac.tuwien.sepm.groupphase.backend.entity.Performance;
import at.ac.tuwien.sepm.groupphase.backend.entity.Seat;
import at.ac.tuwien.sepm.groupphase.backend.entity.SeatGroupArea;
import at.ac.tuwien.sepm.groupphase.backend.entity.StandingArea;
import at.ac.tuwien.sepm.groupphase.backend.exception.BusinessValidationException;
import at.ac.tuwien.sepm.groupphase.backend.repository.PerformanceRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.SeatRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.StandingAreaRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.impl.PerformanceServiceImpl;
import at.ac.tuwien.sepm.groupphase.backend.util.DomainTestObjectFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PerformanceServiceTest {
    @Mock
    private PerformanceRepository performanceRepository;
    @Mock
    private EventService eventService;
    @Mock
    private SeatRepository seatRepository;
    @Mock
    private LocationService locationService;
    @Spy
    private SeatMapper seatMapper = new SeatMapperImpl();
    @Mock
    private StandingAreaRepository standingAreaRepository;

    @InjectMocks
    private PerformanceServiceImpl performanceService;

    @Test
    public void testCreateEventValidInput() {
        final Performance performance = DomainTestObjectFactory.getPerformance();
        performance.setId(1L);
        performance.getEvent().setId(1L);
        performance.getLocation().setId(1L);
        when(performanceRepository.saveAndFlush(any())).thenReturn(performance);
        when(eventService.getEvent(1L)).thenReturn(Optional.of(performance.getEvent()));
        performanceService.createPerformance(performance.getEvent().getId(), performance);
        verify(performanceRepository, times(1)).saveAndFlush(performance);
    }

    @Test
    public void testCreateExceptionOnInvalidObject() {
        final Performance performance = DomainTestObjectFactory.getPerformance();
        performance.setEvent(null);
        assertThatThrownBy(() -> performanceService.createPerformance(null, performance)).isExactlyInstanceOf(
            BusinessValidationException.class);
    }

    @Test
    public void testGetSeatmapValidPerformance() {
        final Performance performance = DomainTestObjectFactory.getPerformance();
        performance.setId(1L);
        performance.getEvent().setId(1L);
        performance.getLocation().setId(1L);
        when(performanceRepository.saveAndFlush(any())).thenReturn(performance);
        when(eventService.getEvent(1L)).thenReturn(Optional.of(performance.getEvent()));
        when(performanceRepository.findById(1L)).thenReturn(Optional.of(performance));
        when(locationService.getSeatMapForLocation(any())).thenReturn(DomainTestObjectFactory.getSeatmap());
        performanceService.createPerformance(performance.getEvent().getId(), performance);
        performanceService.getSeatmap(1L);
        verify(seatRepository,times(3)).findFreeForPerformance(any(),any());
        verify(seatRepository,times(3)).findReservedForPerformance(any(),any());
        verify(standingAreaRepository,times(1)).sumSold(any(),any());
        verify(standingAreaRepository,times(1)).sumReserved(any(),any());
    }
}
