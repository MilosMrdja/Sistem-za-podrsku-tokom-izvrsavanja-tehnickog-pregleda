import { Component, Input } from '@angular/core';

@Component({ standalone: false,
  selector: 'app-ui-form-card',
  templateUrl: './ui-form-card.component.html',
  styleUrl: './ui-form-card.component.scss',
})
export class UiFormCardComponent {
  @Input() title = '';
  @Input() subtitle = '';
}
