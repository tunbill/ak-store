import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IItemGroup } from 'app/shared/model/item-group.model';

type EntityResponseType = HttpResponse<IItemGroup>;
type EntityArrayResponseType = HttpResponse<IItemGroup[]>;

@Injectable({ providedIn: 'root' })
export class ItemGroupService {
  public resourceUrl = SERVER_API_URL + 'api/item-groups';

  constructor(protected http: HttpClient) {}

  create(itemGroup: IItemGroup): Observable<EntityResponseType> {
    return this.http.post<IItemGroup>(this.resourceUrl, itemGroup, { observe: 'response' });
  }

  update(itemGroup: IItemGroup): Observable<EntityResponseType> {
    return this.http.put<IItemGroup>(this.resourceUrl, itemGroup, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IItemGroup>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IItemGroup[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
