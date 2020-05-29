package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.dto.ByteArrayFile;
import at.ac.tuwien.sepm.groupphase.backend.dto.TicketData;
import at.ac.tuwien.sepm.groupphase.backend.service.PdfService;
import at.ac.tuwien.sepm.groupphase.backend.service.TicketService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {

    private final PdfService pdfService;

    public TicketServiceImpl(PdfService pdfService) {
        this.pdfService = pdfService;
    }

    @Override
    public ByteArrayFile renderTickets(List<TicketData> tickets) {
        return pdfService.createPdfFromTemplate(tickets,"ticket.pdf","ticketTemplate.ftl");
    }
}
