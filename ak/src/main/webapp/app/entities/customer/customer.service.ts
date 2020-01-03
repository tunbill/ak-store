import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICustomer } from 'app/shared/model/customer.model';

type EntityResponseType = HttpResponse<ICustomer>;
type EntityArrayResponseType = HttpResponse<ICustomer[]>;

@Injectable({ providedIn: 'root' })
export class CustomerService {
  public resourceUrl = SERVER_API_URL + 'api/customers';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/customers';

  constructor(protected http: HttpClient) {}

  create(customer: ICustomer): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(customer);
    return this.http
      .post<ICustomer>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(customer: ICustomer): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(customer);
    return this.http
      .put<ICustomer>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICustomer>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICustomer[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICustomer[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(customer: ICustomer): ICustomer {
    const copy: ICustomer = Object.assign({}, customer, {
      openBalanceDate: customer.openBalanceDate != null && customer.openBalanceDate.isValid() ? customer.openBalanceDate.toJSON() : null,
      timeCreated: customer.timeCreated != null && customer.timeCreated.isValid() ? customer.timeCreated.toJSON() : null,
      timeModified: customer.timeModified != null && customer.timeModified.isValid() ? customer.timeModified.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.openBalanceDate = res.body.openBalanceDate != null ? moment(res.body.openBalanceDate) : null;
      res.body.timeCreated = res.body.timeCreated != null ? moment(res.body.timeCreated) : null;
      res.body.timeModified = res.body.timeModified != null ? moment(res.body.timeModified) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((customer: ICustomer) => {
        customer.openBalanceDate = customer.openBalanceDate != null ? moment(customer.openBalanceDate) : null;
        customer.timeCreated = customer.timeCreated != null ? moment(customer.timeCreated) : null;
        customer.timeModified = customer.timeModified != null ? moment(customer.timeModified) : null;
      });
    }
    return res;
  }
}
