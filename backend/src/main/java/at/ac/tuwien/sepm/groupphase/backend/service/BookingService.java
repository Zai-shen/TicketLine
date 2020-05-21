package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Ticket;

import java.util.List;

public interface BookingService {
    void bookTickets(Long performanceId, boolean reserve, List<Ticket> tickets);

}
