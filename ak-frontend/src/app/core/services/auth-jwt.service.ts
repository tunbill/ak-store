import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CookieService } from 'ngx-cookie-service';
import { JwtHelperService } from '@auth0/angular-jwt';

const jwtHelperService = new JwtHelperService();

@Injectable({ providedIn: 'root' })
export class AuthJwtService {
  constructor(private http: HttpClient, private cookieService: CookieService) {}
  private accessToken: string = null;

  initialize() {
    const token = this.cookieService.get('authenticationToken');
    if (token) {
      const isExpired = jwtHelperService.isTokenExpired(token, 10);
      if (isExpired) {
        this.cookieService.delete('authenticationToken');
      } else {
        this.accessToken = token;
      }
    }
  }

  getToken() {
    return this.accessToken;
  }

  storeAuthenticationToken(jwt: string, rememberMe?: boolean) {
    if (rememberMe) {
      const expiredTime = jwtHelperService.getTokenExpirationDate(jwt);
      this.cookieService.set('authenticationToken', jwt, expiredTime);
      this.accessToken = jwt;
    } else {
      this.cookieService.set('authenticationToken', jwt);
    }
  }

  logout(): void {
    this.cookieService.delete('authenticationToken');
    this.accessToken = null;
  }
}
