import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';

import { IUser } from '../models/user';
import { AuthenticationService } from '../services/authentication/authentication.service';
import { UserService } from '../services/user/user.service';

@Component({
  selector: 'kata-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit, OnDestroy {
  errorMessage: string;
  form: FormGroup;
  authentication$: Subscription;

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

      this.authentication$ = this.authenticationService.login(userForm).subscribe((user: IUser) => {
        if (user) {
          this.userService.setCurrentUser(user);
          this.router.navigate(['customer-area']);
          this.errorMessage = '';
        }
      }, (error: any) => {
        this.userService.setCurrentUser(null);
        this.form.controls.password.reset();

        if (error.statusCode === 401) {
          this.errorMessage = error.message + ' The information entered does not appear in our data.';
        } else if (error.statusText) {
          this.errorMessage = error.statusText + '. Try again later.';
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
    if (this.authentication$) {
      this.authentication$.unsubscribe();
    }
  }
}
