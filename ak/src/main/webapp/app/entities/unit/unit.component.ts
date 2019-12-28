import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IUnit } from 'app/shared/model/unit.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { UnitService } from './unit.service';
import { UnitDeleteDialogComponent } from './unit-delete-dialog.component';

@Component({
  selector: 'ak-unit',
  templateUrl: './unit.component.html'
})
export class UnitComponent implements OnInit, OnDestroy {
  units: IUnit[];
  eventSubscriber: Subscription;
  itemsPerPage: number;
  links: any;
  page: any;
  predicate: any;
  reverse: any;
  totalItems: number;

  constructor(
    protected unitService: UnitService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.units = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.reverse = true;
  }

  loadAll() {
    this.unitService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IUnit[]>) => this.paginateUnits(res.body, res.headers));
  }

  reset() {
    this.page = 0;
    this.units = [];
    this.loadAll();
  }

  loadPage(page) {
    this.page = page;
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInUnits();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IUnit) {
    return item.id;
  }

  registerChangeInUnits() {
    this.eventSubscriber = this.eventManager.subscribe('unitListModification', () => this.reset());
  }

  delete(unit: IUnit) {
    const modalRef = this.modalService.open(UnitDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.unit = unit;
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateUnits(data: IUnit[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    for (let i = 0; i < data.length; i++) {
      this.units.push(data[i]);
    }
  }
}
