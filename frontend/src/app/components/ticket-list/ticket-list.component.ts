import { Component, OnInit } from '@angular/core';
import { TicketApiService, TicketResponseDTO } from '../../../generated';
import { AuthService } from '../../services/auth.service';
import { PlaceholderpdfService } from '../../services/placeholderpdf.service';

@Component({
  selector: 'tl-bookings',
  templateUrl: './ticket-list.component.html',
  styleUrls: ['./ticket-list.component.scss']
})
export class TicketListComponent implements OnInit {

  constructor(private readonly ticketService: TicketApiService, private readonly placeholderpdfService: PlaceholderpdfService) {
  }

  public tickets: TicketResponseDTO;

  ngOnInit() {
    this.ticketService.getTicketsOfUser().subscribe(value => {
      this.tickets = value;
    });
  }

  getInvoice(ticketId: Number): void {
    this.placeholderpdfService.downloadInvoicePdf(ticketId);
  }
}
