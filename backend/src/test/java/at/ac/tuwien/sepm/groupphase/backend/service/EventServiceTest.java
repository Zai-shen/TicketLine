package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.exception.BusinessValidationException;
import at.ac.tuwien.sepm.groupphase.backend.repository.EventRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.impl.EventServiceImpl;
import at.ac.tuwien.sepm.groupphase.backend.util.DomainTestObjectFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import static org.assertj.core.api.Assertions.assertThat;
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

    @Test
    public void testSearchEvents() {
        final Event searchEvent = DomainTestObjectFactory.getEvent();
        final PageRequest pageRequest = PageRequest.of(0, 20);
        final Page<Event> returnPage = Page.empty();
        when(eventRepository.findAll(any(), eq(pageRequest))).thenReturn(returnPage);

        Page<Event> result = eventService.searchEvents(searchEvent, pageRequest);

        assertThat(result).isEqualTo(returnPage);
        var captor = ArgumentCaptor.forClass(Example.class);
        verify(eventRepository, times(1)).findAll(captor.capture(), eq(pageRequest));
        Example<Event> example = captor.getValue();
        assertThat(example.getProbe()).isEqualTo(searchEvent);
    }
}
