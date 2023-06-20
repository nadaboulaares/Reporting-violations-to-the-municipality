import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReclamationRejeteeComponent } from './reclamationrejetee.component';

describe('ReclamationRejeteeComponent', () => {
  let component: ReclamationRejeteeComponent;
  let fixture: ComponentFixture<ReclamationRejeteeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReclamationRejeteeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReclamationRejeteeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
