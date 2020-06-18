package at.ac.tuwien.sepm.groupphase.backend.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class StandingTicket extends Ticket {
    @Column
    private Long amount;

    @ManyToOne
    private StandingArea standingArea;

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public StandingArea getStandingArea() {
        return standingArea;
    }

    public void setStandingArea(StandingArea standingArea) {
        this.standingArea = standingArea;
    }
}
