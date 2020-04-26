import { Component, OnInit } from '@angular/core';
import { LanguageSelect } from '@akfe/core/models/language';
import { LanguageService } from '@akfe/core/services/language.service';
import { CookieService } from 'ngx-cookie-service';
import { AuthService } from '@akfe/core/services/auth.service';
import { AuthJwtService } from '@akfe/core/services/auth-jwt.service';
import { Router } from '@angular/router';

@Component({
  selector: 'ak-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  languages: LanguageSelect[] = [
    {
      key: 'en',
      label: 'English'
    },
    {
      key: 'vi',
      label: 'Tiếng Việt'
    }
  ];
  se;
  activeLanguage$ = this.languageService.language$;
  authenticationState$ = this.authService.authenticationState$;

  constructor(
    private languageService: LanguageService,
    private cookieService: CookieService,
    private authService: AuthService,
    private authJwtService: AuthJwtService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.languageService.initialize();
    this.authJwtService.initialize();

    if (this.authJwtService.getToken()) {
      this.authService.identity(true).subscribe({
        next: value => {
          console.log('Authenticated');
        },
        error: err => {
          console.log(err);
        }
      });
    }
  }

  onSelectLanguage(langKey: string) {
    this.languageService.setActiveLang(langKey);
  }

  doLogout() {
    this.authService.logout();
    this.authJwtService.logout();
    this.router.navigateByUrl('/');
  }
}
