package at.ac.tuwien.sepm.groupphase.backend.entity;

import com.google.common.base.Objects;

import javax.persistence.*;

@Entity
public class Ticket {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @OneToMany
    private Seatgroup seatgroup;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Seatgroup getSeatgroup() {
        return seatgroup;
    }

    public void setSeatgroup(Seatgroup seatgroup) {
        this.seatgroup = seatgroup;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Ticket location = (Ticket) o;
        return java.util.Objects.equals(id, location.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
