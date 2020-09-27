import { Component, OnInit } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute } from '@angular/router';
import { ITransaction, TransactionResolved } from '../../models/transaction';

@Component({
  selector: 'kata-transaction-history',
  templateUrl: './transaction-history.component.html',
  styleUrls: ['./transaction-history.component.css']
})
export class TransactionHistoryComponent implements OnInit {
  errorMessage: string = '';
  transactionsHistory: ITransaction[];

  constructor(private route: ActivatedRoute) { }

  ngOnInit(): void {
    const transactionResolved: TransactionResolved = this.route.snapshot.data['transactionsHistory'];
    
    this.transactionsHistory = transactionResolved.transactions;

    if (!this.transactionsHistory) {
      this.errorMessage = transactionResolved.errorMessage ? transactionResolved.errorMessage : 'Error when call transaction history ';
    }
  }
}
