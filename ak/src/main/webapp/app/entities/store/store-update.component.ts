import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IStore, Store } from 'app/shared/model/store.model';
import { StoreService } from './store.service';

@Component({
  selector: 'ak-store-update',
  templateUrl: './store-update.component.html'
})
export class StoreUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    companyId: [],
    code: [null, [Validators.maxLength(20)]],
    name: [null, [Validators.maxLength(50)]],
    address: [null, [Validators.maxLength(100)]]
  });

  constructor(protected storeService: StoreService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ store }) => {
      this.updateForm(store);
    });
  }

  updateForm(store: IStore) {
    this.editForm.patchValue({
      id: store.id,
      companyId: store.companyId,
      code: store.code,
      name: store.name,
      address: store.address
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
      companyId: this.editForm.get(['companyId']).value,
      code: this.editForm.get(['code']).value,
      name: this.editForm.get(['name']).value,
      address: this.editForm.get(['address']).value
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
}
