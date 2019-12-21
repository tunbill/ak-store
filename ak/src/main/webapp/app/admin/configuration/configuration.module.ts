import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { AkSharedModule } from 'app/shared/shared.module';

import { AkConfigurationComponent } from './configuration.component';

import { configurationRoute } from './configuration.route';

@NgModule({
  imports: [AkSharedModule, RouterModule.forChild([configurationRoute])],
  declarations: [AkConfigurationComponent]
})
export class ConfigurationModule {}
