import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DownloadSongComponent } from './download-song.component';

describe('DownloadSongComponent', () => {
  let component: DownloadSongComponent;
  let fixture: ComponentFixture<DownloadSongComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DownloadSongComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DownloadSongComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
