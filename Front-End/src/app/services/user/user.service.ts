import { Injectable } from '@angular/core';
import { User } from 'src/app/models/user';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  currentUser: User = JSON.parse(localStorage.getItem('LOGGED')) || null

  constructor() { }

  getCurrentUser() : User{
    return this.currentUser
  }

  setCurrentUser(user: User) : void {
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
