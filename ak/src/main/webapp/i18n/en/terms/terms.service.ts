import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITerms } from 'app/shared/model/terms.model';

type EntityResponseType = HttpResponse<ITerms>;
type EntityArrayResponseType = HttpResponse<ITerms[]>;

@Injectable({ providedIn: 'root' })
export class TermsService {
  public resourceUrl = SERVER_API_URL + 'api/terms';

  constructor(protected http: HttpClient) {}

  create(terms: ITerms): Observable<EntityResponseType> {
    return this.http.post<ITerms>(this.resourceUrl, terms, { observe: 'response' });
  }

  update(terms: ITerms): Observable<EntityResponseType> {
    return this.http.put<ITerms>(this.resourceUrl, terms, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITerms>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITerms[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
