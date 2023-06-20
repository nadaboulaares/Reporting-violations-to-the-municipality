import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReclamationvalideComponent } from './reclamationvalide.component';

describe('ReclamationvalideComponent', () => {
  let component: ReclamationvalideComponent;
  let fixture: ComponentFixture<ReclamationvalideComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReclamationvalideComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReclamationvalideComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
