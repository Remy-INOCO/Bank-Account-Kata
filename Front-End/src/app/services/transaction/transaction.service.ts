import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { tap, catchError } from 'rxjs/operators'
import { ITransaction } from 'src/app/models/transaction';
import { HttpHandleError } from 'src/app/shared/http-handle-error';
import { shared } from '../../shared/index'

@Injectable({
  providedIn: 'root'
})
export class TransactionService {
  private transactionUrl = 'transactions/'
  private error = new HttpHandleError()

  constructor(private httpClient: HttpClient) { }

  getTransactionHistory(): Observable<ITransaction[]> {
    return this.httpClient.get<ITransaction[]>(shared.apiUrl + this.transactionUrl +  "history").pipe(
      tap(transactions => console.log(transactions)),
      catchError(this.error.handleError<ITransaction[]>('getTransactionHistory'))
    )
  }

  toMakeDeposit(transaction: ITransaction): Observable<ITransaction> {
    return this.httpClient.put<ITransaction>(shared.apiUrl + this.transactionUrl +  "deposit", transaction).pipe(
      tap(result => console.log(result)),
      catchError(this.error.handleError<ITransaction>('toMakeDeposit'))
    )
  }
}
