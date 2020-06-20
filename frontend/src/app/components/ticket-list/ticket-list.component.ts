import { Component, OnInit } from '@angular/core';
import { TicketApiService, BookingDTO, FreeSeatgroupBookingDTO } from '../../../generated';
import { format, formatDistanceToNow } from 'date-fns';
import { de } from 'date-fns/locale';
import { BookingService } from '../../services/booking.service';

@Component({
  selector: 'tl-bookings',
  templateUrl: './ticket-list.component.html',
  styleUrls: ['./ticket-list.component.scss']
})
export class TicketListComponent implements OnInit {

  constructor(private readonly ticketService: TicketApiService, private readonly bookingService: BookingService) {

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
    this.updateTickets();
  }

  downloadInvoice(ticketId: number): void {
    this.bookingService.renderInvoice(ticketId);
  }

  sumStanding(free: FreeSeatgroupBookingDTO[]): number {
    return free.reduce((a, b) => a + b.amount, 0);
  }

  cancelBooking(booking: BookingDTO) {
    if (booking.id) {
      this.ticketService.cancelTickets(booking.id).subscribe(() => {
        this.updateTickets();
      });
    }
  }

  downloadTicket(booking: BookingDTO) {
    if (booking.id) {
      this.bookingService.renderTicket(booking.id);
    }
  }

  private updateTickets() {
    this.ticketService.getTicketsOfUser().subscribe(value => {
      this.bookings = value;
    });
  }

}
