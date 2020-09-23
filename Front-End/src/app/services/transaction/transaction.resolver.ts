import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs';
import { Transaction } from 'src/app/models/transaction';
import { UserService } from '../user/user.service';
import { TransactionService } from './transaction.service';

@Injectable({
  providedIn: 'root'
})
export class TransactionResolver implements Resolve<Transaction> {

  constructor(private userService: UserService,
              private transactionService: TransactionService) { }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Transaction | Observable<Transaction> | Promise<Transaction> {
    const currentUser = this.userService.getCurrentUser()
    // this.transactionService.
    return null
  }
}
