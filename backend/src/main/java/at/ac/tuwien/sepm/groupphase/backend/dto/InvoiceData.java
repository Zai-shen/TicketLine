package at.ac.tuwien.sepm.groupphase.backend.dto;

import at.ac.tuwien.sepm.groupphase.backend.config.properties.CompanyProperties;
import at.ac.tuwien.sepm.groupphase.backend.entity.Booking;
import at.ac.tuwien.sepm.groupphase.backend.entity.Ticket;
import at.ac.tuwien.sepm.groupphase.backend.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;

@ConfigurationProperties(prefix="ticketline")
public class InvoiceData {
    private final Booking booking;
    private final User buyer;
    private final boolean cancelled;

    private final CompanyProperties companyProperties;

    @Autowired
    public InvoiceData(Booking booking, User buyer, boolean cancelled, CompanyProperties companyProperties) {
        this.booking = booking;
        this.buyer = buyer;
        this.cancelled = cancelled;
        this.companyProperties = companyProperties;
    }

    public String getName() {
        return companyProperties.getName();
    }

    public String getStreet() {
        return companyProperties.getStreet();
    }

    public String getHousenr() {
        return companyProperties.getHousenr();
    }

    public String getPostalcode() {
        return companyProperties.getPostalcode();
    }

    public String getCity() {
        return companyProperties.getCity();
    }

    public String getCountry() {
        return companyProperties.getCountry();
    }

    public String getUID() {
        return companyProperties.getUID();
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

    public String formatDate() {
        return booking.getDate().format(DateTimeFormatter.ofPattern("dd.MM.YYYY"));
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
            sum = sum.add(ticket.getPrice().divide(BigDecimal.valueOf(1.13), 2, RoundingMode.HALF_UP));
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
