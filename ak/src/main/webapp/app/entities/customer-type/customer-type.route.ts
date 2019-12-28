import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { CustomerType } from 'app/shared/model/customer-type.model';
import { CustomerTypeService } from './customer-type.service';
import { CustomerTypeComponent } from './customer-type.component';
import { CustomerTypeDetailComponent } from './customer-type-detail.component';
import { CustomerTypeUpdateComponent } from './customer-type-update.component';
import { ICustomerType } from 'app/shared/model/customer-type.model';

@Injectable({ providedIn: 'root' })
export class CustomerTypeResolve implements Resolve<ICustomerType> {
  constructor(private service: CustomerTypeService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICustomerType> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((customerType: HttpResponse<CustomerType>) => customerType.body));
    }
    return of(new CustomerType());
  }
}

export const customerTypeRoute: Routes = [
  {
    path: '',
    component: CustomerTypeComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'akApp.customerType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CustomerTypeDetailComponent,
    resolve: {
      customerType: CustomerTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'akApp.customerType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CustomerTypeUpdateComponent,
    resolve: {
      customerType: CustomerTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'akApp.customerType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CustomerTypeUpdateComponent,
    resolve: {
      customerType: CustomerTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'akApp.customerType.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
