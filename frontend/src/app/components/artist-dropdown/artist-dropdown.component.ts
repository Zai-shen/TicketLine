import { AfterViewInit, Component, HostBinding, Input, OnInit, Optional, Self, ViewChild } from '@angular/core';
import { ArtistApiService, ArtistDTO } from '../../../generated';
import { MatFormFieldControl } from '@angular/material/form-field';
import { ControlValueAccessor, FormControl, NgControl } from '@angular/forms';
import { merge, Observable, Subject } from 'rxjs';
import { debounceTime, distinctUntilChanged, switchMap, tap } from 'rxjs/operators';
import { MatAutocomplete, MatAutocompleteTrigger } from '@angular/material/autocomplete';
import { MatInput } from '@angular/material/input';

@Component({
  selector: 'tl-artist-dropdown',
  templateUrl: './artist-dropdown.component.html',
  providers: [{ provide: MatFormFieldControl, useExisting: ArtistDropdownComponent }],
  styleUrls: ['./artist-dropdown.component.scss']
})
export class ArtistDropdownComponent implements OnInit, MatFormFieldControl<ArtistDTO>, AfterViewInit, ControlValueAccessor {
  constructor(
    @Optional() @Self() public ngControl: NgControl,
    private artistService: ArtistApiService,
  ) {
    if (this.ngControl !== null) {
      this.ngControl.valueAccessor = this;
    }
  }
  @Input()
  get value(): ArtistDTO | null {
    return this._artist;
  }

  set value(artist: ArtistDTO | null) {
    this.artistCtrl.setValue(artist);
    (this.stateChanges as Subject<void>).next();
    this.onChange(artist);
    this.onTouched();
  }

  @Input()
  get placeholder() {
    return this._placeholder;
  }
  set placeholder(plh) {
    this._placeholder = plh;
    (this.stateChanges as Subject<void>).next();
  }

  static nextId = 0;

  @Input()
  public appearance: String = 'fill';

  @ViewChild(MatInput)
  public artistInput: MatInput;
  private _placeholder: string;

  readonly autofilled: boolean;
  readonly controlType: string = 'artist-dropdown';
  readonly disabled: boolean;
  readonly empty: boolean;
  readonly errorState: boolean;
  readonly focused: boolean;
  @HostBinding() id = `artist-dropdown-${ArtistDropdownComponent.nextId++}`;

  @Input()
  public required: boolean = false;

  readonly shouldLabelFloat: boolean;
  readonly stateChanges: Observable<void> = new Subject();

  private _artist: ArtistDTO | null = null;

  public artistCtrl: FormControl = new FormControl();

  @ViewChild(MatAutocompleteTrigger)
  private trigger: MatAutocompleteTrigger;
  filteredArtists: ArtistDTO[] = [];
  loading: boolean = false;

  private onChange: any = () => {};
  private onTouched: any = () => {};

  ngOnInit(): void {
    this.artistCtrl.valueChanges.pipe(
      debounceTime(200),
      distinctUntilChanged(),
      tap(() => {
        this.filteredArtists = [];
        this.loading = true;
      }),
      switchMap(value => {
        if (value !== null && value.id !== undefined && value.id !== null) {
          this.value = value;
        }
        if (value === null || value === undefined || typeof value !== 'string') {
          value = '';
        }
        return merge(
          this.artistService.searchArtists({firstname: value}),
          this.artistService.searchArtists({lastname: value})
        );
      })
    ).subscribe(
      (artists: ArtistDTO[]) => {
        this.filteredArtists = this.filteredArtists.concat(artists);
        this.loading = false;
      }
    );
  }

  displayArtist(artist: ArtistDTO): string {
    return (artist && artist.firstname && artist.lastname) ? artist.firstname + ' ' + artist.lastname : '';
  }

  ngAfterViewInit(): void {
    this.trigger.panelClosingActions.subscribe(e => {
        if (!(e && e.source)) {
          this.value = null;
          this.trigger.closePanel();
        }
      }
    );
  }

  onContainerClick(event: MouseEvent): void {
  }

  setDescribedByIds(ids: string[]): void {
  }

  registerOnChange(fn: any): void {
    this.onChange = fn;
  }

  registerOnTouched(fn: any): void {
    this.onTouched = fn;
  }

  writeValue(obj: any): void {
    this.value = obj;
  }
}
