import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IInvoiceLine, InvoiceLine } from 'app/shared/model/invoice-line.model';
import { InvoiceLineService } from './invoice-line.service';
import { IInvoice } from 'app/shared/model/invoice.model';
import { InvoiceService } from 'app/entities/invoice/invoice.service';
import { IItem } from 'app/shared/model/item.model';
import { ItemService } from 'app/entities/item/item.service';

@Component({
  selector: 'ak-invoice-line-update',
  templateUrl: './invoice-line-update.component.html'
})
export class InvoiceLineUpdateComponent implements OnInit {
  isSaving: boolean;

  invoices: IInvoice[];

  items: IItem[];

  editForm = this.fb.group({
    id: [],
    companyId: [],
    displayOrder: [],
    itemName: [null, [Validators.maxLength(100)]],
    unitName: [null, [Validators.maxLength(10)]],
    quantity: [],
    rate: [],
    amount: [],
    discountPct: [],
    accountNumber: [null, [Validators.maxLength(10)]],
    status: [],
    invoice: [],
    item: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected invoiceLineService: InvoiceLineService,
    protected invoiceService: InvoiceService,
    protected itemService: ItemService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ invoiceLine }) => {
      this.updateForm(invoiceLine);
    });
    this.invoiceService
      .query()
      .subscribe((res: HttpResponse<IInvoice[]>) => (this.invoices = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.itemService
      .query()
      .subscribe((res: HttpResponse<IItem[]>) => (this.items = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(invoiceLine: IInvoiceLine) {
    this.editForm.patchValue({
      id: invoiceLine.id,
      companyId: invoiceLine.companyId,
      displayOrder: invoiceLine.displayOrder,
      itemName: invoiceLine.itemName,
      unitName: invoiceLine.unitName,
      quantity: invoiceLine.quantity,
      rate: invoiceLine.rate,
      amount: invoiceLine.amount,
      discountPct: invoiceLine.discountPct,
      accountNumber: invoiceLine.accountNumber,
      status: invoiceLine.status,
      invoice: invoiceLine.invoice,
      item: invoiceLine.item
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const invoiceLine = this.createFromForm();
    if (invoiceLine.id !== undefined) {
      this.subscribeToSaveResponse(this.invoiceLineService.update(invoiceLine));
    } else {
      this.subscribeToSaveResponse(this.invoiceLineService.create(invoiceLine));
    }
  }

  private createFromForm(): IInvoiceLine {
    return {
      ...new InvoiceLine(),
      id: this.editForm.get(['id']).value,
      companyId: this.editForm.get(['companyId']).value,
      displayOrder: this.editForm.get(['displayOrder']).value,
      itemName: this.editForm.get(['itemName']).value,
      unitName: this.editForm.get(['unitName']).value,
      quantity: this.editForm.get(['quantity']).value,
      rate: this.editForm.get(['rate']).value,
      amount: this.editForm.get(['amount']).value,
      discountPct: this.editForm.get(['discountPct']).value,
      accountNumber: this.editForm.get(['accountNumber']).value,
      status: this.editForm.get(['status']).value,
      invoice: this.editForm.get(['invoice']).value,
      item: this.editForm.get(['item']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInvoiceLine>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackInvoiceById(index: number, item: IInvoice) {
    return item.id;
  }

  trackItemById(index: number, item: IItem) {
    return item.id;
  }
}
