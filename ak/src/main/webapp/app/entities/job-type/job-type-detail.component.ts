import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IJobType } from 'app/shared/model/job-type.model';

@Component({
  selector: 'ak-job-type-detail',
  templateUrl: './job-type-detail.component.html'
})
export class JobTypeDetailComponent implements OnInit {
  jobType: IJobType;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ jobType }) => {
      this.jobType = jobType;
    });
  }

  previousState() {
    window.history.back();
  }
}
