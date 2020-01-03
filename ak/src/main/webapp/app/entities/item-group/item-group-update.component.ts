import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IItemGroup, ItemGroup } from 'app/shared/model/item-group.model';
import { ItemGroupService } from './item-group.service';

@Component({
  selector: 'ak-item-group-update',
  templateUrl: './item-group-update.component.html'
})
export class ItemGroupUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    companyId: [],
    code: [null, [Validators.maxLength(10)]],
    name: [null, [Validators.required, Validators.maxLength(50)]],
    description: [null, [Validators.maxLength(200)]],
    isActive: []
  });

  constructor(protected itemGroupService: ItemGroupService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ itemGroup }) => {
      this.updateForm(itemGroup);
    });
  }

  updateForm(itemGroup: IItemGroup) {
    this.editForm.patchValue({
      id: itemGroup.id,
      companyId: itemGroup.companyId,
      code: itemGroup.code,
      name: itemGroup.name,
      description: itemGroup.description,
      isActive: itemGroup.isActive
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const itemGroup = this.createFromForm();
    if (itemGroup.id !== undefined) {
      this.subscribeToSaveResponse(this.itemGroupService.update(itemGroup));
    } else {
      this.subscribeToSaveResponse(this.itemGroupService.create(itemGroup));
    }
  }

  private createFromForm(): IItemGroup {
    return {
      ...new ItemGroup(),
      id: this.editForm.get(['id']).value,
      companyId: this.editForm.get(['companyId']).value,
      code: this.editForm.get(['code']).value,
      name: this.editForm.get(['name']).value,
      description: this.editForm.get(['description']).value,
      isActive: this.editForm.get(['isActive']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IItemGroup>>) {
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
