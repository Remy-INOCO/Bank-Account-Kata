import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AuthenticationService } from '../services/authentication/authentication.service';
import { LogoutResolver } from '../services/authentication/logout-resolver.service';

@Component({
  selector: 'kata-logout',
  template: `<div></div>`
})
export class LogoutComponent implements OnInit {

  constructor(private route: ActivatedRoute,
              private authenticationService: AuthenticationService) { }

  ngOnInit(): void {
    if (this.route.snapshot.data['isDisconnected']) {
        this.authenticationService.logoutComplete()
    }
  }
}
