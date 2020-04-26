import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { LanguageSelect } from '@akfe/core/models/language';

@Component({
  selector: 'ak-nav-layout',
  templateUrl: './nav-layout.component.html',
  styleUrls: ['./nav-layout.component.scss']
})
export class NavLayoutComponent implements OnInit {
  @Input()
  isCollapsed = false;

  @Input() languages: LanguageSelect[] = [];
  @Input() activeLanguage: string = undefined;
  @Output() selectLanguage = new EventEmitter<string>();
  @Output() logout = new EventEmitter<void>();

  constructor() {}

  ngOnInit(): void {}
}
