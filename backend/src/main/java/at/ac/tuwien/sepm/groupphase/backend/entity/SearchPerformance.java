package at.ac.tuwien.sepm.groupphase.backend.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

public class SearchPerformance {

    public LocalDate date;
    public String time;
    public BigDecimal price;
    public String event;
    public String location;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
