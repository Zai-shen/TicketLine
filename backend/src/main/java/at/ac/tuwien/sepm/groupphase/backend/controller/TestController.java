package at.ac.tuwien.sepm.groupphase.backend.controller;

import at.ac.tuwien.sepm.groupphase.backend.dto.ByteArrayFile;
import at.ac.tuwien.sepm.groupphase.backend.dto.TicketData;
import at.ac.tuwien.sepm.groupphase.backend.entity.Address;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.Location;
import at.ac.tuwien.sepm.groupphase.backend.entity.Performance;
import at.ac.tuwien.sepm.groupphase.backend.service.EventService;
import at.ac.tuwien.sepm.groupphase.backend.service.TicketService;
import io.swagger.annotations.ApiOperation;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.UUID;

@RestController
public class TestController {

    private final EventService eventService;
    private final TicketService ticketService;

    public TestController(EventService eventService, TicketService ticketService) {
        this.eventService = eventService;
        this.ticketService = ticketService;
    }

    @PostMapping(path = "/pdf")
    @ApiOperation("Returns placeholder Pdf")
    public ResponseEntity<InputStreamResource> getPlaceholderPdf() {
        Event e = eventService.getEvent(1).get();
        e.setTitle("25 Jahre Rantanplan");
        e.setDescription("Gitarre, Bass, Schlagzeug, Gesang, Trompete, Posaune, hier und da etwas Orgel, fertig. Wer da stillsitzen kann, muss mal zum Arzt. Der Titel Skapunk Band Nr.1 ist hiermit erfolgreich verteidigt. Das Treppchen wird vergoldet. Man wünscht sich mehr solche „Künstler“. Man wird an Ton Steine Scherben erinnert.");
        Performance p = new Performance();
        p.setDateTime(OffsetDateTime.of(2020,9,16,20,0,0,0, ZoneOffset.ofHours(2)));
        Address arenaAddress = new Address();
        arenaAddress.setCity("Wien");
        arenaAddress.setCountry("Austria");
        arenaAddress.setStreet("Baumgasse");
        arenaAddress.setHousenr("80");
        arenaAddress.setPostalcode("1030");
        Location arena = new Location();
        arena.setDescription("Arena Wien");
        arena.setAddress(arenaAddress);
        p.setLocation(arena);

        List<TicketData> tickets = new ArrayList<>();
        tickets.add(new TicketData(e,"Reihe 1 Platz 14", p, UUID.randomUUID(), BigDecimal.valueOf(20.49)));
        tickets.add(new TicketData(e,"Reihe 1 Platz 15", p, UUID.randomUUID(), BigDecimal.valueOf(20.49)));
        ByteArrayFile pdf = ticketService.renderTickets(tickets);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/pdf"));
        headers.add("Content-Disposition", "filename=" + pdf.getName());
        headers.add("Access-Control-Expose-Headers", "Content-Disposition");
        headers.setContentLength(pdf.getContent().length);

        return new ResponseEntity<>(
            new InputStreamResource(new ByteArrayInputStream(pdf.getContent())), headers, HttpStatus.OK);
    }
}
