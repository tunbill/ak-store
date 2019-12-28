import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IIndustry, Industry } from 'app/shared/model/industry.model';
import { IndustryService } from './industry.service';

@Component({
  selector: 'ak-industry-update',
  templateUrl: './industry-update.component.html'
})
export class IndustryUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.maxLength(100)]]
  });

  constructor(protected industryService: IndustryService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ industry }) => {
      this.updateForm(industry);
    });
  }

  updateForm(industry: IIndustry) {
    this.editForm.patchValue({
      id: industry.id,
      name: industry.name
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const industry = this.createFromForm();
    if (industry.id !== undefined) {
      this.subscribeToSaveResponse(this.industryService.update(industry));
    } else {
      this.subscribeToSaveResponse(this.industryService.create(industry));
    }
  }

  private createFromForm(): IIndustry {
    return {
      ...new Industry(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IIndustry>>) {
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
