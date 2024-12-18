import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateSongComponent } from './update-song.component';

describe('UpdateSongComponent', () => {
  let component: UpdateSongComponent;
  let fixture: ComponentFixture<UpdateSongComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UpdateSongComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UpdateSongComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
