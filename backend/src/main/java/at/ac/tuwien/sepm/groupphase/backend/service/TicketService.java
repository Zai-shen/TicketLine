package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.dto.ByteArrayFile;
import at.ac.tuwien.sepm.groupphase.backend.dto.InvoiceData;
import at.ac.tuwien.sepm.groupphase.backend.dto.TicketData;

import java.util.List;

public interface TicketService {
    /**
     * render a list of tickets to a pdf file
     * @param tickets list of tickets to be rendered. Each ticket will be placed on its own page
     * @return File containing the PDF as a byte Array
     * @see ByteArrayFile
     */
    ByteArrayFile renderTickets(List<TicketData> tickets);

    /**
     * renders the invoice to a pdf file
     * @param invoice invoice to be rendered.
     * @return File containing the PDF as a byte Array
     * @see ByteArrayFile
     */
    ByteArrayFile renderInvoice(InvoiceData invoice);
}
