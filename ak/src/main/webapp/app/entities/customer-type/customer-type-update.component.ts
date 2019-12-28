import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { ICustomerType, CustomerType } from 'app/shared/model/customer-type.model';
import { CustomerTypeService } from './customer-type.service';
import { ICompany } from 'app/shared/model/company.model';
import { CompanyService } from 'app/entities/company/company.service';

@Component({
  selector: 'ak-customer-type-update',
  templateUrl: './customer-type-update.component.html'
})
export class CustomerTypeUpdateComponent implements OnInit {
  isSaving: boolean;

  companies: ICompany[];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.maxLength(100)]],
    description: [],
    isActive: [],
    company: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected customerTypeService: CustomerTypeService,
    protected companyService: CompanyService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ customerType }) => {
      this.updateForm(customerType);
    });
    this.companyService
      .query()
      .subscribe((res: HttpResponse<ICompany[]>) => (this.companies = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(customerType: ICustomerType) {
    this.editForm.patchValue({
      id: customerType.id,
      name: customerType.name,
      description: customerType.description,
      isActive: customerType.isActive,
      company: customerType.company
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const customerType = this.createFromForm();
    if (customerType.id !== undefined) {
      this.subscribeToSaveResponse(this.customerTypeService.update(customerType));
    } else {
      this.subscribeToSaveResponse(this.customerTypeService.create(customerType));
    }
  }

  private createFromForm(): ICustomerType {
    return {
      ...new CustomerType(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      description: this.editForm.get(['description']).value,
      isActive: this.editForm.get(['isActive']).value,
      company: this.editForm.get(['company']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICustomerType>>) {
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

  trackCompanyById(index: number, item: ICompany) {
    return item.id;
  }
}
