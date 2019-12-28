import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IItemGroup } from 'app/shared/model/item-group.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { ItemGroupService } from './item-group.service';
import { ItemGroupDeleteDialogComponent } from './item-group-delete-dialog.component';

@Component({
  selector: 'ak-item-group',
  templateUrl: './item-group.component.html'
})
export class ItemGroupComponent implements OnInit, OnDestroy {
  itemGroups: IItemGroup[];
  eventSubscriber: Subscription;
  itemsPerPage: number;
  links: any;
  page: any;
  predicate: any;
  reverse: any;
  totalItems: number;

  constructor(
    protected itemGroupService: ItemGroupService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.itemGroups = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.reverse = true;
  }

  loadAll() {
    this.itemGroupService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IItemGroup[]>) => this.paginateItemGroups(res.body, res.headers));
  }

  reset() {
    this.page = 0;
    this.itemGroups = [];
    this.loadAll();
  }

  loadPage(page) {
    this.page = page;
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInItemGroups();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IItemGroup) {
    return item.id;
  }

  registerChangeInItemGroups() {
    this.eventSubscriber = this.eventManager.subscribe('itemGroupListModification', () => this.reset());
  }

  delete(itemGroup: IItemGroup) {
    const modalRef = this.modalService.open(ItemGroupDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.itemGroup = itemGroup;
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateItemGroups(data: IItemGroup[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    for (let i = 0; i < data.length; i++) {
      this.itemGroups.push(data[i]);
    }
  }
}
