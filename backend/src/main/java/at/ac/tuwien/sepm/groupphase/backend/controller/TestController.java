package at.ac.tuwien.sepm.groupphase.backend.controller;

import at.ac.tuwien.sepm.groupphase.backend.dto.ByteArrayFile;
import at.ac.tuwien.sepm.groupphase.backend.dto.TicketData;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.Performance;
import at.ac.tuwien.sepm.groupphase.backend.service.EventService;
import at.ac.tuwien.sepm.groupphase.backend.service.PdfService;
import at.ac.tuwien.sepm.groupphase.backend.service.PerformanceService;
import io.swagger.annotations.ApiOperation;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.UUID;

@RestController
public class TestController {

    private final PdfService pdfService;
    private final EventService eventService;
    private final PerformanceService performanceService;

    public TestController(PdfService pdfService, EventService eventService, PerformanceService performanceService) {
        this.pdfService = pdfService;
        this.eventService = eventService;
        this.performanceService = performanceService;
    }

    @PostMapping(path = "/pdf")
    @ApiOperation("Returns placeholder Pdf")
    public ResponseEntity<InputStreamResource> getPlaceholderPdf() {
        Event e = eventService.getEvent(1).get();
        List<Performance> performances = eventService.getPerformances(e.getId());
        TicketData td = new TicketData(e,"Row 1 Seat 4", performances.get(0), UUID.randomUUID());
        ByteArrayFile pdf = pdfService.createPdfFromTemplate(td,"ticket.pdf","ticketTemplate.ftl");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/pdf"));
        headers.add("Content-Disposition", "filename=" + pdf.getName());
        headers.add("Access-Control-Expose-Headers", "Content-Disposition");
        headers.setContentLength(pdf.getContent().length);

        return new ResponseEntity<>(
            new InputStreamResource(new ByteArrayInputStream(pdf.getContent())), headers, HttpStatus.OK);
    }
}
