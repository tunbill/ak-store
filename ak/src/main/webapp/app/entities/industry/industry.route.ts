import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Industry } from 'app/shared/model/industry.model';
import { IndustryService } from './industry.service';
import { IndustryComponent } from './industry.component';
import { IndustryDetailComponent } from './industry-detail.component';
import { IndustryUpdateComponent } from './industry-update.component';
import { IIndustry } from 'app/shared/model/industry.model';

@Injectable({ providedIn: 'root' })
export class IndustryResolve implements Resolve<IIndustry> {
  constructor(private service: IndustryService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IIndustry> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((industry: HttpResponse<Industry>) => industry.body));
    }
    return of(new Industry());
  }
}

export const industryRoute: Routes = [
  {
    path: '',
    component: IndustryComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'akApp.industry.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: IndustryDetailComponent,
    resolve: {
      industry: IndustryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'akApp.industry.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: IndustryUpdateComponent,
    resolve: {
      industry: IndustryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'akApp.industry.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: IndustryUpdateComponent,
    resolve: {
      industry: IndustryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'akApp.industry.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
