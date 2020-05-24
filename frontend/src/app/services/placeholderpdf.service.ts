import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Globals } from '../global/globals';

@Injectable({
  providedIn: 'root'
})
export class PlaceholderpdfService {

  constructor(private httpClient: HttpClient, private globals: Globals) {
  }

  downloadPlaceholderPdf() {
    let headers = new HttpHeaders();
    headers = headers.set('Accept', 'application/pdf');
    this.httpClient.post(this.globals.backendUri + '/pdf',
      null,
      { headers: headers, responseType: 'blob', observe: 'response' })
        .subscribe(
          (result: HttpResponse<Blob>) => {
                   this.downloadFile(result);
    });
  }

  parseFilenameFromHeader(result: HttpResponse<Blob>): string {
    const header = result.headers.get('content-disposition');
    const filename = header == null ? 'download.pdf' : header.split('=')[1];
    return decodeURI(filename.replace(/"/g, ''));
  }

  downloadFile(response: HttpResponse<Blob>) {
    const filename: string = this.parseFilenameFromHeader(response);
    const binaryData = [];
    if (response.body != null) {
      binaryData.push(response.body);
    }
    const downloadLink = document.createElement('a');
    downloadLink.href = window.URL.createObjectURL(new Blob(binaryData, { type: 'blob' }));
    downloadLink.setAttribute('download', filename);
    document.body.appendChild(downloadLink);
    downloadLink.click();
  }
}
