import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AccountService } from '@akfe/account/services/account.service';
import { RegisterReqBody } from '@akfe/account/models/account';
import { Router } from '@angular/router';
import { NzNotificationService } from 'ng-zorro-antd/notification';
import { TranslocoService } from '@ngneat/transloco';
import { HttpErrorResponse } from '@angular/common/http';

const errorKeys = ['error.fail', 'error.userexists', 'error.emailexists'];

@Component({
  selector: 'ak-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {
  validateForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private accountService: AccountService,
    private router: Router,
    private notificationService: NzNotificationService,
    private translocoService: TranslocoService
  ) {}

  ngOnInit(): void {
    this.validateForm = this.fb.group({
      email: [null, [Validators.required, Validators.email]],
      username: [null, [Validators.required, Validators.minLength(4)]],
      password: [null, [Validators.required, Validators.minLength(4)]]
    });
  }

  submitForm(): void {
    if (this.validateForm.valid) {
      const formValue = this.validateForm.value;
      const body: RegisterReqBody = {
        email: formValue.email,
        login: formValue.username,
        password: formValue.password
      };
      this.accountService.register(body).subscribe({
        next: value => {
          const successMsg = this.translocoService.translate(
            'register.messages.success'
          );
          this.notificationService.success('', successMsg);
          this.router.navigateByUrl('/');
        },
        error: (err: HttpErrorResponse) => {
          let errorKey = 'error.' + err.error.errorKey;
          if (!errorKeys.includes(errorKey)) {
            errorKey = errorKeys[0];
          }
          const translateKey = `register.messages.${errorKey}`;
          const errorMsg = this.translocoService.translate(translateKey);
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
