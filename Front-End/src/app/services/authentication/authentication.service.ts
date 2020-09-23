import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { tap, catchError } from 'rxjs/operators';
import { User } from '../../models/user';
import { shared } from '../../shared/index';
import { HttpHandleError } from '../../shared/http-handle-error'

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  private error = new HttpHandleError()

  constructor(private http: HttpClient) { }

  login(user: User): Observable<User> {
    console.log('Connection');
    
    return this.http.post(shared.apiUrl + "authentication", user).pipe(
      tap((user: User) => console.log("User :", user)),
      catchError(this.error.handleError<User>('authentication'))
    )
  }
}
