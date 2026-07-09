import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { DTOProducto, ListaProductos, ProductoDetailResponse, ProductoUpdateRequest, ProductoResponse } from '../models/producto.model';

@Injectable({ providedIn: 'root' })
export class ProductoService {
  private readonly http = inject(HttpClient);
  private readonly baseUrl = 'http://localhost:8080/Productos';

  registrarProducto(producto: DTOProducto): Observable<DTOProducto> {
    return this.http.post<DTOProducto>(`${this.baseUrl}/productosagregar`, producto);
  }

  listarProductos(): Observable<ListaProductos[]> {
    return this.http.get<ListaProductos[]>(`${this.baseUrl}/listaProductos`);
  }

  getProductoDetalle(id: number): Observable<ProductoDetailResponse> {
    return this.http.get<ProductoDetailResponse>(`${this.baseUrl}/${id}/productodetalle`);
  }

  updateProducto(idProducto: number, request: ProductoUpdateRequest): Observable<ProductoResponse> {
    return this.http.put<ProductoResponse>(`${this.baseUrl}/${idProducto}`, request);
  }

  anularProducto(id: number): Observable<string> {
    return this.http.put<string>(`${this.baseUrl}/${id}/cancelProducto`, {});
  }
}
