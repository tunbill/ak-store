import { Injectable } from '@angular/core';
import { TranslocoService } from '@ngneat/transloco';
import { Observable } from 'rxjs';
import { startWith } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class LanguageService {
  /**
   * Language as Observable
   * Emit value whenever Language changed
   * Always start with current active language
   */
  language$: Observable<string>;

  constructor(private translocoService: TranslocoService) {
    const activeLang = this.translocoService.getActiveLang();
    this.language$ = this.translocoService.langChanges$.pipe(
      startWith(activeLang)
    );
  }

  initialize() {
    const langKey = localStorage.getItem('currentLang');
    if (langKey) {
      this.translocoService.setActiveLang(langKey);
    }
    this.translocoService.selectTranslation().subscribe({
      next: () => {
        console.log('Load language done!');
      },
      error: err => {
        console.log('Load language fail!');
        console.error(err);
      }
    });
  }

  setActiveLang(lang: string) {
    this.translocoService.setActiveLang(lang);
    localStorage.setItem('currentLang', lang);
  }
}
