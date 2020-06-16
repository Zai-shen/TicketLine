package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.Performance;
import at.ac.tuwien.sepm.groupphase.backend.entity.Seat;
import at.ac.tuwien.sepm.groupphase.backend.entity.SeatGroupArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    Seat findBySeatGroupAreaAndXAndY(SeatGroupArea seatGroupArea,Double x, Double y);
    List<Seat> findBySeatGroupArea(SeatGroupArea seatGroupArea);
    @Query("SELECT s FROM Seat s JOIN SeatedTicket t ON t.seat=s JOIN Booking b ON t.booking=b WHERE b.performance = ?2 AND s.seatGroupArea = ?1 AND b.isReservation = FALSE")
    Set<Seat> findSoldForPerformance(SeatGroupArea sga, Performance p);
    @Query("SELECT s FROM Seat s JOIN SeatedTicket t ON t.seat=s JOIN Booking b ON t.booking=b WHERE b.performance = ?2 AND s.seatGroupArea = ?1 AND b.isReservation = TRUE")
    Set<Seat> findReservedForPerformance(SeatGroupArea sga, Performance p);
    @Query("SELECT s FROM Seat s WHERE s.seatGroupArea = ?1 AND  s NOT IN (SELECT s FROM Seat s JOIN SeatedTicket t ON t.seat=s JOIN Booking b ON t.booking=b WHERE b.performance = ?2 AND s.seatGroupArea = ?1)")
    Set<Seat> findFreeForPerformance(SeatGroupArea sga, Performance p);
}
