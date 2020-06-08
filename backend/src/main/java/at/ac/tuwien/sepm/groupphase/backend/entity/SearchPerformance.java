package at.ac.tuwien.sepm.groupphase.backend.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public class SearchPerformance {

    public LocalDate date;
    public LocalTime time;
    public BigDecimal price;
    public String event;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }
}
