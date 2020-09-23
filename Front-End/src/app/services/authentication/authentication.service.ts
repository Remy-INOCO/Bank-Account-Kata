import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { tap, catchError } from 'rxjs/operators'
import { IUser } from '../../models/user'
import { shared } from '../../shared/index'
import { HttpHandleError } from '../../shared/http-handle-error'
import { Router } from '@angular/router';
import { UserService } from '../user/user.service';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  private error = new HttpHandleError()

  constructor(private http: HttpClient,
              private router: Router,
              private userService: UserService) { }

  login(user: IUser): Observable<IUser> {
    console.log('Connection');
    
    return this.http.post<IUser>(shared.apiUrl + "login", user).pipe(
      tap((user: IUser) => console.log("User :", user)),
      catchError(this.error.handleError<IUser>('authentication'))
    )
  }

  logout(){
    this.http.get(shared.apiUrl + "logout").pipe(
      catchError(this.error.handleError<IUser>('authentication'))
    )
  }

  logoutComplete(){
    this.router.navigate(['login'])
    this.userService.clearStorage();
  }
}
