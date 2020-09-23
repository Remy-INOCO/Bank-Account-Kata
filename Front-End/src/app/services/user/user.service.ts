import { Injectable } from '@angular/core';
import { IUser } from 'src/app/models/user';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  currentUser: IUser = JSON.parse(localStorage.getItem('LOGGED')) || null

  constructor() { }

  getCurrentUser() : IUser{
    return this.currentUser
  }

  setCurrentUser(user: IUser) : void {
    if (user) {
      user.sessionTime = new Date().getTime()
      localStorage.setItem('LOGGED', JSON.stringify(user))
    }

    this.currentUser = user
  }

  clearStorage(){
    this.setCurrentUser(null);
    
    localStorage.clear();
  }
}
