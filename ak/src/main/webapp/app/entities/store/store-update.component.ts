import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IStore, Store } from 'app/shared/model/store.model';
import { StoreService } from './store.service';
import { ICompany } from 'app/shared/model/company.model';
import { CompanyService } from 'app/entities/company/company.service';

@Component({
  selector: 'ak-store-update',
  templateUrl: './store-update.component.html'
})
export class StoreUpdateComponent implements OnInit {
  isSaving: boolean;

  companies: ICompany[];

  editForm = this.fb.group({
    id: [],
    code: [null, [Validators.maxLength(20)]],
    name: [null, [Validators.maxLength(50)]],
    address: [null, [Validators.maxLength(100)]],
    company: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected storeService: StoreService,
    protected companyService: CompanyService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ store }) => {
      this.updateForm(store);
    });
    this.companyService
      .query()
      .subscribe((res: HttpResponse<ICompany[]>) => (this.companies = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(store: IStore) {
    this.editForm.patchValue({
      id: store.id,
      code: store.code,
      name: store.name,
      address: store.address,
      company: store.company
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const store = this.createFromForm();
    if (store.id !== undefined) {
      this.subscribeToSaveResponse(this.storeService.update(store));
    } else {
      this.subscribeToSaveResponse(this.storeService.create(store));
    }
  }

  private createFromForm(): IStore {
    return {
      ...new Store(),
      id: this.editForm.get(['id']).value,
      code: this.editForm.get(['code']).value,
      name: this.editForm.get(['name']).value,
      address: this.editForm.get(['address']).value,
      company: this.editForm.get(['company']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStore>>) {
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
