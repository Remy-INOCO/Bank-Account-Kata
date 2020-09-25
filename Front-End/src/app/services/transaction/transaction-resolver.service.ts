import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs';
import { ITransaction } from 'src/app/models/transaction';
import { UserService } from '../user/user.service';
import { TransactionService } from './transaction.service';

@Injectable({
  providedIn: 'root'
})
export class TransactionResolver implements Resolve<ITransaction> {

  constructor(private userService: UserService,
              private transactionService: TransactionService) { }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): ITransaction | Observable<ITransaction> | Promise<ITransaction> {
    const currentUser = this.userService.getCurrentUser()
    // this.transactionService.
    return null
  }
}
