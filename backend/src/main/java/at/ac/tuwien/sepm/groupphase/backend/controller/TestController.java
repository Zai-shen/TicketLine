package at.ac.tuwien.sepm.groupphase.backend.controller;

import at.ac.tuwien.sepm.groupphase.backend.dto.ByteArrayFile;
import at.ac.tuwien.sepm.groupphase.backend.service.PdfService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;

@RestController
public class TestController {

    private final PdfService pdfService;

    public TestController(PdfService pdfService) {
        this.pdfService = pdfService;
    }

    @PostMapping(path = "/pdf")
    @ApiOperation("Returns placeholder Pdf")
    public ResponseEntity<InputStreamResource> getPlaceholderPdf() {
        ByteArrayFile pdf = pdfService.createPlaceholderPdf();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/pdf"));
        headers.add("Content-Disposition", "filename=" + pdf.getName());
        headers.add("Access-Control-Expose-Headers", "Content-Disposition");
        headers.setContentLength(pdf.getContent().length);

        return new ResponseEntity<>(
            new InputStreamResource(new ByteArrayInputStream(pdf.getContent())), headers, HttpStatus.OK);
    }
}
