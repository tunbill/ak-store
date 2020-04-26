import { Injectable } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivate,
  CanActivateChild,
  Router,
  RouterStateSnapshot,
  UrlTree
} from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { AuthService } from '@akfe/core/services/auth.service';
import { StateStorageService } from '@akfe/core/services/state-storage.service';

@Injectable({ providedIn: 'root' })
export class UserRouteAccessGuard implements CanActivate, CanActivateChild {
  constructor(
    private router: Router,
    private accountService: AuthService,
    private stateStorageService: StateStorageService
  ) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<boolean> {
    const authorities = route.data['authorities'];
    // We need to call the checkLogin / and so the accountService.identity() function, to ensure,
    // that the client has a principal too, if they already logged in by the server.
    // This could happen on a page refresh.
    return this.checkLogin(authorities, state.url);
  }

  canActivateChild(
    childRoute: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ):
    | Observable<boolean | UrlTree>
    | Promise<boolean | UrlTree>
    | boolean
    | UrlTree {
    const authorities = childRoute.data['authorities'];
    return this.checkLogin(authorities, state.url);
  }

  checkLogin(authorities: string[], url: string): Observable<boolean> {
    return this.accountService.identity().pipe(
      map(account => {
        if (account) {
          if (!authorities || authorities.length === 0) {
            return true;
          }
          const hasAnyAuthority = this.accountService.hasAnyAuthority(
            authorities
          );
          if (hasAnyAuthority) {
            return true;
          }
          console.error(
            'User has not any of required authorities: ',
            authorities
          );
          this.router.navigate(['/access-denied']);
          return false;
        }

        this.stateStorageService.storeUrl(url);
        this.router.navigate(['/account/login']);
        return false;
      })
    );
  }
}
