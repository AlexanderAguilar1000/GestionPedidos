import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { DTOCategoria } from '../models/categoria.model';
import { DTOUnidadMedida } from '../models/unidad-medida.model';

@Injectable({ providedIn: 'root' })
export class CategoriaUnidadService {
  private readonly http = inject(HttpClient);
  private readonly baseUrlCategoria = 'http://localhost:8080/categoria';
  private readonly baseUrlUnidadMedida = 'http://localhost:8080/UnidadMedida';

  getCategorias(): Observable<DTOCategoria[]> {
    return this.http.get<DTOCategoria[]>(`${this.baseUrlCategoria}/listacategorias`);
  }

  getUnidadesMedida(): Observable<DTOUnidadMedida[]> {
    return this.http.get<DTOUnidadMedida[]>(`${this.baseUrlUnidadMedida}/listaUnidadMedida`);
  }
}
