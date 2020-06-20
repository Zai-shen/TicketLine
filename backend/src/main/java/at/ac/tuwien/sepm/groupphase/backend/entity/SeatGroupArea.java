package at.ac.tuwien.sepm.groupphase.backend.entity;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
public class SeatGroupArea {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    private Double x;

    @Column(nullable = false)
    private Double y;

    @Column(nullable = false)
    private Double width;

    @Column(nullable = false)
    private Double height;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Double price;

    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY,mappedBy = "seatGroupArea")
    private Set<Seat> seats;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "seatgroup_id")
    private Set<SeatLabel> seatLabels;

    @ManyToOne
    private Seatmap seatmap;

    public Seatmap getSeatmap() {
        return seatmap;
    }

    public void setSeatmap(Seatmap seatmap) {
        this.seatmap = seatmap;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Seat> getSeats() {
        return seats;
    }

    public void setSeats(Set<Seat> seats) {
        this.seats = seats;
    }

    public Set<SeatLabel> getSeatLabels() {
        return seatLabels;
    }

    public void setSeatLabels(Set<SeatLabel> seatLabels) {
        this.seatLabels = seatLabels;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SeatGroupArea that = (SeatGroupArea) o;
        return Objects.equals(id, that.id) && Objects.equals(x, that.x) && Objects.equals(y, that.y) &&
            Objects.equals(width, that.width) && Objects.equals(height, that.height) &&
            Objects.equals(name, that.name) && Objects.equals(seats, that.seats) &&
            Objects.equals(seatLabels, that.seatLabels);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, x, y, width, height, name, seats, seatLabels);
    }
}
