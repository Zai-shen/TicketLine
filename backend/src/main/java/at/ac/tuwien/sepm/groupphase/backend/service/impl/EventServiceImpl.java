package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.*;
import at.ac.tuwien.sepm.groupphase.backend.exception.BusinessValidationException;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.BookingRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.EventRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.PerformanceRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.SearchEventSpecification;
import at.ac.tuwien.sepm.groupphase.backend.service.EventService;
import at.ac.tuwien.sepm.groupphase.backend.service.UserService;
import at.ac.tuwien.sepm.groupphase.backend.service.validator.NewEventValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.invoke.MethodHandles;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImpl implements EventService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final EventRepository eventRepository;
    private final PerformanceRepository performanceRepository;
    private final UserService userService;
    private final BookingRepository bookingRepository;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository, PerformanceRepository performanceRepository,
        UserService userService, BookingRepository bookingRepository) {
        this.eventRepository = eventRepository;
        this.performanceRepository = performanceRepository;
        this.userService = userService;
        this.bookingRepository = bookingRepository;
    }

    @Override
    public Long createEvent(Event event) throws BusinessValidationException {
        new NewEventValidator().build(event).validate();
        return this.eventRepository.saveAndFlush(event).getId();
    }

    @Override
    public Optional<Event> getEvent(long eventid) {
        return eventRepository.findById(eventid);
    }

    @Override
    public List<Performance> getPerformances(long eventid) {
        return performanceRepository.findByEvent(
            eventRepository.findById(eventid).orElseThrow(() -> new IllegalArgumentException("Event not found")));
    }

    @Override
    public Page<Event> searchEvents(Event searchEvent, PageRequest pageRequest) throws BusinessValidationException {
        LOGGER.debug("search events");
        Specification<Event> specification = new SearchEventSpecification(searchEvent);
        return eventRepository.findAll(specification, pageRequest);
    }

    @Override
    public List<Event> getTopTen(CategoryEnum category) {
        if (category == null) {
            LOGGER.debug("get top ten events");
            return eventRepository.getOrderedEvents(OffsetDateTime.now(), OffsetDateTime.now().plusMonths(1),
                PageRequest.of(0, 10)).getContent();
        } else {
            LOGGER.debug("get top ten events in category {}", category);
            return eventRepository.getOrderedEvents(OffsetDateTime.now(), OffsetDateTime.now().plusMonths(1), category,
                PageRequest.of(0, 10)).getContent();
        }
    }

    @Override
    @Transactional
    public void cancelBooking(Long bookingId) {
        User user = userService.getCurrentLoggedInUser();

        LOGGER.debug("delete booking {} for user {}", bookingId, user.getEmail());
        if (user.getBookings().stream().map(Booking::getId).noneMatch(id -> id.equals(bookingId))) {
            throw new BusinessValidationException("booking does not belong to currently logged in user");
        }

        Booking booking = bookingRepository.findById(bookingId).orElseThrow(NotFoundException::new);
        booking.setCanceled(true);
    }
}