import { NgModule } from '@angular/core';
import { HomeRoutingModule } from './home-routing.module';
import { HomeComponent } from './home.component';
import { SharedModule } from '../shared/shared.module';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { MatGridListModule } from '@angular/material/grid-list';
import { FlexLayoutModule } from '@angular/flex-layout';
import { DepositComponent } from '../deposit/deposit.component';
import { MatCardModule } from '@angular/material/card';
import { WithdrawalComponent } from '../withdrawal/withdrawal.component';
import { TransactionHistoryComponent } from '../transaction-history/transaction-history.component';
import { AccountStatementComponent } from '../account-statement/account-statement.component';

@NgModule({
  declarations: [
    HomeComponent,
    DepositComponent,
    WithdrawalComponent,
    TransactionHistoryComponent,
    AccountStatementComponent
  ],
  imports: [
    HomeRoutingModule,
    SharedModule,
    MatToolbarModule,
    MatSidenavModule,
    MatIconModule,
    MatListModule,
    MatGridListModule,
    FlexLayoutModule,
    MatCardModule
  ]
})
export class HomeModule { }
