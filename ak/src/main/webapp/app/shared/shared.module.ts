import { NgModule } from '@angular/core';
import { AkSharedLibsModule } from './shared-libs.module';
import { FindLanguageFromKeyPipe } from './language/find-language-from-key.pipe';
import { TrueAlertComponent } from './alert/alert.component';
import { TrueAlertErrorComponent } from './alert/alert-error.component';
import { TrueLoginModalComponent } from './login/login.component';
import { HasAnyAuthorityDirective } from './auth/has-any-authority.directive';

@NgModule({
  imports: [AkSharedLibsModule],
  declarations: [FindLanguageFromKeyPipe, TrueAlertComponent, TrueAlertErrorComponent, TrueLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [TrueLoginModalComponent],
  exports: [
    AkSharedLibsModule,
    FindLanguageFromKeyPipe,
    TrueAlertComponent,
    TrueAlertErrorComponent,
    TrueLoginModalComponent,
    HasAnyAuthorityDirective
  ]
})
export class AkSharedModule {}
