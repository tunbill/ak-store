import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { InvoiceLine } from 'app/shared/model/invoice-line.model';
import { InvoiceLineService } from './invoice-line.service';
import { InvoiceLineComponent } from './invoice-line.component';
import { InvoiceLineDetailComponent } from './invoice-line-detail.component';
import { InvoiceLineUpdateComponent } from './invoice-line-update.component';
import { IInvoiceLine } from 'app/shared/model/invoice-line.model';

@Injectable({ providedIn: 'root' })
export class InvoiceLineResolve implements Resolve<IInvoiceLine> {
  constructor(private service: InvoiceLineService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IInvoiceLine> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((invoiceLine: HttpResponse<InvoiceLine>) => invoiceLine.body));
    }
    return of(new InvoiceLine());
  }
}

export const invoiceLineRoute: Routes = [
  {
    path: '',
    component: InvoiceLineComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'akApp.invoiceLine.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: InvoiceLineDetailComponent,
    resolve: {
      invoiceLine: InvoiceLineResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'akApp.invoiceLine.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: InvoiceLineUpdateComponent,
    resolve: {
      invoiceLine: InvoiceLineResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'akApp.invoiceLine.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: InvoiceLineUpdateComponent,
    resolve: {
      invoiceLine: InvoiceLineResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'akApp.invoiceLine.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
