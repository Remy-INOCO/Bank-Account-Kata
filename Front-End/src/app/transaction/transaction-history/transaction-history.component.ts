import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITransaction } from '../../models/transaction';

@Component({
  selector: 'kata-transaction-history',
  templateUrl: './transaction-history.component.html',
  styleUrls: ['./transaction-history.component.css']
})
export class TransactionHistoryComponent implements OnInit {
  errorMessage = '';
  transactionsHistory: ITransaction[];

  constructor(private route: ActivatedRoute) { }

  ngOnInit(): void {
    const transactionResolved = this.route.snapshot.data.transactionsHistory;

    if (transactionResolved && Array.isArray(transactionResolved) && transactionResolved.length !== 0) {
      this.transactionsHistory = transactionResolved;
    } else if (transactionResolved.statusCode === 500) {
      this.errorMessage = transactionResolved.message;
    }
  }
}
