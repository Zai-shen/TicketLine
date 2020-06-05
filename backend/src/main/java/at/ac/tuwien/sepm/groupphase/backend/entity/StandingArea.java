package at.ac.tuwien.sepm.groupphase.backend.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class StandingArea {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    private Double x;

    @Column(nullable = false)
    private Double y;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long maxPeople;

    @Column(nullable = false)
    private Long price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getMaxPeople() {
        return maxPeople;
    }

    public void setMaxPeople(Long maxPeople) {
        this.maxPeople = maxPeople;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
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
        StandingArea that = (StandingArea) o;
        return Objects.equals(id, that.id) && Objects.equals(x, that.x) && Objects.equals(y, that.y) &&
            Objects.equals(name, that.name) && Objects.equals(maxPeople, that.maxPeople) &&
            Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, x, y, name, maxPeople, price);
    }
}
