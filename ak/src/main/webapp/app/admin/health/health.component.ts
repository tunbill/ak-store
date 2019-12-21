import { Component, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { AkHealthService } from './health.service';
import { AkHealthModalComponent } from './health-modal.component';

@Component({
  selector: 'ak-health',
  templateUrl: './health.component.html'
})
export class AkHealthCheckComponent implements OnInit {
  healthData: any;
  updatingHealth: boolean;

  constructor(private modalService: NgbModal, private healthService: AkHealthService) {}

  ngOnInit() {
    this.refresh();
  }

  baseName(name: string) {
    return this.healthService.getBaseName(name);
  }

  getBadgeClass(statusState) {
    if (statusState === 'UP') {
      return 'badge-success';
    } else {
      return 'badge-danger';
    }
  }

  refresh() {
    this.updatingHealth = true;

    this.healthService.checkHealth().subscribe(
      health => {
        this.healthData = this.healthService.transformHealthData(health);
        this.updatingHealth = false;
      },
      error => {
        if (error.status === 503) {
          this.healthData = this.healthService.transformHealthData(error.error);
          this.updatingHealth = false;
        }
      }
    );
  }

  showHealth(health: any) {
    const modalRef = this.modalService.open(AkHealthModalComponent);
    modalRef.componentInstance.currentHealth = health;
  }

  subSystemName(name: string) {
    return this.healthService.getSubSystemName(name);
  }
}
