import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Terms } from 'app/shared/model/terms.model';
import { TermsService } from './terms.service';
import { TermsComponent } from './terms.component';
import { TermsDetailComponent } from './terms-detail.component';
import { TermsUpdateComponent } from './terms-update.component';
import { ITerms } from 'app/shared/model/terms.model';

@Injectable({ providedIn: 'root' })
export class TermsResolve implements Resolve<ITerms> {
  constructor(private service: TermsService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITerms> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((terms: HttpResponse<Terms>) => terms.body));
    }
    return of(new Terms());
  }
}

export const termsRoute: Routes = [
  {
    path: '',
    component: TermsComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'akApp.terms.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TermsDetailComponent,
    resolve: {
      terms: TermsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'akApp.terms.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TermsUpdateComponent,
    resolve: {
      terms: TermsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'akApp.terms.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TermsUpdateComponent,
    resolve: {
      terms: TermsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'akApp.terms.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
