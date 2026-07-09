import { ChangeDetectionStrategy, Component, OnInit, inject, signal } from '@angular/core';
import { ProductoFormComponent } from '../producto-form.component/producto-form.component';
import { ProductoToolbarComponent } from '../producto-toolbar.component/producto-toolbar.component';
import { ProductoService } from '../servicios/producto.service';
import { Producto, ListaProductos } from '../models/producto.model';

@Component({
  selector: 'app-productos',
  standalone: true,
  imports: [ProductoFormComponent, ProductoToolbarComponent],
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
    // TODO: implementar eliminación masiva
  }

  onVisualizarProducto(producto: ListaProductos): void {
    console.log('Visualizar producto:', producto);
    // TODO: abrir modal de detalle
  }

  onEditarProducto(producto: ListaProductos): void {
    console.log('Editar producto:', producto);
    // TODO: abrir modal de edición
  }

  onEliminarProducto(producto: ListaProductos): void {
    console.log('Eliminar producto:', producto);
    // TODO: confirmar y llamar endpoint de eliminación
  }
}
