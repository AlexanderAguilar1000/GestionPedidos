import { Routes } from '@angular/router';
import { LoginComponent } from './pages/InicioSeccion/login/login.component';
import { ProductosComponent } from './pages/modulo-productos/productos.component/productos.component';

export const routes: Routes = [
  {
    path: 'login',
    component: LoginComponent,
  },
  {
    path: '',
    component: ProductosComponent,
  },
  {
    path: '**',
    redirectTo: 'login',
  },
];
