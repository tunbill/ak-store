import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IInvoice } from 'app/shared/model/invoice.model';

@Component({
  selector: 'ak-invoice-detail',
  templateUrl: './invoice-detail.component.html'
})
export class InvoiceDetailComponent implements OnInit {
  invoice: IInvoice;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ invoice }) => {
      this.invoice = invoice;
    });
  }

  previousState() {
    window.history.back();
  }
}
