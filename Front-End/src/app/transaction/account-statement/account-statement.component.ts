import { DatePipe } from '@angular/common';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Subscription } from 'rxjs';
import { ITransaction } from '../../models/transaction';
import { TransactionService } from '../..//services/transaction/transaction.service';
import { handleFormError } from '../..//shared/common-error';

@Component({
  selector: 'kata-account-statement',
  templateUrl: './account-statement.component.html',
  styleUrls: ['./account-statement.component.css'],
  providers: [DatePipe]
})
export class AccountStatementComponent implements OnInit, OnDestroy {
  accountStatementForm: FormGroup;
  accountStatement$: Subscription;
  transactionsAccountStatement: ITransaction[];
  errorMessage = '';

  constructor(private formBuilder: FormBuilder,
              private datePipe: DatePipe,
              private transactionService: TransactionService) { }

  ngOnInit(): void {
    this.accountStatementForm = this.formBuilder.group({
      startDate: ['', Validators.required],
      endDate: [this.datePipe.transform(new Date(), 'yyyy-MM-dd'), Validators.required]
    });
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
      this.accountStatement$ = this.transactionService.getAccountStatement(formValue.startDate, formValue.endDate)
        .subscribe(transactions => {
          if (transactions && transactions.length !== 0) {
            this.transactionsAccountStatement = transactions;
            this.errorMessage = '';
          } else {
            this.transactionsAccountStatement = [];
            this.errorMessage = 'No data could be recovered.';
          }
        });
    }
  }

  ngOnDestroy(): void {
    if (this.accountStatement$) {
      this.accountStatement$.unsubscribe();
    }
  }
}
