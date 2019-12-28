import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IJobs } from 'app/shared/model/jobs.model';

type EntityResponseType = HttpResponse<IJobs>;
type EntityArrayResponseType = HttpResponse<IJobs[]>;

@Injectable({ providedIn: 'root' })
export class JobsService {
  public resourceUrl = SERVER_API_URL + 'api/jobs';

  constructor(protected http: HttpClient) {}

  create(jobs: IJobs): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(jobs);
    return this.http
      .post<IJobs>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(jobs: IJobs): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(jobs);
    return this.http
      .put<IJobs>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IJobs>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IJobs[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(jobs: IJobs): IJobs {
    const copy: IJobs = Object.assign({}, jobs, {
      startDate: jobs.startDate != null && jobs.startDate.isValid() ? jobs.startDate.format(DATE_FORMAT) : null,
      endDate: jobs.endDate != null && jobs.endDate.isValid() ? jobs.endDate.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.startDate = res.body.startDate != null ? moment(res.body.startDate) : null;
      res.body.endDate = res.body.endDate != null ? moment(res.body.endDate) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((jobs: IJobs) => {
        jobs.startDate = jobs.startDate != null ? moment(jobs.startDate) : null;
        jobs.endDate = jobs.endDate != null ? moment(jobs.endDate) : null;
      });
    }
    return res;
  }
}
