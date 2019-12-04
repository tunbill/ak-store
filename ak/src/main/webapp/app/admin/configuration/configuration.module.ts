import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { AkSharedModule } from 'app/shared/shared.module';

import { TrueConfigurationComponent } from './configuration.component';

import { configurationRoute } from './configuration.route';

@NgModule({
  imports: [AkSharedModule, RouterModule.forChild([configurationRoute])],
  declarations: [TrueConfigurationComponent]
})
export class ConfigurationModule {}
