package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.Performance;
import at.ac.tuwien.sepm.groupphase.backend.entity.StandingArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StandingAreaRepository extends JpaRepository<StandingArea, Long> {

    @Query("SELECT sum(t.amount) FROM StandingArea sa JOIN StandingTicket t ON t.standingArea = sa JOIN Booking b ON t.booking=b WHERE sa=?1 AND b.performance=?2 AND b.isReservation = FALSE")
    Integer sumSold(StandingArea sa, Performance p);
    @Query("SELECT sum(t.amount) FROM StandingArea sa JOIN StandingTicket t ON t.standingArea = sa JOIN Booking b ON t.booking=b WHERE sa=?1 AND b.performance=?2 AND b.isReservation = TRUE")
    Integer sumReserved(StandingArea sa, Performance p);
}
