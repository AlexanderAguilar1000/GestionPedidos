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

export interface DTOProducto {
  idproducto?: number;
  idcategoria: number;
  idunidadmedida: number;
  nombreProducto: string;
  descripcion: string;
  nombreCategoria?: string;
  unidadmedida?: string;
  activo?: boolean;
}
