package at.ac.tuwien.sepm.groupphase.backend.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Seat {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    private Double x;

    @Column(nullable = false)
    private Double y;

    @Column(nullable = false)
    private String rowLabel;

    @Column(nullable = false)
    private String colLabel;

    @Column(nullable = false)
    private Double radius;

    @Column(nullable = false)
    private Double price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getRowLabel() {
        return rowLabel;
    }

    public void setRowLabel(String rowLabel) {
        this.rowLabel = rowLabel;
    }

    public String getColLabel() {
        return colLabel;
    }

    public void setColLabel(String col) {
        this.colLabel = col;
    }

    public Double getRadius() {
        return radius;
    }

    public void setRadius(Double radius) {
        this.radius = radius;
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
        Seat seat = (Seat) o;
        return Objects.equals(id, seat.id) && Objects.equals(x, seat.x) && Objects.equals(y, seat.y) &&
            Objects.equals(rowLabel, seat.rowLabel) && Objects.equals(colLabel, seat.colLabel) &&
            Objects.equals(radius, seat.radius) && Objects.equals(price, seat.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, x, y, rowLabel, colLabel, radius, price);
    }
}
