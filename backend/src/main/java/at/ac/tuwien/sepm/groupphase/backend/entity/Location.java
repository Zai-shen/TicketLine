package at.ac.tuwien.sepm.groupphase.backend.entity;

import com.google.common.base.Objects;

import javax.persistence.*;
import java.util.List;

@Entity
public class Location {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable=false)
    private String description;

    @OneToMany
    private List<Seatgroup> seatGroups;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Seatgroup> getSeatGroups() {
        return seatGroups;
    }

    public void setSeatGroups(List<Seatgroup> seatGroups) {
        this.seatGroups = seatGroups;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Location location = (Location) o;
        return java.util.Objects.equals(id, location.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
