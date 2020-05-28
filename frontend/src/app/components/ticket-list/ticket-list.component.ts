import { Component, OnInit } from '@angular/core';
import { TicketApiService, TicketResponseDTO } from '../../../generated';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'tl-bookings',
  templateUrl: './ticket-list.component.html',
  styleUrls: ['./ticket-list.component.scss']
})
export class TicketListComponent implements OnInit {

  constructor(private readonly ticketService: TicketApiService) {
  }

  public tickets: TicketResponseDTO;

  ngOnInit() {
    this.ticketService.getTicketsOfUser().subscribe(value => {
      this.tickets = value;
    });
  }
}
