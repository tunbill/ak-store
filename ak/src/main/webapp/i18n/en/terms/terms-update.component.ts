import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ITerms, Terms } from 'app/shared/model/terms.model';
import { TermsService } from './terms.service';

@Component({
  selector: 'ak-terms-update',
  templateUrl: './terms-update.component.html'
})
export class TermsUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    code: [null, [Validators.maxLength(10)]],
    name: [null, [Validators.required, Validators.maxLength(50)]],
    dayOfMonthDue: [],
    dueNextMonthDays: [],
    discountDayOfMonth: [],
    discountPct: []
  });

  constructor(protected termsService: TermsService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ terms }) => {
      this.updateForm(terms);
    });
  }

  updateForm(terms: ITerms) {
    this.editForm.patchValue({
      id: terms.id,
      code: terms.code,
      name: terms.name,
      dayOfMonthDue: terms.dayOfMonthDue,
      dueNextMonthDays: terms.dueNextMonthDays,
      discountDayOfMonth: terms.discountDayOfMonth,
      discountPct: terms.discountPct
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const terms = this.createFromForm();
    if (terms.id !== undefined) {
      this.subscribeToSaveResponse(this.termsService.update(terms));
    } else {
      this.subscribeToSaveResponse(this.termsService.create(terms));
    }
  }

  private createFromForm(): ITerms {
    return {
      ...new Terms(),
      id: this.editForm.get(['id']).value,
      code: this.editForm.get(['code']).value,
      name: this.editForm.get(['name']).value,
      dayOfMonthDue: this.editForm.get(['dayOfMonthDue']).value,
      dueNextMonthDays: this.editForm.get(['dueNextMonthDays']).value,
      discountDayOfMonth: this.editForm.get(['discountDayOfMonth']).value,
      discountPct: this.editForm.get(['discountPct']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITerms>>) {
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
