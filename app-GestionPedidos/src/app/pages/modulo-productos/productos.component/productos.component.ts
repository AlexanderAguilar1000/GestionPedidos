import { ChangeDetectionStrategy, Component, inject, signal } from '@angular/core';
import {
  ProductoFormComponent,
  Producto,
} from '../producto-form.component/producto-form.component';
import { ProductoService } from '../producto.service';

@Component({
  selector: 'app-productos',
  standalone: true,
  imports: [ProductoFormComponent],
  templateUrl: './productos.component.html',
  styleUrl: './productos.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ProductosComponent {
  private readonly productoService = inject(ProductoService);

  readonly mensajeExito = signal<string | null>(null);
  readonly mensajeError = signal<string | null>(null);

  onProductoRegistrado(producto: Producto): void {
    this.mensajeExito.set(null);
    this.mensajeError.set(null);

    this.productoService
      .registrarProducto({
        idcategoria: producto.categoriaId!,
        idunidadmedida: producto.unidadMedidaId!,
        nombreProducto: producto.nombre,
        descripcion: producto.descripcion,
      })
      .subscribe({
        next: () => {
          this.mensajeExito.set('Producto registrado correctamente.');
        },
        error: (error) => {
          console.error('Error al registrar producto:', error);
          this.mensajeError.set('Ocurrió un error al registrar el producto. Intente nuevamente.');
        },
      });
  }

  onCancelar(): void {
    // TODO: manejar cancelación (p. ej. redireccionar)
  }
}
