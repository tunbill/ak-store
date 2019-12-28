import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ItemGroup } from 'app/shared/model/item-group.model';
import { ItemGroupService } from './item-group.service';
import { ItemGroupComponent } from './item-group.component';
import { ItemGroupDetailComponent } from './item-group-detail.component';
import { ItemGroupUpdateComponent } from './item-group-update.component';
import { IItemGroup } from 'app/shared/model/item-group.model';

@Injectable({ providedIn: 'root' })
export class ItemGroupResolve implements Resolve<IItemGroup> {
  constructor(private service: ItemGroupService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IItemGroup> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((itemGroup: HttpResponse<ItemGroup>) => itemGroup.body));
    }
    return of(new ItemGroup());
  }
}

export const itemGroupRoute: Routes = [
  {
    path: '',
    component: ItemGroupComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'akApp.itemGroup.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ItemGroupDetailComponent,
    resolve: {
      itemGroup: ItemGroupResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'akApp.itemGroup.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ItemGroupUpdateComponent,
    resolve: {
      itemGroup: ItemGroupResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'akApp.itemGroup.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ItemGroupUpdateComponent,
    resolve: {
      itemGroup: ItemGroupResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'akApp.itemGroup.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
