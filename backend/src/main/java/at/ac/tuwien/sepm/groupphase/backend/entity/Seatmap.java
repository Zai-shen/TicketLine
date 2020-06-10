package at.ac.tuwien.sepm.groupphase.backend.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
public class Seatmap {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "seatmap_id")
    private Set<SeatGroupArea> seatGroupAreas;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "seatmap_id")
    private Set<StandingArea> standingAreas;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<SeatGroupArea> getSeatGroupAreas() {
        return seatGroupAreas;
    }

    public void setSeatGroupAreas(Set<SeatGroupArea> seatGroupAreas) {
        this.seatGroupAreas = seatGroupAreas;
    }

    public Set<StandingArea> getStandingAreas() {
        return standingAreas;
    }

    public void setStandingAreas(Set<StandingArea> standingAreas) {
        this.standingAreas = standingAreas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Seatmap seatmap = (Seatmap) o;
        return Objects.equals(id, seatmap.id) && Objects.equals(seatGroupAreas, seatmap.seatGroupAreas);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, seatGroupAreas);
    }
}
