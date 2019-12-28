import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IIndustry } from 'app/shared/model/industry.model';
import { IndustryService } from './industry.service';
import { IndustryDeleteDialogComponent } from './industry-delete-dialog.component';

@Component({
  selector: 'ak-industry',
  templateUrl: './industry.component.html'
})
export class IndustryComponent implements OnInit, OnDestroy {
  industries: IIndustry[];
  eventSubscriber: Subscription;

  constructor(protected industryService: IndustryService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll() {
    this.industryService.query().subscribe((res: HttpResponse<IIndustry[]>) => {
      this.industries = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInIndustries();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IIndustry) {
    return item.id;
  }

  registerChangeInIndustries() {
    this.eventSubscriber = this.eventManager.subscribe('industryListModification', () => this.loadAll());
  }

  delete(industry: IIndustry) {
    const modalRef = this.modalService.open(IndustryDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.industry = industry;
  }
}
