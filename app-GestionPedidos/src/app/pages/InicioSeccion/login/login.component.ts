import { ChangeDetectionStrategy, ChangeDetectorRef, Component, inject, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { LoginService } from './services/login.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class LoginComponent {

  private readonly fb = inject(FormBuilder);
  private readonly loginService = inject(LoginService);
  private readonly router = inject(Router);
  private readonly cdr = inject(ChangeDetectorRef);

  readonly cargando = signal(false);
  readonly mensajeError = signal<string | null>(null);
  readonly mensajeExito = signal<string | null>(null);

  readonly form = this.fb.group({
    nombreusuario: ['', [Validators.required]],
    contrasena: ['', [Validators.required]],
  });

  isFieldInvalid(field: string): boolean {
    const control = this.form.get(field);
    return !!(control?.invalid && control?.touched);
  }

  onLogin(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.mensajeError.set(null);
    this.mensajeExito.set(null);
    this.cargando.set(true);

    this.loginService
      .login({
        nombreusuario: this.form.value.nombreusuario!,
        contrasena: this.form.value.contrasena!,
      })
      .subscribe({
        next: (res) => {
          this.cargando.set(false);
          this.mensajeExito.set(`Bienvenido, ${res.nombreusuario}`);
          this.cdr.markForCheck();
          setTimeout(() => this.router.navigate(['/']), 1200);
        },
        error: () => {
          this.cargando.set(false);
          this.mensajeError.set('Credenciales inválidas. Verifique su usuario y contraseña.');
          this.cdr.markForCheck();
        },
      });
  }

  onRegistrar(): void {
    this.router.navigate(['/registro']);
  }

  
}
