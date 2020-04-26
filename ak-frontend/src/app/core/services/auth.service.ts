import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, of } from 'rxjs';
import { catchError, shareReplay, tap } from 'rxjs/operators';
import { LanguageService } from '@akfe/core/services/language.service';
import { Account } from '../models/account.model';
import { AccountService } from '@akfe/account/services/account.service';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private userIdentity: Account;
  private authenticated = false;
  private authenticationState = new BehaviorSubject<Account | null>(null);
  private accountCache$: Observable<Account>;

  get authenticationState$(): Observable<Account | null> {
    return this.authenticationState.asObservable();
  }

  constructor(
    private languageService: LanguageService,
    private accountService: AccountService
  ) {}

  authenticate(identity: Account) {
    this.userIdentity = identity;
    this.authenticated = identity != null;
    this.authenticationState.next(this.userIdentity);
  }

  hasAnyAuthority(authorities: string[] | string): boolean {
    if (
      !(
        this.authenticated &&
        this.userIdentity &&
        this.userIdentity.authorities
      )
    ) {
      return false;
    }

    if (!Array.isArray(authorities)) {
      authorities = [authorities];
    }

    return authorities.some((authority: string) =>
      this.userIdentity.authorities.includes(authority)
    );
  }

  identity(force?: boolean): Observable<Account> {
    if (force || !this.authenticated) {
      this.accountCache$ = null;
    }

    if (!this.accountCache$) {
      this.accountCache$ = this.accountService.fetch().pipe(
        catchError(() => {
          return of(null);
        }),
        tap(account => {
          if (account) {
            this.userIdentity = account;
            this.authenticated = true;
            // After retrieve the account info, the language will be changed to
            // the user's preferred language configured in the account setting
            if (this.userIdentity.langKey) {
              const langKey =
                localStorage.getItem('currentLang') ||
                this.userIdentity.langKey;
              this.languageService.setActiveLang(langKey);
            }
          } else {
            this.userIdentity = null;
            this.authenticated = false;
          }
          this.authenticationState.next(this.userIdentity);
        }),
        shareReplay()
      );
    }
    return this.accountCache$;
  }

  isAuthenticated(): boolean {
    return this.authenticated;
  }

  isIdentityResolved(): boolean {
    return this.userIdentity != null;
  }

  logout() {
    this.authenticate(null);
    this.accountCache$ = null;
  }

  getImageUrl(): string {
    return this.isIdentityResolved() ? this.userIdentity.imageUrl : null;
  }
}
