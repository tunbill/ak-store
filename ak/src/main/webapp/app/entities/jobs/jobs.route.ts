import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Jobs } from 'app/shared/model/jobs.model';
import { JobsService } from './jobs.service';
import { JobsComponent } from './jobs.component';
import { JobsDetailComponent } from './jobs-detail.component';
import { JobsUpdateComponent } from './jobs-update.component';
import { IJobs } from 'app/shared/model/jobs.model';

@Injectable({ providedIn: 'root' })
export class JobsResolve implements Resolve<IJobs> {
  constructor(private service: JobsService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IJobs> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((jobs: HttpResponse<Jobs>) => jobs.body));
    }
    return of(new Jobs());
  }
}

export const jobsRoute: Routes = [
  {
    path: '',
    component: JobsComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'akApp.jobs.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: JobsDetailComponent,
    resolve: {
      jobs: JobsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'akApp.jobs.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: JobsUpdateComponent,
    resolve: {
      jobs: JobsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'akApp.jobs.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: JobsUpdateComponent,
    resolve: {
      jobs: JobsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'akApp.jobs.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
