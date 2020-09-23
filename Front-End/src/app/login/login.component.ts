import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Subscription } from 'rxjs';
import { User } from '../models/user';
import { AuthenticationService } from '../services/authentication/authentication.service';
import { UserService } from '../services/user/user.service';

@Component({
  selector: 'kata-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit, OnDestroy {
  error: string
  form: FormGroup
  authentication$: Subscription

  constructor(private formBuilder: FormBuilder,
              private authenticationService: AuthenticationService,
              private userService: UserService) { }

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      firstName: ['test', Validators.required],
      lastName: ['yolo', Validators.required],
      password: ['sfsrf', Validators.required], 
    });
  }

  submit() {
    if (this.form.valid) {
      const formValue = this.form.value
      const user: User = {
        firstName: formValue.firstName,
        lastName: formValue.lastName,
        password: formValue.password
      }
      this.authentication$ = this.authenticationService.login(user).subscribe()
    }
  }
  ngOnDestroy(): void {
    this.authentication$.unsubscribe()
  }
}
