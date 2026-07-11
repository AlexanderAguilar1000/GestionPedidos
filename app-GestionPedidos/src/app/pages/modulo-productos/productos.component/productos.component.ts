import { ChangeDetectionStrategy, Component, OnInit, inject, signal } from '@angular/core';
import { ProductoFormComponent } from '../producto-form.component/producto-form.component';
import { ProductoEditComponent } from '../producto-edit.component/producto-edit.component';
import { ProductoToolbarComponent } from '../producto-toolbar.component/producto-toolbar.component';
import { ProductoService } from '../servicios/producto.service';
import { Producto, ListaProductos } from '../models/producto.model';

@Component({
  selector: 'app-productos',
  standalone: true,
  imports: [ProductoFormComponent, ProductoEditComponent, ProductoToolbarComponent],
  templateUrl: './productos.component.html',
  styleUrl: './productos.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ProductosComponent implements OnInit {
  private readonly productoService = inject(ProductoService);

  readonly productos = signal<ListaProductos[]>([]);
  readonly cargando = signal(false);
  readonly mostrarFormulario = signal(false);
  readonly mensajeExito = signal<string | null>(null);
  readonly mensajeError = signal<string | null>(null);

  readonly productoEditando = signal<ListaProductos | null>(null);
  readonly productoAEliminar = signal<ListaProductos | null>(null);
  readonly eliminando = signal(false);

  ngOnInit(): void {
    this.cargarProductos();
  }

  private cargarProductos(): void {
    this.cargando.set(true);
    this.productoService.listarProductos().subscribe({
      next: (lista) => {
        this.productos.set(lista);
        this.cargando.set(false);
      },
      error: (error) => {
        console.error('Error al cargar productos:', error);
        this.mensajeError.set('No se pudo cargar la lista de productos.');
        this.cargando.set(false);
      },
    });
  }

  onAbrirFormulario(): void {
    this.mensajeExito.set(null);
    this.mensajeError.set(null);
    this.mostrarFormulario.set(true);
  }

  onCerrarFormulario(): void {
    this.mostrarFormulario.set(false);
  }

  onProductoRegistrado(producto: Producto): void {
    this.productoService
      .registrarProducto({
        idcategoria: producto.categoriaId!,
        idunidadmedida: producto.unidadMedidaId!,
        nombreProducto: producto.nombre,
        descripcion: producto.descripcion,
      })
      .subscribe({
        next: () => {
          this.mostrarFormulario.set(false);
          this.mensajeExito.set('Producto registrado correctamente.');
          this.cargarProductos();
        },
        error: (error) => {
          console.error('Error al registrar producto:', error);
          this.mensajeError.set('Ocurrió un error al registrar el producto. Intente nuevamente.');
        },
      });
  }

  onEliminar(): void {
    // TODO: eliminación masiva
  }

  // ── Edición ──────────────────────────────────────────

  onEditarProducto(producto: ListaProductos): void {
    this.mensajeExito.set(null);
    this.mensajeError.set(null);
    this.productoEditando.set(producto);
  }

  onCerrarEdicion(): void {
    this.productoEditando.set(null);
  }

  onProductoEditado(): void {
    this.productoEditando.set(null);
    this.mensajeExito.set('Producto actualizado correctamente.');
    this.cargarProductos();
  }

  // ── Eliminación ───────────────────────────────────────

  onEliminarProducto(producto: ListaProductos): void {
    this.mensajeExito.set(null);
    this.mensajeError.set(null);
    this.productoAEliminar.set(producto);
  }

  onCerrarEliminar(): void {
    this.productoAEliminar.set(null);
  }

  onConfirmarEliminar(): void {
    const producto = this.productoAEliminar();
    if (!producto) return;

    this.eliminando.set(true);
    this.productoService.anularProducto(producto.idproducto).subscribe({
      next: () => {
        // Remover el producto del array local inmediatamente
        this.productos.update((productos) =>
          productos.filter((p) => p.idproducto !== producto.idproducto)
        );
        
        this.eliminando.set(false);
        this.productoAEliminar.set(null);
        this.mensajeExito.set(`El producto "${producto.nombreProducto}" fue enviado a la papelera.`);
      },
      error: (error) => {
        console.error('Error al anular producto:', error);
        this.eliminando.set(false);
        this.productoAEliminar.set(null);
        this.mensajeError.set('Ocurrió un error al eliminar el producto. Intente nuevamente.');
      },
    });
  }
}
