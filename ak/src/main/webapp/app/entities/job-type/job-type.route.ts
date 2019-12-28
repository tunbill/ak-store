import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { JobType } from 'app/shared/model/job-type.model';
import { JobTypeService } from './job-type.service';
import { JobTypeComponent } from './job-type.component';
import { JobTypeDetailComponent } from './job-type-detail.component';
import { JobTypeUpdateComponent } from './job-type-update.component';
import { IJobType } from 'app/shared/model/job-type.model';

@Injectable({ providedIn: 'root' })
export class JobTypeResolve implements Resolve<IJobType> {
  constructor(private service: JobTypeService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IJobType> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((jobType: HttpResponse<JobType>) => jobType.body));
    }
    return of(new JobType());
  }
}

export const jobTypeRoute: Routes = [
  {
    path: '',
    component: JobTypeComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'akApp.jobType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: JobTypeDetailComponent,
    resolve: {
      jobType: JobTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'akApp.jobType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: JobTypeUpdateComponent,
    resolve: {
      jobType: JobTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'akApp.jobType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: JobTypeUpdateComponent,
    resolve: {
      jobType: JobTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'akApp.jobType.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
