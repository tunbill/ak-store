import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { XyzSharedModule } from 'app/shared/shared.module';
import { XyzCoreModule } from 'app/core/core.module';
import { XyzAppRoutingModule } from './app-routing.module';
import { XyzHomeModule } from './home/home.module';
import { XyzEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { JhiMainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ActiveMenuDirective } from './layouts/navbar/active-menu.directive';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    XyzSharedModule,
    XyzCoreModule,
    XyzHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    XyzEntityModule,
    XyzAppRoutingModule
  ],
  declarations: [JhiMainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, ActiveMenuDirective, FooterComponent],
  bootstrap: [JhiMainComponent]
})
export class XyzAppModule {}
