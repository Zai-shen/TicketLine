package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.Booking;
import at.ac.tuwien.sepm.groupphase.backend.entity.Performance;
import at.ac.tuwien.sepm.groupphase.backend.entity.Ticket;
import at.ac.tuwien.sepm.groupphase.backend.repository.BookingRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.PerformanceRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.SeatGroupRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.BookingService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {
    private final PerformanceRepository performanceRepository;
    private final BookingRepository bookingRepository;
    private final SeatGroupRepository seatGroupRepository;

    public BookingServiceImpl(PerformanceRepository performanceRepository, BookingRepository bookingRepository,
        SeatGroupRepository seatGroupRepository) {
        this.performanceRepository = performanceRepository;
        this.bookingRepository = bookingRepository;
        this.seatGroupRepository = seatGroupRepository;
    }

    @Override
    public void bookTickets(Long performanceId, boolean reserve, List<Ticket> tickets) {
        Performance performance = performanceRepository.getOne(performanceId);

        Booking booking = new Booking();
        booking.setPerformance(performance);
        booking.setReservation(reserve);



        bookingRepository.save(booking);
    }
}
