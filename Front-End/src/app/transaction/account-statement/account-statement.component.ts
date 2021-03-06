import { DatePipe } from '@angular/common';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import * as es6printJS from 'print-js';

import { ITransaction } from '../../models/transaction';
import { TransactionService } from '../..//services/transaction/transaction.service';
import { handleFormError } from '../..//shared/common-error';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { IErrorMessage } from 'src/app/models/error-message';

@Component({
  selector: 'kata-account-statement',
  templateUrl: './account-statement.component.html',
  styleUrls: ['./account-statement.component.css'],
  providers: [DatePipe]
})
export class AccountStatementComponent implements OnInit, OnDestroy {
  accountStatementForm: FormGroup;
  onDestroy$ = new Subject();
  transactionsAccountStatement: ITransaction[];
  errorMessage: IErrorMessage = {
    authentication: ''
  };

  constructor(private formBuilder: FormBuilder,
              private datePipe: DatePipe,
              private transactionService: TransactionService) { }

  ngOnInit(): void {
    this.accountStatementForm = this.formBuilder.group({
      startDate: ['', Validators.required],
      endDate: [this.datePipe.transform(new Date(), 'yyyy-MM-dd'), Validators.required]
    });
  }

  ngOnDestroy(): void {
    this.onDestroy$.next();
    this.onDestroy$.complete();
  }

  handleFormError(form: FormGroup, field: string): boolean {
    return handleFormError(form, field);
  }

  checkDate(): boolean {
    const formValue = this.accountStatementForm.value;

    return formValue.startDate >= formValue.endDate ? false : true;
  }

  submit(): void {
    const formValue = this.accountStatementForm.value;

    if (this.accountStatementForm.valid) {
      this.transactionService.getAccountStatement(formValue.startDate, formValue.endDate)
        .pipe(
          takeUntil(this.onDestroy$)
        )
        .subscribe(transactions => {
          if (transactions && transactions.length !== 0) {
            this.transactionsAccountStatement = transactions;
            this.errorMessage.authentication = '';
          } else {
            this.transactionsAccountStatement = [];
            this.errorMessage.authentication = 'No data could be recovered.';
          }
        }, error => {
          if (error.statusCode === 400 || error.statusCode === 401) {
            this.errorMessage.authentication = error.message;
          } else if (error.statusText) {
            this.errorMessage.authentication = error.statusText + '. Try again later.';
          }
        });
    }
  }

  printAccountStatement(): void {
    const formValue = this.accountStatementForm.value;

    if (formValue && this.transactionsAccountStatement) {
      const jsonArray = this.transactionsAccountStatement.map(transaction => ({...transaction}));
      jsonArray.forEach((transaction: any) => {
        transaction.amount = transaction.amount + ' €';
        transaction.balance = transaction.balance + ' €';
        transaction.date = this.datePipe.transform(transaction.date, 'dd/MM/yyyy');
      });
      es6printJS({
        printable: jsonArray,
        type: 'json',
        properties: ['date', 'operation', 'wording', 'amount', 'balance'],
        header: '<h3 class="custom-h3">My account statement from ' + this.datePipe.transform(formValue.startDate, 'dd/MM/yyyy') +
          ' to ' + this.datePipe.transform(formValue.endDate, 'dd/MM/yyyy') + '</h3>'
      });
    }
  }
}
