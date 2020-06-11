package at.ac.tuwien.sepm.groupphase.backend.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class StandingTicket extends Ticket {
    @Column
    private Long amount;

    @ManyToOne
    @Column(nullable = false)
    private SeatGroupArea seatGroupArea;

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public SeatGroupArea getSeatGroupArea() {
        return seatGroupArea;
    }

    public void setSeatGroupArea(SeatGroupArea seatGroupArea) {
        this.seatGroupArea = seatGroupArea;
    }
}
