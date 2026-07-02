import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { DTOProducto } from '../models/producto.model';

@Injectable({ providedIn: 'root' })
export class ProductoService {
  private readonly http = inject(HttpClient);
  private readonly baseUrl = 'http://localhost:8080/Productos';

  registrarProducto(producto: DTOProducto): Observable<DTOProducto> {
    return this.http.post<DTOProducto>(`${this.baseUrl}/productosagregar`, producto);
  }
}
