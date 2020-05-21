import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {News} from '../dtos/news';
import {Observable} from 'rxjs';
import {Globals} from '../global/globals';

@Injectable({
  providedIn: 'root'
})
export class MessageService {

  private messageBaseUri: string = this.globals.backendUri + '/messages';

  constructor(private httpClient: HttpClient, private globals: Globals) {
  }

  /**
   * Loads all messages from the backend
   */
  getMessage(): Observable<News[]> {
    return this.httpClient.get<News[]>(this.messageBaseUri);
  }

  /**
   * Loads specific message from the backend
   * @param id of message to load
   */
  getMessageById(id: number): Observable<News> {
    console.log('Load message details for ' + id);
    return this.httpClient.get<News>(this.messageBaseUri + '/' + id);
  }

  /**
   * Persists message to the backend
   * @param message to persist
   */
  createMessage(message: News): Observable<News> {
    console.log('Create message with title ' + message.title);
    return this.httpClient.post<News>(this.messageBaseUri, message);
  }
}
