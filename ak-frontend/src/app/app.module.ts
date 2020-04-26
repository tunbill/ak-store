import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { IconsProviderModule } from './icons-provider.module';
import { NzLayoutModule } from 'ng-zorro-antd/layout';
import { NzMenuModule } from 'ng-zorro-antd/menu';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { en_US, NZ_I18N } from 'ng-zorro-antd/i18n';
import { registerLocaleData } from '@angular/common';
import en from '@angular/common/locales/en';
import { CoreModule } from './core/core.module';
import { LayoutModule } from '@akfe/layout/layout.module';
import { HttpClientModule } from '@angular/common/http';
import { TranslocoRootModule } from './transloco-root.module';
import { JWT_OPTIONS, JwtModule, JwtModuleOptions } from '@auth0/angular-jwt';
import { AuthJwtService } from '@akfe/core/services/auth-jwt.service';
import { AkEntityModule } from '@akfe/entities/entity.module';

registerLocaleData(en);

type Options = Omit<JwtModuleOptions, 'jwtOptionsProvider'>;
type ConfigOption = Options['config'];

export function jwtOptionsFactory(
  authJwtService: AuthJwtService
): ConfigOption {
  return {
    tokenGetter: () => {
      return authJwtService.getToken();
    },
    blacklistedRoutes: [
      /.*api\/authenticate/,
      /.*api\/register/,
      /.*api\/activate/
    ]
  };
}

@NgModule({
  declarations: [AppComponent],
  imports: [
    BrowserModule,
    IconsProviderModule,
    NzLayoutModule,
    NzMenuModule,
    ReactiveFormsModule,
    FormsModule,
    BrowserAnimationsModule,
    CoreModule,
    LayoutModule,
    AppRoutingModule,
    HttpClientModule,
    TranslocoRootModule,
    AkEntityModule,
    JwtModule.forRoot({
      jwtOptionsProvider: {
        provide: JWT_OPTIONS,
        useFactory: jwtOptionsFactory,
        deps: [AuthJwtService]
      }
    })
  ],
  providers: [{ provide: NZ_I18N, useValue: en_US }],
  bootstrap: [AppComponent]
})
export class AppModule {}
