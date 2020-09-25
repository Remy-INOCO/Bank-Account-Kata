import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { IUser } from '../models/user';
import { UserService } from '../services/user/user.service';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationGuard implements CanActivate {
  constructor(private router: Router,
              private userService: UserService) { }

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    
    // session for 10 min.
    const session = this.validateSession('LOGGED', 600000);

    if(!session && !this.userService.getCurrentUser()) {
      this.router.navigate(['login']);
      return false
    }

    return true;
  }
  
  private validateSession(key, exp) : boolean {
    const user: IUser = JSON.parse(localStorage.getItem(key));
    
    if (user) {
      if (new Date().getTime() - user.sessionTime > exp) {
        this.userService.setCurrentUser(null);
        return false
      } else {
        this.userService.setCurrentUser(user);
        return true
      }
    }
  }
}
