import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Unit } from 'app/shared/model/unit.model';
import { UnitService } from './unit.service';
import { UnitComponent } from './unit.component';
import { UnitDetailComponent } from './unit-detail.component';
import { UnitUpdateComponent } from './unit-update.component';
import { IUnit } from 'app/shared/model/unit.model';

@Injectable({ providedIn: 'root' })
export class UnitResolve implements Resolve<IUnit> {
  constructor(private service: UnitService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUnit> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((unit: HttpResponse<Unit>) => unit.body));
    }
    return of(new Unit());
  }
}

export const unitRoute: Routes = [
  {
    path: '',
    component: UnitComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'akApp.unit.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: UnitDetailComponent,
    resolve: {
      unit: UnitResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'akApp.unit.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: UnitUpdateComponent,
    resolve: {
      unit: UnitResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'akApp.unit.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: UnitUpdateComponent,
    resolve: {
      unit: UnitResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'akApp.unit.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
