import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Observable, Subscription } from 'rxjs';
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
  currentUser: IUser;
  errorMessage = {
    transaction: '',
    amountAboveBalance: true
  };
  operations: string[] = [];
  transactionForm: FormGroup;
  depositTransaction$: Subscription;
  withdrawalTransaction$: Subscription;

  constructor(private formBuilder: FormBuilder,
              private transactionService: TransactionService,
              private userService: UserService) { }

  ngOnInit(): void {
    Object.keys(Operation).forEach(operation => this.operations.push(operation));

    this.transactionForm = this.formBuilder.group({
      operation: [this.operations[0]],
      wording: ['', Validators.required],
      amount: [1]
    });

    this.currentUser = this.userService.getCurrentUser();
  }

  ngOnDestroy(): void {
    if (this.depositTransaction$) {
      this.depositTransaction$.unsubscribe();
    }
    if (this.withdrawalTransaction$) {
      this.withdrawalTransaction$.unsubscribe();
    }
  }

  handleFormError(form: FormGroup, field: string): boolean {
    return handleFormError(form, field);
  }

  submit(): void {
    this.errorMessage.amountAboveBalance = true;
    this.errorMessage.transaction = '';

    if (this.transactionForm.valid) {
      const formValue: ITransaction = this.transactionForm.value;

      switch (formValue.operation) {
        case Operation.DEPOSIT:
          this.depositTransaction$ = this.handleTransaction(this.transactionService.toMakeDeposit(formValue));
          break;

        case Operation.WITHDRAWAL:
          if (formValue.amount > this.currentUser.balance) {
            this.errorMessage.amountAboveBalance = false;
            break;
          }
          this.withdrawalTransaction$ = this.handleTransaction(this.transactionService.toMakeWithdrawal(formValue));
          break;

        default:
          break;
      }
    }
  }

  private handleTransaction(action: Observable<ITransaction>): Subscription {
    return action.subscribe((transaction: ITransaction) => {
      if (transaction) {
        this.errorMessage.transaction = 'true';
        this.currentUser.balance = transaction.balance;
        this.userService.setCurrentUser(this.currentUser);
      } else {
        this.errorMessage.transaction = 'false';
      }
    });
  }

  formatLabel(value: number): string {
    return value + '€';
  }
}
