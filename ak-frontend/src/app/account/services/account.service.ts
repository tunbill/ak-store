import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { SERVER_API_URL } from '@akfe/env/environment';
import { LoginReqBody, LoginResBody, RegisterReqBody } from '../models/account';
import { Observable } from 'rxjs';
import { Account } from '@akfe/core/models/account.model';

@Injectable({
  providedIn: 'root'
})
export class AccountService {
  constructor(private http: HttpClient) {}

  login(body: LoginReqBody) {
    return this.http.post<LoginResBody>(
      `${SERVER_API_URL}/api/authenticate`,
      body
    );
  }

  register(body: RegisterReqBody) {
    return this.http.post<void>(`${SERVER_API_URL}/api/register`, body);
  }

  activate(key: string) {
    return this.http.get(SERVER_API_URL + '/api/activate', {
      params: {
        key
      }
    });
  }

  fetch(): Observable<Account> {
    return this.http.get<Account>(SERVER_API_URL + '/api/account');
  }

  save(account: Account): Observable<Account> {
    return this.http.post<Account>(SERVER_API_URL + '/api/account', account);
  }
}
