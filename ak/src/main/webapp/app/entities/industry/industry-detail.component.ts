import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IIndustry } from 'app/shared/model/industry.model';

@Component({
  selector: 'ak-industry-detail',
  templateUrl: './industry-detail.component.html'
})
export class IndustryDetailComponent implements OnInit {
  industry: IIndustry;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ industry }) => {
      this.industry = industry;
    });
  }

  previousState() {
    window.history.back();
  }
}
