# API de Gestión de Usuarios - Proyecto Sematext

Este repositorio contiene la implementación del backend para la gestión de usuarios del sistema Sematext, desarrollado con Java 17 y Spring Boot. El controlador principal expuesto es `ControladorUsuario`, el cual permite el registro de nuevos usuarios y el inicio de sesión.

---

## 🚀 Guía de Inicio Rápido

Para consumir rápidamente la API una vez que el servidor esté en ejecución en local (por defecto en el puerto `8080`):

1. **Registrar un Usuario:**
   Envía una solicitud POST a `/Usuario/Registrarusuario` con la información del nuevo usuario.
2. **Iniciar Sesión:**
   Envía una solicitud POST a `/Usuario/login` con tus credenciales.

---

## 🛠️ Instrucciones de Configuración del Entorno

Antes de compilar y ejecutar el proyecto, asegúrate de tener configurados los siguientes requisitos previos y variables de entorno:

### Requisitos Previos
* **Java Development Kit (JDK):** Versión 17 o superior.
* **Apache Maven:** Versión 3.8 o superior.
* **Base de Datos:** PostgreSQL o MySQL (según tu configuración local).

### Configuración del archivo `application.properties`
Crea o edita el archivo en `src/main/resources/application.properties` con los siguientes parámetros básicos de conexión a tu base de datos:

```properties
# Configuración del servidor
server.port=8080

# Configuración de base de datos
spring.datasource.url=jdbc:mysql://localhost:3306/gestion_pedidos?useSSL=false&serverTimezone=UTC
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contrasena
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# Configuración de JPA / Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
```

---

## 📦 Instrucciones de Instalación y Ejecución

Sigue estos pasos para compilar y levantar el proyecto localmente:

1. **Clonar el repositorio:**
   ```bash
   git clone <url_del_repositorio>
   cd ProyectoSematextBackend
   ```

2. **Compilar el proyecto:**
   Utiliza Maven para descargar las dependencias y compilar el código fuente:
   ```bash
   mvn clean install
   ```

3. **Ejecutar la aplicación:**
   Puedes iniciar el servidor Spring Boot ejecutando:
   ```bash
   mvn spring-boot:run
   ```
   El servidor estará disponible en: `http://localhost:8080`

---

## 🔌 Referencia de Endpoints y Ejemplos de Consumo

### 1. Registro de Usuario (`POST /Usuario/Registrarusuario`)

Crea una nueva cuenta de usuario en el sistema.

* **Estructura del Payload (JSON):**
  | Campo | Tipo | Requerido | Descripción |
  |---|---|---|---|
  | `email` | String | Sí | Correo electrónico único del usuario. |
  | `nombreusuario` | String | Sí | Nombre de usuario único para autenticación. |
  | `apellidopaterno` | String | No | Apellido paterno. |
  | `apellidomaterno` | String | No | Apellido materno. |
  | `contrasena` | String | Sí | Contraseña para la nueva cuenta. |
  | `idrol` | Integer | No | ID del rol asociado. |

#### Ejemplo con `curl`:
```bash
curl -X POST http://localhost:8080/Usuario/Registrarusuario \
  -H "Content-Type: application/json" \
  -d '{
    "email": "juan.perez@example.com",
    "nombreusuario": "juanp",
    "apellidopaterno": "Perez",
    "apellidomaterno": "Gomez",
    "contrasena": "MiClaveSegura123!",
    "idrol": 2
  }'
```

#### Ejemplo con Python (`requests`):
```python
import requests

url = "http://localhost:8080/Usuario/Registrarusuario"
payload = {
    "email": "juan.perez@example.com",
    "nombreusuario": "juanp",
    "apellidopaterno": "Perez",
    "apellidomaterno": "Gomez",
    "contrasena": "MiClaveSegura123!",
    "idrol": 2
}
headers = {
    "Content-Type": "application/json"
}

response = requests.post(url, json=payload, headers=headers)

if response.status_code == 200:
    print("Usuario registrado exitosamente:", response.json())
else:
    print(f"Error ({response.status_code}):", response.json())
```

---

### 2. Inicio de Sesión (`POST /Usuario/login`)

Verifica las credenciales y permite el acceso.

* **Estructura del Payload (JSON):**
  | Campo | Tipo | Requerido | Descripción |
  |---|---|---|---|
  | `nombreusuario` | String | Sí | Nombre de usuario registrado. |
  | `contrasena` | String | Sí | Contraseña de acceso. |

#### Ejemplo con `curl`:
```bash
curl -X POST http://localhost:8080/Usuario/login \
  -H "Content-Type: application/json" \
  -d '{
    "nombreusuario": "juanp",
    "contrasena": "MiClaveSegura123!"
  }'
```

#### Ejemplo con Python (`requests`):
```python
import requests

url = "http://localhost:8080/Usuario/login"
payload = {
    "nombreusuario": "juanp",
    "contrasena": "MiClaveSegura123!"
}
headers = {
    "Content-Type": "application/json"
}

response = requests.post(url, json=payload, headers=headers)

if response.status_code == 200:
    print("Login correcto:", response.json())
else:
    print(f"Error de autenticación ({response.status_code}):", response.json())
```

---

## ⚠️ Ejemplos de Manejo de Errores

El backend maneja excepciones y retorna códigos de estado HTTP semánticos estructurados con mensajes aclaratorios.

### Caso 1: Credenciales Inválidas (Código `401 Unauthorized`)
Se retorna cuando la contraseña o el usuario de login no coinciden.

* **Respuesta JSON:**
  ```json
  {
    "message": "Credenciales invalidas"
  }
  ```

### Caso 2: Intento de Registro Duplicado o Error de Negocio (Código `400 Bad Request`)
Se retorna si el nombre de usuario ya está registrado o faltan datos obligatorios.

* **Respuesta JSON:**
  ```json
  {
    "message": "El nombre de usuario '\''juanp'\'' ya se encuentra en uso"
  }
  ```

### Caso 3: Formato del Body Incorrecto (Código `400 Bad Request`)
Se retorna cuando el cuerpo de la petición no tiene un formato JSON válido o falta.

* **Respuesta JSON:**
  ```json
  {
    "timestamp": "2026-06-26T20:36:21.000+00:00",
    "status": 400,
    "error": "Bad Request",
    "message": "Required request body is missing",
    "path": "/Usuario/login"
  }
  ```
