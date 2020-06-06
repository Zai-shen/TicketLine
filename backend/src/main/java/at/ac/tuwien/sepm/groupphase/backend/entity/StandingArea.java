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
    private Double width;

    @Column(nullable = false)
    private Double height;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long maxPeople;

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

    public Long getMaxPeople() {
        return maxPeople;
    }

    public void setMaxPeople(Long maxPeople) {
        this.maxPeople = maxPeople;
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
        StandingArea that = (StandingArea) o;
        return Objects.equals(id, that.id) && Objects.equals(x, that.x) && Objects.equals(y, that.y) &&
            Objects.equals(width, that.width) && Objects.equals(height, that.height) &&
            Objects.equals(name, that.name) && Objects.equals(maxPeople, that.maxPeople) &&
            Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, x, y, width, height, name, maxPeople, price);
    }
}
