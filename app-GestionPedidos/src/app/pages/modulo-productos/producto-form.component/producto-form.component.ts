import {
  ChangeDetectionStrategy,
  Component,
  EventEmitter,
  Output,
} from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';

export interface Producto {
  nombre: string;
  descripcion: string;
  categoriaId: number | null;
  unidadMedidaId: number | null;
}

export interface CategoriaOption {
  id: number;
  nombre: string;
}

export interface UnidadMedidaOption {
  id: number;
  nombre: string;
}

@Component({
  selector: 'app-producto-form',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './producto-form.component.html',
  styleUrl: './producto-form.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ProductoFormComponent {
  @Output() productoRegistrado = new EventEmitter<Producto>();
  @Output() cancelar = new EventEmitter<void>();

  categorias: CategoriaOption[] = [];
  unidadesMedida: UnidadMedidaOption[] = [];

  form: FormGroup;

  constructor(private fb: FormBuilder) {
    this.form = this.fb.group({
      nombre: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(100)]],
      descripcion: ['', Validators.maxLength(500)],
      categoriaId: [null, Validators.required],
      unidadMedidaId: [null, Validators.required],
    });
  }

  onSubmit(): void {
    if (this.form.valid) {
      this.productoRegistrado.emit(this.form.value as Producto);
      this.form.reset({ nombre: '', descripcion: '', categoriaId: null, unidadMedidaId: null });
    } else {
      this.form.markAllAsTouched();
    }
  }

  onCancelar(): void {
    this.form.reset({ nombre: '', categoriaId: null, unidadMedidaId: null });
    this.cancelar.emit();
  }

  isFieldInvalid(field: string): boolean {
    const control = this.form.get(field);
    return !!(control && control.invalid && control.touched);
  }
}
