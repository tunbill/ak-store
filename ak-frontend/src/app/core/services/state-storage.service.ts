import { Injectable } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';

const key = 'previousUrl';

@Injectable({ providedIn: 'root' })
export class StateStorageService {
  constructor(private cookieService: CookieService) {}

  storeUrl(url: string) {
    this.cookieService.set(key, url);
  }

  getUrl() {
    return this.cookieService.get(key);
  }

  removeUrl() {
    this.cookieService.delete(key);
  }
}
