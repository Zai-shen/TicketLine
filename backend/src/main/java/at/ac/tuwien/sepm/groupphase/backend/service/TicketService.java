package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.dto.ByteArrayFile;
import at.ac.tuwien.sepm.groupphase.backend.dto.TicketData;

import java.util.List;

public interface TicketService {
    ByteArrayFile renderTickets(List<TicketData> tickets);
}
