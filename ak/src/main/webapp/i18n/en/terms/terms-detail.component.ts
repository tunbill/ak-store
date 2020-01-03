import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITerms } from 'app/shared/model/terms.model';

@Component({
  selector: 'ak-terms-detail',
  templateUrl: './terms-detail.component.html'
})
export class TermsDetailComponent implements OnInit {
  terms: ITerms;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ terms }) => {
      this.terms = terms;
    });
  }

  previousState() {
    window.history.back();
  }
}
