import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { LanguageSelect } from '@akfe/core/models/language';

@Component({
  selector: 'ak-unauthorized-nav-layout',
  templateUrl: './unauthorized-nav-layout.component.html',
  styleUrls: ['./unauthorized-nav-layout.component.scss']
})
export class UnauthorizedNavLayoutComponent implements OnInit {
  @Input()
  isCollapsed = false;

  @Input() languages: LanguageSelect[] = [];
  @Output() selectLanguage = new EventEmitter<string>();
  @Input() activeLanguage: string = undefined;

  constructor() {}

  ngOnInit(): void {}
}
