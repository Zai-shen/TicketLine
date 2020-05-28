package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.Performance;
import at.ac.tuwien.sepm.groupphase.backend.entity.SeatGroupPerformance;
import at.ac.tuwien.sepm.groupphase.backend.entity.SeatGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatGroupRepository extends JpaRepository<SeatGroupPerformance, Long> {
    SeatGroupPerformance getBySeatGroupAndPerformance(SeatGroup seatgroup, Performance performance);
}
