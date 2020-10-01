import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot } from '@angular/router';
import { Observable, of } from 'rxjs';
import { map, catchError } from 'rxjs/operators';
import { IServerErrorMessage } from '../../models/server-error-message';
import { HttpHandleError } from '../../shared/http-handle-error';
import { ITransaction } from '../../models/transaction';
import { TransactionService } from './transaction.service';

@Injectable({
  providedIn: 'root'
})
export class TransactionResolver implements Resolve<ITransaction[] | IServerErrorMessage> {
  private error = new HttpHandleError();

  constructor(private transactionService: TransactionService) { }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ITransaction[] | IServerErrorMessage> {
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
