import { Component, OnInit } from '@angular/core';
import { AuthService } from '@akfe/core/services/auth.service';
import { filter } from 'rxjs/operators';

@Component({
  selector: 'ak-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {
  user$ = this.authService.authenticationState$.pipe(filter(x => Boolean(x)));
  constructor(private authService: AuthService) {}

  ngOnInit(): void {}
}
