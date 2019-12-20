import { NgModule } from '@angular/core';
import { AkSharedLibsModule } from './shared-libs.module';
import { FindLanguageFromKeyPipe } from './language/find-language-from-key.pipe';
import { AkAlertComponent } from './alert/alert.component';
import { AkAlertErrorComponent } from './alert/alert-error.component';
import { AkLoginModalComponent } from './login/login.component';
import { HasAnyAuthorityDirective } from './auth/has-any-authority.directive';

@NgModule({
  imports: [AkSharedLibsModule],
  declarations: [FindLanguageFromKeyPipe, AkAlertComponent, AkAlertErrorComponent, AkLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [AkLoginModalComponent],
  exports: [
    AkSharedLibsModule,
    FindLanguageFromKeyPipe,
    AkAlertComponent,
    AkAlertErrorComponent,
    AkLoginModalComponent,
    HasAnyAuthorityDirective
  ]
})
export class AkSharedModule {}
