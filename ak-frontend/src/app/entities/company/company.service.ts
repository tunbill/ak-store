import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from '@akfe/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from '@akfe/env/environment';
import { createRequestOption } from '@akfe/shared/util/request-util';
import { ICompany } from '@akfe/shared/model/company.model';

type EntityResponseType = HttpResponse<ICompany>;
type EntityArrayResponseType = HttpResponse<ICompany[]>;

@Injectable({ providedIn: 'root' })
export class CompanyService {
  public resourceUrl = SERVER_API_URL + 'api/companies';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/companies';

  constructor(protected http: HttpClient) {}

  create(company: ICompany): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(company);
    return this.http
      .post<ICompany>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(company: ICompany): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(company);
    return this.http
      .put<ICompany>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICompany>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICompany[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICompany[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(company: ICompany): ICompany {
    const copy: ICompany = Object.assign({}, company, {
      startDate: company.startDate != null && company.startDate.isValid() ? company.startDate.format(DATE_FORMAT) : null,
      timeCreated: company.timeCreated != null && company.timeCreated.isValid() ? company.timeCreated.toJSON() : null,
      timeModified: company.timeModified != null && company.timeModified.isValid() ? company.timeModified.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.startDate = res.body.startDate != null ? moment(res.body.startDate) : null;
      res.body.timeCreated = res.body.timeCreated != null ? moment(res.body.timeCreated) : null;
      res.body.timeModified = res.body.timeModified != null ? moment(res.body.timeModified) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((company: ICompany) => {
        company.startDate = company.startDate != null ? moment(company.startDate) : null;
        company.timeCreated = company.timeCreated != null ? moment(company.timeCreated) : null;
        company.timeModified = company.timeModified != null ? moment(company.timeModified) : null;
      });
    }
    return res;
  }
}
