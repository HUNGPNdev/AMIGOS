import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GiftRegistriesComponent } from './gift-registries.component';

describe('GiftRegistriesComponent', () => {
  let component: GiftRegistriesComponent;
  let fixture: ComponentFixture<GiftRegistriesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GiftRegistriesComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(GiftRegistriesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
