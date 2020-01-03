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
import { ICustomer, Customer } from 'app/shared/model/customer.model';
import { CustomerService } from './customer.service';
import { ICustomerType } from 'app/shared/model/customer-type.model';
import { CustomerTypeService } from 'app/entities/customer-type/customer-type.service';
import { ITerms } from 'app/shared/model/terms.model';
import { TermsService } from 'app/entities/terms/terms.service';

@Component({
  selector: 'ak-customer-update',
  templateUrl: './customer-update.component.html'
})
export class CustomerUpdateComponent implements OnInit {
  isSaving: boolean;

  customertypes: ICustomerType[];

  terms: ITerms[];

  editForm = this.fb.group({
    id: [],
    companyId: [],
    isVendor: [],
    vendorId: [],
    code: [null, [Validators.required, Validators.maxLength(20)]],
    companyName: [null, [Validators.required, Validators.maxLength(150)]],
    address: [null, [Validators.maxLength(300)]],
    phone: [null, [Validators.maxLength(30)]],
    mobile: [null, [Validators.maxLength(30)]],
    fax: [null, [Validators.maxLength(30)]],
    email: [null, [Validators.maxLength(100), Validators.pattern('^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$')]],
    website: [null, [Validators.maxLength(150)]],
    taxCode: [null, [Validators.maxLength(30)]],
    accountNumber: [null, [Validators.maxLength(20)]],
    bankAccount: [null, [Validators.maxLength(20)]],
    bankName: [null, [Validators.maxLength(150)]],
    balance: [],
    totalBalance: [],
    openBalance: [],
    openBalanceDate: [],
    creditLimit: [],
    notes: [null, [Validators.maxLength(200)]],
    contactName: [null, [Validators.maxLength(50)]],
    contactMobile: [null, [Validators.maxLength(50)]],
    contactEmail: [null, [Validators.maxLength(100), Validators.pattern('^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$')]],
    isActive: [],
    timeCreated: [],
    timeModified: [],
    userIdCreated: [],
    userIdModified: [],
    customerType: [],
    terms: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected customerService: CustomerService,
    protected customerTypeService: CustomerTypeService,
    protected termsService: TermsService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ customer }) => {
      this.updateForm(customer);
    });
    this.customerTypeService
      .query()
      .subscribe(
        (res: HttpResponse<ICustomerType[]>) => (this.customertypes = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.termsService
      .query()
      .subscribe((res: HttpResponse<ITerms[]>) => (this.terms = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(customer: ICustomer) {
    this.editForm.patchValue({
      id: customer.id,
      companyId: customer.companyId,
      isVendor: customer.isVendor,
      vendorId: customer.vendorId,
      code: customer.code,
      companyName: customer.companyName,
      address: customer.address,
      phone: customer.phone,
      mobile: customer.mobile,
      fax: customer.fax,
      email: customer.email,
      website: customer.website,
      taxCode: customer.taxCode,
      accountNumber: customer.accountNumber,
      bankAccount: customer.bankAccount,
      bankName: customer.bankName,
      balance: customer.balance,
      totalBalance: customer.totalBalance,
      openBalance: customer.openBalance,
      openBalanceDate: customer.openBalanceDate != null ? customer.openBalanceDate.format(DATE_TIME_FORMAT) : null,
      creditLimit: customer.creditLimit,
      notes: customer.notes,
      contactName: customer.contactName,
      contactMobile: customer.contactMobile,
      contactEmail: customer.contactEmail,
      isActive: customer.isActive,
      timeCreated: customer.timeCreated != null ? customer.timeCreated.format(DATE_TIME_FORMAT) : null,
      timeModified: customer.timeModified != null ? customer.timeModified.format(DATE_TIME_FORMAT) : null,
      userIdCreated: customer.userIdCreated,
      userIdModified: customer.userIdModified,
      customerType: customer.customerType,
      terms: customer.terms
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const customer = this.createFromForm();
    if (customer.id !== undefined) {
      this.subscribeToSaveResponse(this.customerService.update(customer));
    } else {
      this.subscribeToSaveResponse(this.customerService.create(customer));
    }
  }

  private createFromForm(): ICustomer {
    return {
      ...new Customer(),
      id: this.editForm.get(['id']).value,
      companyId: this.editForm.get(['companyId']).value,
      isVendor: this.editForm.get(['isVendor']).value,
      vendorId: this.editForm.get(['vendorId']).value,
      code: this.editForm.get(['code']).value,
      companyName: this.editForm.get(['companyName']).value,
      address: this.editForm.get(['address']).value,
      phone: this.editForm.get(['phone']).value,
      mobile: this.editForm.get(['mobile']).value,
      fax: this.editForm.get(['fax']).value,
      email: this.editForm.get(['email']).value,
      website: this.editForm.get(['website']).value,
      taxCode: this.editForm.get(['taxCode']).value,
      accountNumber: this.editForm.get(['accountNumber']).value,
      bankAccount: this.editForm.get(['bankAccount']).value,
      bankName: this.editForm.get(['bankName']).value,
      balance: this.editForm.get(['balance']).value,
      totalBalance: this.editForm.get(['totalBalance']).value,
      openBalance: this.editForm.get(['openBalance']).value,
      openBalanceDate:
        this.editForm.get(['openBalanceDate']).value != null
          ? moment(this.editForm.get(['openBalanceDate']).value, DATE_TIME_FORMAT)
          : undefined,
      creditLimit: this.editForm.get(['creditLimit']).value,
      notes: this.editForm.get(['notes']).value,
      contactName: this.editForm.get(['contactName']).value,
      contactMobile: this.editForm.get(['contactMobile']).value,
      contactEmail: this.editForm.get(['contactEmail']).value,
      isActive: this.editForm.get(['isActive']).value,
      timeCreated:
        this.editForm.get(['timeCreated']).value != null ? moment(this.editForm.get(['timeCreated']).value, DATE_TIME_FORMAT) : undefined,
      timeModified:
        this.editForm.get(['timeModified']).value != null ? moment(this.editForm.get(['timeModified']).value, DATE_TIME_FORMAT) : undefined,
      userIdCreated: this.editForm.get(['userIdCreated']).value,
      userIdModified: this.editForm.get(['userIdModified']).value,
      customerType: this.editForm.get(['customerType']).value,
      terms: this.editForm.get(['terms']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICustomer>>) {
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

  trackCustomerTypeById(index: number, item: ICustomerType) {
    return item.id;
  }

  trackTermsById(index: number, item: ITerms) {
    return item.id;
  }
}
