import { NgModule } from '@angular/core';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatCardModule } from '@angular/material/card';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSortModule } from '@angular/material/sort';
import { MatSliderModule } from '@angular/material/slider';
import { MatSelectModule } from '@angular/material/select';
import { MatButtonModule } from '@angular/material/button';

import { HomeRoutingModule } from './home-routing.module';
import { HomeComponent } from './home.component';
import { SharedModule } from '../shared/shared.module';
import { TransactionHistoryComponent } from '../transaction/transaction-history/transaction-history.component';
import { AccountStatementComponent } from '../transaction/account-statement/account-statement.component';
import { HandleTransactionComponent } from '../transaction/handle-transaction/handle-transaction.component';
import { TransactionTableViewComponent } from '../transaction/transaction-table-view/transaction-table-view.component';

@NgModule({
  declarations: [
    HomeComponent,
    TransactionHistoryComponent,
    AccountStatementComponent,
    HandleTransactionComponent,
    TransactionTableViewComponent
  ],
  imports: [
    HomeRoutingModule,
    SharedModule,
    MatToolbarModule,
    MatSidenavModule,
    MatIconModule,
    MatListModule,
    MatGridListModule,
    MatCardModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatSliderModule,
    MatSelectModule,
    MatButtonModule
  ]
})
export class HomeModule { }
