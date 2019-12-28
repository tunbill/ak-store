import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IItemGroup } from 'app/shared/model/item-group.model';

@Component({
  selector: 'ak-item-group-detail',
  templateUrl: './item-group-detail.component.html'
})
export class ItemGroupDetailComponent implements OnInit {
  itemGroup: IItemGroup;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ itemGroup }) => {
      this.itemGroup = itemGroup;
    });
  }

  previousState() {
    window.history.back();
  }
}
