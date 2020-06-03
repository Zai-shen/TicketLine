import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { NewsApiService, NewsDTO, TicketApiService, UserApiService } from '../../generated';
import { Globals } from '../global/globals';

@Injectable({
  providedIn: 'root'
})
export class BookingService {

  constructor(private ticketApiService: TicketApiService, private userApiService: UserApiService, private httpClient: HttpClient, private globals: Globals) { }

  public renderTicket(bookingId: number): void {
    let headers = new HttpHeaders();
    headers = headers.set('Accept', 'application/pdf');
    this.httpClient.get(this.globals.backendUri + '/user/ticket/' + bookingId + '/ticket',
      { headers: headers, responseType: 'blob', observe: 'response' })
        .subscribe(
          (result: HttpResponse<Blob>) => {
            this.downloadFile(result);
          });
  }

  downloadFile(response: HttpResponse<Blob>) {
    const filename: string = this.parseFilenameFromHeader(response);
    const binaryData: Blob[] = [];
    if (response.body !== null) {
      binaryData.push(response.body);
    }
    const downloadLink = document.createElement('a');
    downloadLink.href = window.URL.createObjectURL(new Blob(binaryData, { type: 'blob' }));
    downloadLink.setAttribute('download', filename);
    document.body.appendChild(downloadLink);
    downloadLink.click();
  }

  parseFilenameFromHeader(result: HttpResponse<Blob>): string {
    const header = result.headers.get('content-disposition');
    let filename = 'ticketline.pdf';
    if (header !== null && header.length >= 2) {
      filename = header.split('=')[1];
    }
    return decodeURI(filename.replace(/"/g, ''));
  }
}
