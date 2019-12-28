import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IInvoice, Invoice } from 'app/shared/model/invoice.model';
import { InvoiceService } from './invoice.service';
import { ICustomer } from 'app/shared/model/customer.model';
import { CustomerService } from 'app/entities/customer/customer.service';
import { ITerms } from 'app/shared/model/terms.model';
import { TermsService } from 'app/entities/terms/terms.service';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeService } from 'app/entities/employee/employee.service';
import { ICompany } from 'app/shared/model/company.model';
import { CompanyService } from 'app/entities/company/company.service';

@Component({
  selector: 'ak-invoice-update',
  templateUrl: './invoice-update.component.html'
})
export class InvoiceUpdateComponent implements OnInit {
  isSaving: boolean;

  customers: ICustomer[];

  terms: ITerms[];

  employees: IEmployee[];

  companies: ICompany[];
  invoiceDateDp: any;
  dueDateDp: any;

  editForm = this.fb.group({
    id: [],
    invoiceNo: [null, [Validators.maxLength(20)]],
    invoiceDate: [],
    dueDate: [],
    billingAddress: [],
    accountNumber: [null, [Validators.maxLength(10)]],
    poNumber: [null, [Validators.maxLength(20)]],
    notes: [],
    productTotal: [],
    vatTotal: [],
    discountTotal: [],
    total: [],
    status: [],
    timeCreated: [],
    timeModified: [],
    userIdCreated: [],
    userIdModified: [],
    customer: [],
    terms: [],
    employee: [],
    company: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected invoiceService: InvoiceService,
    protected customerService: CustomerService,
    protected termsService: TermsService,
    protected employeeService: EmployeeService,
    protected companyService: CompanyService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ invoice }) => {
      this.updateForm(invoice);
    });
    this.customerService
      .query()
      .subscribe((res: HttpResponse<ICustomer[]>) => (this.customers = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.termsService
      .query()
      .subscribe((res: HttpResponse<ITerms[]>) => (this.terms = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.employeeService
      .query()
      .subscribe((res: HttpResponse<IEmployee[]>) => (this.employees = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.companyService
      .query()
      .subscribe((res: HttpResponse<ICompany[]>) => (this.companies = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(invoice: IInvoice) {
    this.editForm.patchValue({
      id: invoice.id,
      invoiceNo: invoice.invoiceNo,
      invoiceDate: invoice.invoiceDate,
      dueDate: invoice.dueDate,
      billingAddress: invoice.billingAddress,
      accountNumber: invoice.accountNumber,
      poNumber: invoice.poNumber,
      notes: invoice.notes,
      productTotal: invoice.productTotal,
      vatTotal: invoice.vatTotal,
      discountTotal: invoice.discountTotal,
      total: invoice.total,
      status: invoice.status,
      timeCreated: invoice.timeCreated != null ? invoice.timeCreated.format(DATE_TIME_FORMAT) : null,
      timeModified: invoice.timeModified != null ? invoice.timeModified.format(DATE_TIME_FORMAT) : null,
      userIdCreated: invoice.userIdCreated,
      userIdModified: invoice.userIdModified,
      customer: invoice.customer,
      terms: invoice.terms,
      employee: invoice.employee,
      company: invoice.company
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const invoice = this.createFromForm();
    if (invoice.id !== undefined) {
      this.subscribeToSaveResponse(this.invoiceService.update(invoice));
    } else {
      this.subscribeToSaveResponse(this.invoiceService.create(invoice));
    }
  }

  private createFromForm(): IInvoice {
    return {
      ...new Invoice(),
      id: this.editForm.get(['id']).value,
      invoiceNo: this.editForm.get(['invoiceNo']).value,
      invoiceDate: this.editForm.get(['invoiceDate']).value,
      dueDate: this.editForm.get(['dueDate']).value,
      billingAddress: this.editForm.get(['billingAddress']).value,
      accountNumber: this.editForm.get(['accountNumber']).value,
      poNumber: this.editForm.get(['poNumber']).value,
      notes: this.editForm.get(['notes']).value,
      productTotal: this.editForm.get(['productTotal']).value,
      vatTotal: this.editForm.get(['vatTotal']).value,
      discountTotal: this.editForm.get(['discountTotal']).value,
      total: this.editForm.get(['total']).value,
      status: this.editForm.get(['status']).value,
      timeCreated:
        this.editForm.get(['timeCreated']).value != null ? moment(this.editForm.get(['timeCreated']).value, DATE_TIME_FORMAT) : undefined,
      timeModified:
        this.editForm.get(['timeModified']).value != null ? moment(this.editForm.get(['timeModified']).value, DATE_TIME_FORMAT) : undefined,
      userIdCreated: this.editForm.get(['userIdCreated']).value,
      userIdModified: this.editForm.get(['userIdModified']).value,
      customer: this.editForm.get(['customer']).value,
      terms: this.editForm.get(['terms']).value,
      employee: this.editForm.get(['employee']).value,
      company: this.editForm.get(['company']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInvoice>>) {
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

  trackCustomerById(index: number, item: ICustomer) {
    return item.id;
  }

  trackTermsById(index: number, item: ITerms) {
    return item.id;
  }

  trackEmployeeById(index: number, item: IEmployee) {
    return item.id;
  }

  trackCompanyById(index: number, item: ICompany) {
    return item.id;
  }
}
