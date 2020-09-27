import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot } from '@angular/router';
import { Observable, of } from 'rxjs';
import { map, catchError } from 'rxjs/operators';
import { TransactionResolved } from '../../models/transaction';
import { TransactionService } from './transaction.service';

@Injectable({
  providedIn: 'root'
})
export class TransactionResolver implements Resolve<TransactionResolved> {

  constructor(private transactionService: TransactionService) { }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<TransactionResolved> {
    return this.transactionService.getTransactionHistory().pipe(
      map(transactions => ({transactions})),
      catchError((error) => {
        return of({
          transactions: null,
          errorMessage: 'Error when call transaction history ' + error
        });
      })
    );
  }
}

// resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<TransactionResolved> {
//   console.log('yolo');
  
//   return new Promise((resolve, reject) => {
//     this.transactionService.getTransactionHistory().pipe(
//       map(transactions => resolve({ transactions })),
//       catchError(async (error) => reject({
//         transactions: null,
//         errorMessage: 'Error when call transaction history ' + error
//       }))
//     ).subscribe()
//   })
// }
