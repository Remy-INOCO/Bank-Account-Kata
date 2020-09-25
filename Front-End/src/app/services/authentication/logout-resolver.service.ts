import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthenticationService } from './authentication.service';

@Injectable({
  providedIn: 'root'
})
export class LogoutResolver implements Resolve<boolean> {

  constructor(private authenticationService: AuthenticationService) { }

  resolve(route: ActivatedRouteSnapshot): Observable<boolean> | Promise<boolean> | boolean {
    return this.authenticationService.logout()
  }
}
