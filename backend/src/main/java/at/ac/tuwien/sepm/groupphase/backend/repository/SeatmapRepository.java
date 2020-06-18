package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.Location;
import at.ac.tuwien.sepm.groupphase.backend.entity.SeatGroupArea;
import at.ac.tuwien.sepm.groupphase.backend.entity.Seatmap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatmapRepository extends JpaRepository<Seatmap, Long> {
    Seatmap findByLocation(Location location);
}
