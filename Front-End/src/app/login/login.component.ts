import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { IErrorMessage } from '../models/error-message';

import { IUser } from '../models/user';
import { AuthenticationService } from '../services/authentication/authentication.service';
import { UserService } from '../services/user/user.service';

@Component({
  selector: 'kata-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit, OnDestroy {
  errorMessage: IErrorMessage = {
    authentication: ''
  };
  form: FormGroup;
  onDestroy$ = new Subject();

  constructor(private formBuilder: FormBuilder,
              private authenticationService: AuthenticationService,
              private userService: UserService,
              private router: Router) { }

  ngOnInit(): void {
    this.clearUserIfExist();

    this.form = this.formBuilder.group({
      firstName: ['Bilbon', Validators.required],
      lastName: ['Sacquet', Validators.required],
      password: ['Test123', Validators.required],
    });
  }

  submit(): void {
    if (this.form.valid) {
      const formValue = this.form.value;
      const userForm: IUser = {
        firstName: formValue.firstName,
        lastName: formValue.lastName,
        password: formValue.password
      };

      this.authenticationService.login(userForm)
        .pipe(
          takeUntil(this.onDestroy$)
        ).subscribe((user: IUser) => {
          if (user) {
            this.userService.setCurrentUser(user);
            this.router.navigate(['customer-area']);
            this.errorMessage.authentication = '';
          }
        }, (error: any) => {
          this.userService.setCurrentUser(null);
          this.form.controls.password.reset();

          if (error.statusCode === 401) {
            this.errorMessage.authentication = error.message + ' The information entered does not appear in our data.';
          } else if (error.statusText) {
            this.errorMessage.authentication = error.statusText + '. Try again later.';
          }
        });
    }
  }

  private clearUserIfExist(): void {
    if (this.userService.getCurrentUser()) {
      this.authenticationService.logout().subscribe();
      this.userService.clearStorage();
    }
  }

  ngOnDestroy(): void {
    this.onDestroy$.next();
    this.onDestroy$.complete();
  }
}
