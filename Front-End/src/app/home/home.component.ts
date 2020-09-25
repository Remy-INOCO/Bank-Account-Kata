import { Component, OnInit } from '@angular/core';
import { BreakpointObserver, Breakpoints, BreakpointState } from '@angular/cdk/layout';
import { Observable } from 'rxjs/internal/Observable';

import { Operation } from '../models/operation';
import { ITransaction } from '../models/transaction';
import { IUser } from '../models/user';
import { TransactionService } from '../services/transaction/transaction.service';
import { UserService } from '../services/user/user.service';

@Component({
  selector: 'kata-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  isHandset$: Observable<BreakpointState> = this.breakpointObserver.observe(Breakpoints.Handset);
  
  constructor(private breakpointObserver: BreakpointObserver,
              private transactionService: TransactionService,
              private userService: UserService) { }

  ngOnInit(): void {
    const currentUser: IUser = this.userService.getCurrentUser()
    const transaction: ITransaction = {
      idUser: currentUser.id,
      operation: Operation.DEPOSIT,
      wording: 'Premier test de versement',
      date: new Date(),
      amount: 200
    }
    
    this.transactionService.toMakeDeposit(transaction).subscribe()
    this.transactionService.getTransactionHistory().subscribe()
  }

}
