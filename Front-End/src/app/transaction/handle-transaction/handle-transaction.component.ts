import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { of } from 'rxjs';
import { iif } from 'rxjs/internal/observable/iif';
import { Subject } from 'rxjs/internal/Subject';
import { filter, switchMap, takeUntil, tap } from 'rxjs/operators';
import { withdrawalValidator } from 'src/app/custom-validator/withdrawal-validator';
import { IErrorMessage } from 'src/app/models/error-message';
import { Operation } from '../../models/operation';
import { ITransaction } from '../../models/transaction';
import { IUser } from '../../models/user';
import { TransactionService } from '../../services/transaction/transaction.service';
import { UserService } from '../../services/user/user.service';
import { handleFormError } from '../../shared/common-error';

@Component({
  selector: 'kata-handle-transaction',
  templateUrl: './handle-transaction.component.html',
  styleUrls: ['./handle-transaction.component.css']
})
export class HandleTransactionComponent implements OnInit, OnDestroy {
  private readonly onDestroy$ = new Subject();
  currentUser: IUser;
  errorMessage: IErrorMessage = {
    transaction: '',
    serverResponse: ''
  };
  operations: string[] = [];
  transactionForm: FormGroup;

  constructor(private formBuilder: FormBuilder,
              private transactionService: TransactionService,
              private userService: UserService) { }

  ngOnInit(): void {
    Object.keys(Operation).forEach(operation => this.operations.push(operation));

    this.currentUser = this.userService.getCurrentUser();

    this.transactionForm = this.formBuilder.group({
      operation: [this.operations[0]],
      wording: ['', Validators.required],
      amount: [1]
    }, {
      validators: withdrawalValidator(this.currentUser.balance)
    });

    this.swithValidator();
  }

  ngOnDestroy(): void {
    this.onDestroy$.next();
    this.onDestroy$.complete();
  }

  swithValidator(): void {
    let initBalance = this.currentUser.balance;

    this.transactionForm.valueChanges.pipe(
      tap(value =>  console.log(value)),
      takeUntil(this.onDestroy$),
      filter(() =>  initBalance !== this.currentUser.balance)
    ).subscribe(
      () => {
        initBalance = this.currentUser.balance;
        this.transactionForm.setValidators([withdrawalValidator(initBalance)]);
        this.transactionForm.updateValueAndValidity();
    });
  }

  handleFormError(form: FormGroup, field: string): boolean {
    return handleFormError(form, field);
  }

  submit(): void {
    this.errorMessage = {
      transaction: '',
      serverResponse: ''
    };

    if (this.transactionForm.valid) {
      const formValue: ITransaction = this.transactionForm.value;

      of(formValue)
      .pipe(
        takeUntil(this.onDestroy$),
        switchMap(value => iif(
        () => value.operation ===  Operation.DEPOSIT,
        this.transactionService.toMakeDeposit(value),
        this.transactionService.toMakeWithdrawal(value)
        ))
      ).subscribe((transaction: ITransaction) => {
        if (transaction) {
          this.errorMessage.transaction = 'true';
          this.currentUser.balance = transaction.balance;
          this.userService.setCurrentUser(this.currentUser);
        } else {
          this.errorMessage.transaction = 'false';
        }
      }, error => {
        if (error.statusCode === 400 || error.statusCode === 401) {
          this.errorMessage.serverResponse = error.message;
        } else if (error.statusText) {
          this.errorMessage.serverResponse = error.statusText + '. Try again later.';
        }
      });
    }
  }

  formatLabel(value: number): string {
    return value + '€';
  }
}
