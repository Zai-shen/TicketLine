import { Component, OnInit } from '@angular/core';
import { TicketApiService, BookingDTO } from '../../../generated';

@Component({
  selector: 'tl-bookings',
  templateUrl: './ticket-list.component.html',
  styleUrls: ['./ticket-list.component.scss']
})
export class TicketListComponent implements OnInit {

  constructor(private readonly ticketService: TicketApiService) {
  }

  public bookings: BookingDTO[] = [];
  public reserved: boolean = false;

  get filteredBookings(): BookingDTO[] {
    if (!this.bookings) {
      return [];
    }
    return this.bookings.filter(b => b.reservation === this.reserved);
  }

  ngOnInit() {
    this.ticketService.getTicketsOfUser().subscribe(value => {
      this.bookings = value;
    });
  }
}
