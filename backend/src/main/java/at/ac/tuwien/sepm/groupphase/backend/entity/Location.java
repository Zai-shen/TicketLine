package at.ac.tuwien.sepm.groupphase.backend.entity;

import javax.persistence.*;
import java.util.Objects;
import java.util.List;

@Entity
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false,length = 1024)
    private String description;

    @OneToMany
    private List<Seatgroup> seatGroups;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return Objects.equals(id, location.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Location{" +
            "id=" + id +
            ", description='" + description + '\'' +
            ", address=" + address.toString() +
            '}';
    }
}
