import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IJobs } from 'app/shared/model/jobs.model';

@Component({
  selector: 'ak-jobs-detail',
  templateUrl: './jobs-detail.component.html'
})
export class JobsDetailComponent implements OnInit {
  jobs: IJobs;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ jobs }) => {
      this.jobs = jobs;
    });
  }

  previousState() {
    window.history.back();
  }
}
