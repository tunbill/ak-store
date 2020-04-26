import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AccountService } from '@akfe/account/services/account.service';
import { mergeMap } from 'rxjs/operators';

@Component({
  selector: 'ak-activate',
  templateUrl: './activate.component.html',
  styleUrls: ['./activate.component.scss']
})
export class ActivateComponent implements OnInit {
  constructor(
    private activatedRoute: ActivatedRoute,
    private accountService: AccountService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.activatedRoute.queryParams
      .pipe(mergeMap(params => this.accountService.activate(params.key)))
      .subscribe(
        v => {
          console.log(v);
          this.router.navigateByUrl('/account/login');
        },
        err => {
          console.log(err);
        }
      );
  }
}
