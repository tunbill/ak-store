import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AkSharedModule } from 'app/shared/shared.module';
import { ItemGroupComponent } from './item-group.component';
import { ItemGroupDetailComponent } from './item-group-detail.component';
import { ItemGroupUpdateComponent } from './item-group-update.component';
import { ItemGroupDeleteDialogComponent } from './item-group-delete-dialog.component';
import { itemGroupRoute } from './item-group.route';

@NgModule({
  imports: [AkSharedModule, RouterModule.forChild(itemGroupRoute)],
  declarations: [ItemGroupComponent, ItemGroupDetailComponent, ItemGroupUpdateComponent, ItemGroupDeleteDialogComponent],
  entryComponents: [ItemGroupDeleteDialogComponent]
})
export class AkItemGroupModule {}
