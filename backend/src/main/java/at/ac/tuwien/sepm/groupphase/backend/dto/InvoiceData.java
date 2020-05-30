package at.ac.tuwien.sepm.groupphase.backend.dto;

import at.ac.tuwien.sepm.groupphase.backend.entity.Booking;
import at.ac.tuwien.sepm.groupphase.backend.entity.User;
import at.ac.tuwien.sepm.groupphase.backend.repository.BookingRepository;

import java.math.BigDecimal;

public class InvoiceData {
    private final Booking booking;
    private final User buyer;
    private final BigDecimal price;
    private final BigDecimal VAT;
    private final Long invoiceId;
    private final String sellerUID;

    private BookingRepository bookingRepository;

    public InvoiceData(Booking booking, User buyer, BigDecimal price, BigDecimal vat, Long invoiceId, String sellerUID) {
        this.booking = booking;
        this.buyer = buyer;
        this.price = price;
        this.VAT = vat;
        this.invoiceId = invoiceId;
        this.sellerUID = sellerUID;
    }

    public int getAmount() {
        return booking.getTickets().size();
    }

    public Booking getBooking() {
        return booking;
    }

    public User getBuyer() {
        return buyer;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getVAT() {
        return VAT;
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public String getSellerUID() {
        return sellerUID;
    }
}
