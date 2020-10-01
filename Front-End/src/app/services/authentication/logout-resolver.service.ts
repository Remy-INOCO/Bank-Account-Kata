import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { map, catchError } from 'rxjs/operators';
import { IServerErrorMessage } from '../../models/server-error-message';
import { HttpHandleError } from '../../shared/http-handle-error';
import { AuthenticationService } from './authentication.service';

@Injectable({
  providedIn: 'root'
})
export class LogoutResolver implements Resolve<boolean | IServerErrorMessage> {
  private error = new HttpHandleError();

  constructor(private authenticationService: AuthenticationService,
              private router: Router) { }

  resolve(route: ActivatedRouteSnapshot): Observable<any> {
    return this.authenticationService.logout().pipe(
      map(isDeconnected => isDeconnected),
      catchError(() => this.router.navigate(['login']))
    );
  }
}
