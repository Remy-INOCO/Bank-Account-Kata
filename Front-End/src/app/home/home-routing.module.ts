import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { TransactionResolver } from '../services/transaction/transaction-resolver.service';
import { AccountStatementComponent } from '../transaction/account-statement/account-statement.component';
import { HandleTransactionComponent } from '../transaction/handle-transaction/handle-transaction.component';
import { TransactionHistoryComponent } from '../transaction/transaction-history/transaction-history.component';
import { HomeComponent } from './home.component';

const routes: Routes = [
  {
    path: '',
    component: HomeComponent,
    children: [
      {
        path: 'handle-transaction',
        component: HandleTransactionComponent
      },
      {
        path: 'account-statement',
        component: AccountStatementComponent
      },
      {
        path: 'transactions-history',
        component: TransactionHistoryComponent,
        resolve: { transactionsHistory: TransactionResolver }
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class HomeRoutingModule { }
