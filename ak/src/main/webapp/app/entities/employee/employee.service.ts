import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEmployee } from 'app/shared/model/employee.model';

type EntityResponseType = HttpResponse<IEmployee>;
type EntityArrayResponseType = HttpResponse<IEmployee[]>;

@Injectable({ providedIn: 'root' })
export class EmployeeService {
  public resourceUrl = SERVER_API_URL + 'api/employees';

  constructor(protected http: HttpClient) {}

  create(employee: IEmployee): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(employee);
    return this.http
      .post<IEmployee>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(employee: IEmployee): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(employee);
    return this.http
      .put<IEmployee>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IEmployee>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IEmployee[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(employee: IEmployee): IEmployee {
    const copy: IEmployee = Object.assign({}, employee, {
      birthday: employee.birthday != null && employee.birthday.isValid() ? employee.birthday.format(DATE_FORMAT) : null,
      identityDate: employee.identityDate != null && employee.identityDate.isValid() ? employee.identityDate.format(DATE_FORMAT) : null,
      timeCreated: employee.timeCreated != null && employee.timeCreated.isValid() ? employee.timeCreated.toJSON() : null,
      timeModified: employee.timeModified != null && employee.timeModified.isValid() ? employee.timeModified.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.birthday = res.body.birthday != null ? moment(res.body.birthday) : null;
      res.body.identityDate = res.body.identityDate != null ? moment(res.body.identityDate) : null;
      res.body.timeCreated = res.body.timeCreated != null ? moment(res.body.timeCreated) : null;
      res.body.timeModified = res.body.timeModified != null ? moment(res.body.timeModified) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((employee: IEmployee) => {
        employee.birthday = employee.birthday != null ? moment(employee.birthday) : null;
        employee.identityDate = employee.identityDate != null ? moment(employee.identityDate) : null;
        employee.timeCreated = employee.timeCreated != null ? moment(employee.timeCreated) : null;
        employee.timeModified = employee.timeModified != null ? moment(employee.timeModified) : null;
      });
    }
    return res;
  }
}
