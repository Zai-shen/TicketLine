package at.ac.tuwien.sepm.groupphase.backend.entity;

import com.google.common.base.Objects;

import javax.persistence.*;
import java.util.List;

@Entity
public class Booking {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    private Boolean isReservation;

    @OneToMany
    private Performance performance;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getReservation() {
        return isReservation;
    }

    public void setReservation(Boolean reservation) {
        isReservation = reservation;
    }

    public Performance getPerformance() {
        return performance;
    }

    public void setPerformance(Performance performance) {
        this.performance = performance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Booking location = (Booking) o;
        return java.util.Objects.equals(id, location.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
