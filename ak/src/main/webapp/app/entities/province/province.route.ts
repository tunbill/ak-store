import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Province } from 'app/shared/model/province.model';
import { ProvinceService } from './province.service';
import { ProvinceComponent } from './province.component';
import { ProvinceDetailComponent } from './province-detail.component';
import { ProvinceUpdateComponent } from './province-update.component';
import { IProvince } from 'app/shared/model/province.model';

@Injectable({ providedIn: 'root' })
export class ProvinceResolve implements Resolve<IProvince> {
  constructor(private service: ProvinceService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProvince> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((province: HttpResponse<Province>) => province.body));
    }
    return of(new Province());
  }
}

export const provinceRoute: Routes = [
  {
    path: '',
    component: ProvinceComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'akApp.province.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ProvinceDetailComponent,
    resolve: {
      province: ProvinceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'akApp.province.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ProvinceUpdateComponent,
    resolve: {
      province: ProvinceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'akApp.province.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ProvinceUpdateComponent,
    resolve: {
      province: ProvinceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'akApp.province.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
