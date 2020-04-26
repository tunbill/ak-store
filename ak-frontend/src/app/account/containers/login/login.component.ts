import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { LoginReqBody } from '@akfe/account/models/account';
import { AccountService } from '@akfe/account/services/account.service';
import { Router } from '@angular/router';
import { NzNotificationService } from 'ng-zorro-antd/notification';
import { TranslocoService } from '@ngneat/transloco';
import { HttpErrorResponse } from '@angular/common/http';
import { AuthJwtService } from '@akfe/core/services/auth-jwt.service';
import { first, mergeMap, tap } from 'rxjs/operators';
import { AuthService } from '@akfe/core/services/auth.service';
import { StateStorageService } from '@akfe/core/services/state-storage.service';

@Component({
  selector: 'ak-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  validateForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private accountService: AccountService,
    private router: Router,
    private notificationService: NzNotificationService,
    private authJwtService: AuthJwtService,
    private authService: AuthService,
    private translocoService: TranslocoService,
    private stateStorageService: StateStorageService
  ) {}

  ngOnInit(): void {
    this.validateForm = this.fb.group({
      username: [null, [Validators.required, Validators.minLength(4)]],
      password: [null, [Validators.required, Validators.minLength(4)]],
      rememberMe: [true]
    });
    this.authService.authenticationState$.pipe(first()).subscribe(user => {
      if (user) {
        this.router.navigateByUrl('/');
      }
    });
  }

  submitForm(): void {
    if (this.validateForm.valid) {
      const formValue = this.validateForm.value;
      const body: LoginReqBody = {
        username: formValue.username,
        password: formValue.password,
        rememberMe: formValue.rememberMe
      };
      this.accountService
        .login(body)
        .pipe(
          tap(({ id_token }) => {
            this.authJwtService.storeAuthenticationToken(
              id_token,
              body.rememberMe
            );
          }),
          mergeMap(({ id_token }) => {
            return this.authService.identity(true);
          })
        )
        .subscribe({
          next: value => {
            console.log(value);
            const navigateUrl = this.stateStorageService.getUrl() || '/';
            this.stateStorageService.removeUrl();
            this.router.navigateByUrl(navigateUrl);
          },
          error: (err: HttpErrorResponse) => {
            let errorMsg = err.error.detail;
            errorMsg =
              errorMsg ||
              this.translocoService.translate(
                'login.messages.error.authentication'
              );
            this.notificationService.error(null, errorMsg);
            console.log(err);
          }
        });
    } else {
      this.validateForm.markAllAsTouched();
      Object.entries(this.validateForm.controls).forEach(([key, control]) => {
        control.markAsDirty();
        control.updateValueAndValidity();
      });
    }
  }
}
