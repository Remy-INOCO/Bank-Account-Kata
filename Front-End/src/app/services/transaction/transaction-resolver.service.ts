import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot } from '@angular/router';
import { Observable, of } from 'rxjs';
import { map, catchError } from 'rxjs/operators';
import { IErrorMessage } from '../../models/error-message';
import { HttpHandleError } from '../../shared/http-handle-error';
import { ITransaction } from '../../models/transaction';
import { TransactionService } from './transaction.service';

@Injectable({
  providedIn: 'root'
})
export class TransactionResolver implements Resolve<ITransaction[] | IErrorMessage> {
  private error = new HttpHandleError();

  constructor(private transactionService: TransactionService) { }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ITransaction[] | IErrorMessage> {
    return this.transactionService.getTransactionHistory().pipe(
      map(transactions => transactions),
      catchError(() => of({
        statusCode: 500,
        timestamp: new Date(),
        message: 'Unknown error. Try again later'
      }))
    );
  }
}
