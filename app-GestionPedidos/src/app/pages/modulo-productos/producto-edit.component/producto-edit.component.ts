import {
  ChangeDetectionStrategy,
  ChangeDetectorRef,
  Component,
  EventEmitter,
  Input,
  OnInit,
  Output,
  inject,
  signal,
} from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { forkJoin } from 'rxjs';
import { CategoriaUnidadService } from '../servicios/categoria-unidad.service';
import { ProductoService } from '../servicios/producto.service';
import { CategoriaOption, UnidadMedidaOption, ProductoUpdateRequest } from '../models/producto.model';
import { DTOCategoria } from '../models/categoria.model';
import { DTOUnidadMedida } from '../models/unidad-medida.model';

@Component({
  selector: 'app-producto-edit',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './producto-edit.component.html',
  styleUrl: './producto-edit.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ProductoEditComponent implements OnInit {
  @Input({ required: true }) productoId!: number;
  @Output() guardado = new EventEmitter<void>();
  @Output() cancelar = new EventEmitter<void>();

  private readonly productoService = inject(ProductoService);
  private readonly categoriaUnidadService = inject(CategoriaUnidadService);
  private readonly fb = inject(FormBuilder);
  private readonly cdr = inject(ChangeDetectorRef);

  readonly categorias = signal<CategoriaOption[]>([]);
  readonly unidadesMedida = signal<UnidadMedidaOption[]>([]);
  readonly cargando = signal(true);
  readonly guardando = signal(false);
  readonly errorCarga = signal<string | null>(null);
  readonly errorGuardado = signal<string | null>(null);

  form!: FormGroup;

  constructor() {
    this.form = this.fb.group({
      nombreproducto: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(100)]],
      descripcion: ['', Validators.maxLength(500)],
      categoria: [null, Validators.required],
      unidadMedida: [null, Validators.required],
    });
  }

  ngOnInit(): void {
    forkJoin({
      detalle: this.productoService.getProductoDetalle(this.productoId),
      categorias: this.categoriaUnidadService.getCategorias(),
      unidades: this.categoriaUnidadService.getUnidadesMedida(),
    }).subscribe({
      next: ({ detalle, categorias, unidades }) => {
        const catOptions: CategoriaOption[] = categorias.map((c: DTOCategoria) => ({
          id: c.idcategoria,
          nombre: c.nombrecategoria,
        }));
        const umOptions: UnidadMedidaOption[] = unidades.map((u: DTOUnidadMedida) => ({
          id: u.idunidadmedida,
          nombre: u.nombre,
        }));

        this.categorias.set(catOptions);
        this.unidadesMedida.set(umOptions);

        const catId = catOptions.find(c => c.nombre === detalle.categoria)?.id ?? null;
        const umId = umOptions.find(u => u.nombre === detalle.unidadMedida)?.id ?? null;

        this.form.patchValue({
          nombreproducto: detalle.nombreproducto,
          descripcion: detalle.descripcion,
          categoria: catId,
          unidadMedida: umId,
        });

        this.cargando.set(false);
        this.cdr.markForCheck();
      },
      error: () => {
        this.errorCarga.set('No se pudo cargar la información del producto.');
        this.cargando.set(false);
        this.cdr.markForCheck();
      },
    });
  }

  onGuardar(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }
    this.guardando.set(true);
    this.errorGuardado.set(null);

    const request: ProductoUpdateRequest = {
      idproducto: this.productoId,
      nombreproducto: this.form.value.nombreproducto,
      descripcion: this.form.value.descripcion,
      categoria: this.form.value.categoria,
      unidadMedida: this.form.value.unidadMedida,
      activo: true,
    };

    this.productoService.updateProducto(this.productoId, request).subscribe({
      next: () => {
        this.guardando.set(false);
        this.guardado.emit();
      },
      error: () => {
        this.errorGuardado.set('Ocurrió un error al guardar los cambios. Intente nuevamente.');
        this.guardando.set(false);
        this.cdr.markForCheck();
      },
    });
  }

  onCancelar(): void {
    this.cancelar.emit();
  }

  isFieldInvalid(field: string): boolean {
    const control = this.form.get(field);
    return !!(control && control.invalid && control.touched);
  }
}
