import { ChangeDetectionStrategy, Component, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'app-producto-toolbar',
  standalone: true,
  templateUrl: './producto-toolbar.component.html',
  styleUrl: './producto-toolbar.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ProductoToolbarComponent {
  @Output() registrar = new EventEmitter<void>();
  @Output() eliminar = new EventEmitter<void>();
}
