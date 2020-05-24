package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.Performance;
import at.ac.tuwien.sepm.groupphase.backend.exception.BusinessValidationException;
import at.ac.tuwien.sepm.groupphase.backend.repository.EventRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.PerformanceRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.impl.EventServiceImpl;
import at.ac.tuwien.sepm.groupphase.backend.service.impl.PerformanceServiceImpl;
import at.ac.tuwien.sepm.groupphase.backend.util.DomainTestObjectFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
}
