package at.ac.tuwien.sepm.groupphase.backend.entity;

import com.google.common.base.Objects;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

@Entity
public class Ticket {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    private BigDecimal price;

    @ManyToOne
    private Booking booking;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public String formatPriceExclVAT() {
        return new DecimalFormat("0.00").format(price.divide(BigDecimal.valueOf(1.13), 2, RoundingMode.HALF_UP));
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
