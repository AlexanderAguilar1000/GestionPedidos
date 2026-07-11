export interface LoginRequest {
  nombreusuario: string;
  contrasena: string;
}

export interface LoginResponse {
  message: string;
  nombreusuario: string;
}
