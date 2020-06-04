package at.ac.tuwien.sepm.groupphase.backend.dto;

import at.ac.tuwien.sepm.groupphase.backend.entity.Booking;
import at.ac.tuwien.sepm.groupphase.backend.entity.SeatedTicket;
import at.ac.tuwien.sepm.groupphase.backend.entity.Ticket;
import at.ac.tuwien.sepm.groupphase.backend.entity.User;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class InvoiceData {
    private final Booking booking;
    private final User buyer;
    private final boolean cancelled;
    private Properties appProps;

    public InvoiceData(Booking booking, User buyer, boolean cancelled) {
        this.booking = booking;
        this.buyer = buyer;
        this.cancelled = cancelled;
        String appProperties = Thread.currentThread().getContextClassLoader().getResource("").getPath() + "application.properties";
        appProps = new Properties();
        try {
            appProps.load(new FileInputStream(appProperties));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return appProps.getProperty("name");
    }

    public String getStreet() {
        return appProps.getProperty("street");
    }

    public String getHousenr() {
        return appProps.getProperty("housenr");
    }

    public String getPostalcode() {
        return appProps.getProperty("postalcode");
    }

    public String getCity() {
        return appProps.getProperty("city");
    }

    public String getCountry() {
        return appProps.getProperty("country");
    }

    public String getUID() {
        return appProps.getProperty("UID");
    }

    public boolean getCancelled() {
        return cancelled;
    }

    public int getAmount() {
        return booking.getTickets().size();
    }

    public Booking getBooking() {
        return booking;
    }

    public List<Ticket> getTickets() {
        return booking.getTickets();
    }

    public int getSeatedAmount() {
        List<Ticket> tickets = new ArrayList<>();
        for (Ticket ticket : booking.getTickets()) {
            if (ticket instanceof SeatedTicket) {
                tickets.add(ticket);
            }
        }
        return tickets.size();
    }

    public User getBuyer() {
        return buyer;
    }

    public String formatPrice() {
        return new DecimalFormat("0.00").format(getPrice());
    }

    public BigDecimal getPrice() {
        BigDecimal sum = BigDecimal.valueOf(0);
        for (Ticket ticket : booking.getTickets()) {
            sum = sum.add(ticket.getPrice());
        }
        return sum;
    }

    public String formatVAT() {
        return new DecimalFormat("0.00").format(getVAT());
    }

    public BigDecimal getVAT() {
        return getPrice().multiply(BigDecimal.valueOf(0.13));
    }

    public String getTotalPrice() {
        return new DecimalFormat("0.00").format(getPrice().add(getVAT()));
    }
}
