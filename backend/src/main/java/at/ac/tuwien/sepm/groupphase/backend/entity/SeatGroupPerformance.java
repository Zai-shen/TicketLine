package at.ac.tuwien.sepm.groupphase.backend.entity;

import com.google.common.base.Objects;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class SeatGroupPerformance{
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @ManyToOne
    private SeatGroup seatGroup;

    @ManyToOne
    private Performance performance;

    @Column(nullable = false)
    private BigDecimal ticketPrice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SeatGroup getSeatGroup() {
        return seatGroup;
    }

    public void setSeatGroup(SeatGroup seatGroup) {
        this.seatGroup = seatGroup;
    }

    public Performance getPerformance() {
        return performance;
    }

    public void setPerformance(Performance performance) {
        this.performance = performance;
    }

    public BigDecimal getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(BigDecimal ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SeatGroupPerformance location = (SeatGroupPerformance) o;
        return java.util.Objects.equals(id, location.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
