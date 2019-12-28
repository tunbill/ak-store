import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IItemGroup, ItemGroup } from 'app/shared/model/item-group.model';
import { ItemGroupService } from './item-group.service';
import { ICompany } from 'app/shared/model/company.model';
import { CompanyService } from 'app/entities/company/company.service';

@Component({
  selector: 'ak-item-group-update',
  templateUrl: './item-group-update.component.html'
})
export class ItemGroupUpdateComponent implements OnInit {
  isSaving: boolean;

  companies: ICompany[];

  editForm = this.fb.group({
    id: [],
    code: [null, [Validators.maxLength(10)]],
    name: [null, [Validators.required, Validators.maxLength(200)]],
    company: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected itemGroupService: ItemGroupService,
    protected companyService: CompanyService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ itemGroup }) => {
      this.updateForm(itemGroup);
    });
    this.companyService
      .query()
      .subscribe((res: HttpResponse<ICompany[]>) => (this.companies = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(itemGroup: IItemGroup) {
    this.editForm.patchValue({
      id: itemGroup.id,
      code: itemGroup.code,
      name: itemGroup.name,
      company: itemGroup.company
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
      code: this.editForm.get(['code']).value,
      name: this.editForm.get(['name']).value,
      company: this.editForm.get(['company']).value
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
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackCompanyById(index: number, item: ICompany) {
    return item.id;
  }
}
