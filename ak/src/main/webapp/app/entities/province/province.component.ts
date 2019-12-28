import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IProvince } from 'app/shared/model/province.model';
import { ProvinceService } from './province.service';
import { ProvinceDeleteDialogComponent } from './province-delete-dialog.component';

@Component({
  selector: 'ak-province',
  templateUrl: './province.component.html'
})
export class ProvinceComponent implements OnInit, OnDestroy {
  provinces: IProvince[];
  eventSubscriber: Subscription;

  constructor(protected provinceService: ProvinceService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll() {
    this.provinceService.query().subscribe((res: HttpResponse<IProvince[]>) => {
      this.provinces = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInProvinces();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IProvince) {
    return item.id;
  }

  registerChangeInProvinces() {
    this.eventSubscriber = this.eventManager.subscribe('provinceListModification', () => this.loadAll());
  }

  delete(province: IProvince) {
    const modalRef = this.modalService.open(ProvinceDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.province = province;
  }
}
