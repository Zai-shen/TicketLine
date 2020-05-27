package at.ac.tuwien.sepm.groupphase.backend.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class SeatedTicket extends Ticket {

    @Column
    private Long seatGroupId;

    @Column
    private Integer seatColumn;

    @Column
    private Integer seatRow;

    public Long getSeatGroupId() {
        return seatGroupId;
    }

    public void setSeatGroupId(Long seatGroupId) {
        this.seatGroupId = seatGroupId;
    }

    public Integer getSeatColumn() {
        return seatColumn;
    }

    public void setSeatColumn(Integer seatColumn) {
        this.seatColumn = seatColumn;
    }

    public Integer getSeatRow() {
        return seatRow;
    }

    public void setSeatRow(Integer seatRow) {
        this.seatRow = seatRow;
    }
}
