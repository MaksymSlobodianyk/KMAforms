import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreatedFormsListComponent } from './created-forms-list.component';

describe('CreatedFormsListComponent', () => {
  let component: CreatedFormsListComponent;
  let fixture: ComponentFixture<CreatedFormsListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CreatedFormsListComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CreatedFormsListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
