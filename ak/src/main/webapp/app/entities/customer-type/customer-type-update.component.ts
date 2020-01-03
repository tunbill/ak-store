import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ICustomerType, CustomerType } from 'app/shared/model/customer-type.model';
import { CustomerTypeService } from './customer-type.service';

@Component({
  selector: 'ak-customer-type-update',
  templateUrl: './customer-type-update.component.html'
})
export class CustomerTypeUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    companyId: [],
    name: [null, [Validators.required, Validators.maxLength(50)]],
    description: [null, [Validators.maxLength(200)]],
    isActive: []
  });

  constructor(protected customerTypeService: CustomerTypeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ customerType }) => {
      this.updateForm(customerType);
    });
  }

  updateForm(customerType: ICustomerType) {
    this.editForm.patchValue({
      id: customerType.id,
      companyId: customerType.companyId,
      name: customerType.name,
      description: customerType.description,
      isActive: customerType.isActive
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
      companyId: this.editForm.get(['companyId']).value,
      name: this.editForm.get(['name']).value,
      description: this.editForm.get(['description']).value,
      isActive: this.editForm.get(['isActive']).value
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
}
