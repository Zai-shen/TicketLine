package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.dto.*;
import at.ac.tuwien.sepm.groupphase.backend.entity.*;
import at.ac.tuwien.sepm.groupphase.backend.exception.BusinessValidationException;
import at.ac.tuwien.sepm.groupphase.backend.repository.EventRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.LocationRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.impl.EventServiceImpl;
import at.ac.tuwien.sepm.groupphase.backend.util.DomainTestObjectFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EventServiceTest {
    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventServiceImpl eventService;

    @Test
    public void testCreateEventValidInput() {
        final Event event = DomainTestObjectFactory.getEvent();
        when(eventRepository.saveAndFlush(any())).thenReturn(event);
        eventService.createEvent(event);
        verify(eventRepository, times(1)).saveAndFlush(event);
    }

    @Test
    public void testCreateExceptionOnInvalidObject() {
        final Event event = DomainTestObjectFactory.getEvent();
        event.setCategory(null);
        assertThatThrownBy(() -> eventService.createEvent(event)).isExactlyInstanceOf(
            BusinessValidationException.class);
    }
}
