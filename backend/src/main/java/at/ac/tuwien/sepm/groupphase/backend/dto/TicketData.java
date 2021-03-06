package at.ac.tuwien.sepm.groupphase.backend.dto;

import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.Performance;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.pdf417.PDF417Writer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Base64;
import java.util.UUID;

public class TicketData {
    private final Event event;
    private final String seat;
    private final String area;
    private final UUID uuid;
    private final Performance performance;
    private final BigDecimal price;

    public TicketData(Event event, String seat, String area, Performance performance, UUID uuid, BigDecimal price) {
        this.event = event;
        this.seat = seat;
        this.area = area;
        this.performance = performance;
        this.uuid = uuid;
        this.price = price;
    }

    public Event getEvent() {
        return event;
    }

    public String getSeat() {
        return seat;
    }

    public String getArea() {
        return area;
    }

    public UUID getUUID() {
        return uuid;
    }

    public Performance getPerformance() {
        return performance;
    }

    public String formatPrice() {
        return new DecimalFormat("0.00").format(this.price);
    }

    public String getBarcode() throws WriterException, IOException {
        PDF417Writer bcw = new PDF417Writer();
        BitMatrix bm = bcw.encode(uuid.toString(),BarcodeFormat.PDF_417,300,150);
        return encodeImage(MatrixToImageWriter.toBufferedImage(bm));
    }

    private String encodeImage(BufferedImage image) throws IOException {
        StringBuilder sb = new StringBuilder("data:image/png;base64,");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(image,"png", Base64.getEncoder().wrap(bos));
        sb.append(bos.toString());
        return sb.toString();
    }
}
