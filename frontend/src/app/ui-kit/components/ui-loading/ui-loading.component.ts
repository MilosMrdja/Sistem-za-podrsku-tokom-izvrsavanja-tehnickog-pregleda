import { Component, Input } from '@angular/core';

@Component({ standalone: false,
  selector: 'app-ui-loading',
  templateUrl: './ui-loading.component.html',
  styleUrl: './ui-loading.component.scss',
})
export class UiLoadingComponent {
  @Input() message = 'Učitavanje...';
}
