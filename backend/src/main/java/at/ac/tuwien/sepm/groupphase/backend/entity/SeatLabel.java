package at.ac.tuwien.sepm.groupphase.backend.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class SeatLabel {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    private Double x;

    @Column(nullable = false)
    private Double y;

    @Column(nullable = false)
    private Double size;

    @Column(nullable = false)
    private String text;

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

    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SeatLabel seatLabel = (SeatLabel) o;
        return Double.compare(seatLabel.x, x) == 0 && Double.compare(seatLabel.y, y) == 0 &&
            Double.compare(seatLabel.size, size) == 0 && Objects.equals(id, seatLabel.id) &&
            Objects.equals(text, seatLabel.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, x, y, size, text);
    }
}
