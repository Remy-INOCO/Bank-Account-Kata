import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { map, catchError } from 'rxjs/operators';

import { IUser } from '../../models/user';
import { shared } from '../../shared/index';
import { HttpHandleError } from '../../shared/http-handle-error';
import { UserService } from '../user/user.service';
import { IErrorMessage } from '../../models/error-message';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  private error = new HttpHandleError();

  constructor(private http: HttpClient,
              private router: Router,
              private userService: UserService) { }

  login(currentUser: IUser): Observable<IUser | IErrorMessage> {
    return this.http.post<IUser>(shared.apiUrl + 'login', currentUser).pipe(
      map((user: IUser) => {
        user.id = null;
        user.password = '';
        return user;
      }),
      catchError(this.error.handleError<IErrorMessage>('login'))
    );
  }

  logout(): Observable<boolean | IErrorMessage> {
    return this.http.get<boolean>(shared.apiUrl + 'logout').pipe(
      catchError(this.error.handleError<IErrorMessage>('logout'))
    );
  }

  logoutComplete(): void {
    this.router.navigate(['login']);
    this.userService.clearStorage();
  }
}
