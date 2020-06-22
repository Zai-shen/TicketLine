package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.CategoryEnum;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>, JpaSpecificationExecutor<Event> {
    @Query("SELECT e FROM Event e JOIN Performance p ON p.event=e LEFT JOIN Booking b ON b.performance=p LEFT JOIN Ticket t ON t.booking = b WHERE p.dateTime > ?1 AND p.dateTime < ?2 AND e.category = ?3 AND b.canceled = false AND (b is null OR b.canceled = false) GROUP BY e ORDER BY count(t) DESC")
    Page<Event> getOrderedEvents(OffsetDateTime after, OffsetDateTime before, CategoryEnum c, PageRequest pageRequest);

    @Query("SELECT e FROM Event e JOIN Performance p ON p.event=e LEFT JOIN Booking b ON b.performance=p LEFT JOIN Ticket t ON t.booking = b WHERE p.dateTime > ?1 AND p.dateTime < ?2 AND (b is null OR b.canceled = false) GROUP BY e ORDER BY count(t) DESC")
    Page<Event> getOrderedEvents(OffsetDateTime after, OffsetDateTime before, PageRequest pageRequest);

    @Query("SELECT count(t) FROM Ticket t WHERE t.booking.performance.event = ?1 AND t.booking.performance.dateTime > ?2 AND t.booking.performance.dateTime < ?3 AND t.booking.canceled = false")
    Long getTicketsSold(Event event, OffsetDateTime after, OffsetDateTime before);
}
