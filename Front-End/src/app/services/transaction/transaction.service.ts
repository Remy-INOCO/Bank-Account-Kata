import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { IServerErrorMessage } from '../../models/server-error-message';
import { ITransaction } from '../../models/transaction';
import { HttpHandleError } from '../../shared/http-handle-error';
import { shared } from '../../shared/index';

@Injectable({
  providedIn: 'root'
})
export class TransactionService {
  private transactionUrl = 'transactions/';
  private error = new HttpHandleError();

  constructor(private httpClient: HttpClient) { }

  getTransactionHistory(): Observable<ITransaction[]> {
    return this.httpClient.get<ITransaction[]>(shared.apiUrl + this.transactionUrl + 'history').pipe(
      catchError(this.error.handleError<IServerErrorMessage>('getTransactionHistory'))
    );
  }

  toMakeDeposit(transaction: ITransaction): Observable<ITransaction> {
    return this.httpClient.put<ITransaction>(shared.apiUrl + this.transactionUrl + 'deposit', transaction).pipe(
      catchError(this.error.handleError<IServerErrorMessage>('toMakeDeposit'))
    );
  }

  toMakeWithdrawal(transaction: ITransaction): Observable<ITransaction> {
    return this.httpClient.put<ITransaction>(shared.apiUrl + this.transactionUrl + 'withdrawal', transaction).pipe(
      catchError(this.error.handleError<IServerErrorMessage>('toMakeWithdrawal'))
    );
  }

  getAccountStatement(startDate: string, endDate: string): Observable<ITransaction[]> {
    return this.httpClient.get<ITransaction[]>(shared.apiUrl + this.transactionUrl + 'accountStatement/' +
      new Date(startDate) + '-' + new Date(endDate)).pipe(
      catchError(this.error.handleError<IServerErrorMessage>('getAccountStatement'))
    );
  }
}
