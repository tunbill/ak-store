import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { AkSharedModule } from 'app/shared/shared.module';
import { AkCoreModule } from 'app/core/core.module';
import { AkAppRoutingModule } from './app-routing.module';
import { AkHomeModule } from './home/home.module';
import { AkEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { AkMainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ActiveMenuDirective } from './layouts/navbar/active-menu.directive';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    AkSharedModule,
    AkCoreModule,
    AkHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    AkEntityModule,
    AkAppRoutingModule
  ],
  declarations: [AkMainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, ActiveMenuDirective, FooterComponent],
  bootstrap: [AkMainComponent]
})
export class AkAppModule {}
