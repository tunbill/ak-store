import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UnauthorizedNavLayoutComponent } from './unauthorized-nav-layout.component';

describe('UnauthorizedNavLayoutComponent', () => {
  let component: UnauthorizedNavLayoutComponent;
  let fixture: ComponentFixture<UnauthorizedNavLayoutComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [UnauthorizedNavLayoutComponent]
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UnauthorizedNavLayoutComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
