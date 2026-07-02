import {
  ChangeDetectionStrategy,
  ChangeDetectorRef,
  Component,
  EventEmitter,
  OnInit,
  Output,
  inject,
  signal,
} from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CategoriaUnidadService } from '../servicios/categoria-unidad.service';
import { DTOCategoria } from '../models/categoria.model';
import { DTOUnidadMedida } from '../models/unidad-medida.model';
import { Producto, CategoriaOption, UnidadMedidaOption } from '../models/producto.model';

@Component({
  selector: 'app-producto-form',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './producto-form.component.html',
  styleUrl: './producto-form.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ProductoFormComponent implements OnInit {
  @Output() productoRegistrado = new EventEmitter<Producto>();
  @Output() cancelar = new EventEmitter<void>();

  private readonly categoriaUnidadService = inject(CategoriaUnidadService);
  private readonly fb = inject(FormBuilder);
  private readonly cdr = inject(ChangeDetectorRef);

  readonly categorias = signal<CategoriaOption[]>([]);
  readonly unidadesMedida = signal<UnidadMedidaOption[]>([]);

  form: FormGroup;

  constructor() {
    this.form = this.fb.group({
      nombre: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(100)]],
      descripcion: ['', Validators.maxLength(500)],
      categoriaId: [null, Validators.required],
      unidadMedidaId: [null, Validators.required],
    });
  }

  ngOnInit(): void {
    this.cargarCategorias();
    this.cargarUnidadesMedida();
  }

  private cargarCategorias(): void {
    this.categoriaUnidadService.getCategorias().subscribe({
      next: (data: DTOCategoria[]) => {
        console.log("HOla", data);
        this.categorias.set(data.map(cat => ({
          id: cat.idcategoria,
          nombre: cat.nombrecategoria
        })));
        this.cdr.markForCheck();
      },
      error: (error) => {
        console.error('Error al cargar categorías:', error);
      },
    });
  }

  private cargarUnidadesMedida(): void {
    this.categoriaUnidadService.getUnidadesMedida().subscribe({
      next: (data: DTOUnidadMedida[]) => {
        this.unidadesMedida.set(data.map(um => ({
          id: um.idunidadmedida,
          nombre: um.nombre
        })));
        this.cdr.markForCheck();
      },
      error: (error) => {
        console.error('Error al cargar unidades de medida:', error);
      },
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
