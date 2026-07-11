# Arquitectura y Estructura de Carpetas: Generador de Documentos

Este documento describe la arquitectura modular del módulo de Angular **Generador de Documentos** (`generador-documentos`). Está diseñado bajo el principio de **Feature-Based Modularity with Sub-page Scoping** (Modularidad orientada a características con componentes encapsulados por página). 

---

## 1. Estructura de Carpetas

A continuación, se detalla la estructura física de directorios dentro de `generador-documentos`:

```text
generador-documentos/
│
├── components/                 # Componentes de presentación compartidos dentro del módulo
│   ├── chip/                   # Componente de etiqueta o badge reusable
│   ├── kebab-menu/             # Menú de opciones de tres puntos reusable
│   ├── stat-card/              # Tarjetas de resumen/estadísticas
│   ├── tab-nav/                # Barra de navegación por pestañas del módulo
│   └── type-card/              # Tarjeta para selección de tipo de documento
│
├── models/                     # Interfaces, tipos y constantes del dominio
│   ├── automatizacion.model.ts
│   ├── configuracion.model.ts
│   ├── documento.model.ts
│   ├── plantilla.model.ts
│   └── index.ts                # Archivo barrel de exportaciones
│
├── services/                   # Servicios de Angular para consumir APIs y lógica de negocio
│   ├── automatizacion.service.ts
│   ├── configuracion.service.ts
│   ├── documento.service.ts
│   ├── plantilla.service.ts
│   └── index.ts                # Archivo barrel de exportaciones
│
├── utils/                      # Funciones de utilidad puras y formateadores de datos
│   └── formatters.ts           # Formateo de fechas, parsing de variables, descarga de blobs
│
├── pages/                      # Páginas inteligentes (Smart Components) accesibles por rutas
│   │
│   ├── dashboard/              # Página principal del dashboard
│   │   ├── components/         # Modales y subcomponentes exclusivos de esta vista
│   │   │   ├── document-viewer/
│   │   │   ├── documento-trash/
│   │   │   └── ... (otros modales)
│   │   ├── dashboard.component.html
│   │   └── dashboard.component.ts
│   │
│   ├── plantillas/             # Página de gestión de plantillas
│   │   ├── components/         # Modales y subcomponentes exclusivos de esta vista
│   │   │   ├── plantilla-card/
│   │   │   ├── plantilla-editor-modal/
│   │   │   └── ... (otros modales)
│   │   ├── plantillas.component.html
│   │   ├── plantillas.component.scss
│   │   └── plantillas.component.ts
│   │
│   ├── automatizaciones/       # Página de gestión de automatizaciones
│   │   ├── components/         # Modales y subcomponentes exclusivos de esta vista
│   │   ├── automatizaciones.component.html
│   │   └── automatizaciones.component.ts
│   │
│   └── configuracion/          # Página de configuración del módulo
│       ├── configuracion.component.html
│       └── configuracion.component.ts
│
├── generador-documentos.component.ts   # Layout/Contenedor raíz del módulo (Smart Container)
└── generador-documentos.routes.ts      # Definición de rutas hijas (Lazy Loading) del módulo
```

---

## 2. Responsabilidades por Carpeta

### `components/` (Shared/Dumb Components)
* **Responsabilidad:** Alojar componentes puramente visuales y reusables en múltiples páginas del módulo.
* **Características:**
  - Deben ser componentes de presentación (*dumb components*).
  - Reciben datos a través de `@Input()` y emiten eventos a través de `@Output()`.
  - No deben inyectar servicios del módulo que conecten directamente con la API (mantenerlos libres de acoplamiento).

### `models/` (Domain Models & Types)
* **Responsabilidad:** Declarar la estructura de datos del dominio (interfaces TypeScript, clases, DTOs y tipos) junto a constantes globales de selección.
* **Características:**
  - Evita lógica de ejecución.
  - Implementa el patrón *Barrel* mediante un `index.ts` que exporta todo, simplificando las rutas de importación hacia el exterior del módulo.

### `services/` (HTTP Services & State Management)
* **Responsabilidad:** Conectar el frontend con las APIs del backend y gestionar el estado local/global del módulo.
* **Características:**
  - Inyectables (`@Injectable({ providedIn: 'root' })` o provistos a nivel de componente).
  - Encapsulan las operaciones HTTP del módulo.
  - Devuelven observables y manejan mapeos o conversiones si es necesario.
  - También incluye un archivo `index.ts` para exportación unificada.

### `utils/` (Helper Pure Functions)
* **Responsabilidad:** Guardar utilidades puras que no dependen de la inyección de dependencias de Angular ni del ciclo de vida del framework.
* **Características:**
  - Funciones puras (sin efectos secundarios) para dar formato a datos, descargar archivos, manejar manipulaciones del DOM manuales o parsear sintaxis especial (ej. `{{variable}}`).

### `pages/` (Smart Components & Page-Scoped Views)
* **Responsabilidad:** Contener las vistas principales del módulo. Cada carpeta dentro de `pages` representa una pantalla o pestaña ruteable del sistema.
* **Características:**
  - El componente base de cada carpeta (ej. `dashboard.component.ts`) es un *Smart Component* (inyecta servicios, maneja la carga de datos iniciales y define la lógica de flujo principal).
  - **Sub-Carpeta `components/` local:** Si una pantalla usa modales de formulario, modales de confirmación, visores especiales, etc., que **solo** se utilizan en esta pantalla, se guardan en la carpeta `components/` interna de dicha página. Esto previene la contaminación de la carpeta `components/` global.

### Raíz del Módulo (`generador-documentos.component.ts` & `generador-documentos.routes.ts`)
* **`generador-documentos.component.ts`:** Actúa como el layout contenedor de todo el módulo. Contiene las pestañas superiores (tabs), títulos y un `<router-outlet>` donde se inyectan dinámicamente las páginas del directorio `pages/`.
* **`generador-documentos.routes.ts`:** Registra las rutas asociadas a cada página utilizando la carga diferida (`loadComponent()`) para agilizar el rendimiento de la aplicación.

---

## 3. Prompt para Generación de Nuevos Módulos con Claude

Copia y pega el siguiente prompt cuando quieras crear una estructura de archivos limpia para un nuevo módulo en Angular:

```markdown
Actúa como un arquitecto experto de Angular Frontend. Quiero que diseñes la estructura de archivos y carpetas para un nuevo módulo llamado "[NOMBRE_DEL_MODULO_EN_PLURAL]".

Este proyecto utiliza Angular standalone con TypeScript y sigue el patrón de diseño "Feature-Based Modularity with Sub-page Scoping" (Modularidad por característica con componentes encapsulados por página).

### 1. Directrices de Estructura de Carpetas

Debes seguir esta estricta jerarquía de carpetas al organizar el módulo:

[nombre-modulo]/
├── components/                 # Componentes visuales compartidos en todo el módulo (Dumb Components)
├── models/                     # Interfaces de TS y constantes, exportadas mediante index.ts
├── services/                   # Servicios Angular que consumen APIs HTTP, exportados mediante index.ts
├── utils/                      # Funciones utilitarias puras (helpers) de formateo o mapeo
├── pages/                      # Vistas ruteables independientes (Smart Components)
│   ├── [pagina-1]/
│   │   ├── components/         # Componentes y modales de uso exclusivo de [pagina-1]
│   │   ├── [pagina-1].component.ts
│   │   └── [pagina-1].component.html
│   └── [pagina-2]/
│       ├── components/         # Componentes y modales de uso exclusivo de [pagina-2]
│       ├── [pagina-2].component.ts
│       └── [pagina-2].component.html
├── [nombre-modulo].component.ts  # Componente Layout/Contenedor del módulo (con router-outlet y pestañas si aplica)
└── [nombre-modulo].routes.ts     # Definición de rutas hijas del módulo
```

### 2. Ejemplo de Referencia (Módulo: Gestión de Inventario)

Para un módulo de inventarios con tres pestañas: "Stock Actual", "Movimientos de Almacén", e "Historial de Transferencias":

1. **`inventario.component.ts`**: Contenedor principal que renderiza el breadcrumb del módulo, el menú de tabs principales ("stock", "movimientos", "transferencias") y el `<router-outlet>`.
2. **`inventario.routes.ts`**: Define las subrutas para re-direccionar a las vistas correspondientes dentro de `pages/`.
3. **`models/stock.model.ts`**, `models/movimiento.model.ts` e `index.ts`.
4. **`services/stock.service.ts`**, `services/movimiento.service.ts` e `index.ts`.
5. **`pages/stock/`**: Contiene `stock.component.ts`, `stock.component.html` y la carpeta `components/` interna con los componentes:
   - `stock-adjust-modal/` (Modal para registrar un ajuste físico de stock)
   - `stock-detail-modal/` (Modal de detalles del producto)
6. **`pages/movimientos/`**: Contiene `movimientos.component.ts`, `movimientos.component.html` y la carpeta `components/` interna con los componentes:
   - `movimiento-form-modal/` (Formulario para registrar una entrada o salida)
7. **`components/` compartido**: Componentes como `inventario-status-badge/` y `warehouse-selector/` que se reutilizan tanto en la vista de Stock como en la de Movimientos.

---

### TAREA A REALIZAR:

Quiero que me generes la estructura de archivos completa para el módulo "[NOMBRE_DEL_MODULO_A_CREAR]". 
- Describe el árbol de archivos resultante.
- Genera el código correspondiente a:
  1. `[nombre-modulo].routes.ts`
  2. `[nombre-modulo].component.ts` (con la navegación por tabs)
  3. Los archivos `.model.ts` y `.service.ts` necesarios.
  4. Los componentes smart de la carpeta `pages/` y algunos ejemplos de subcomponentes dentro de su respectiva sub-carpeta `components/`.

Asegúrate de que todos los componentes sean Standalone, usen `ChangeDetectionStrategy.OnPush` y que el tipado de TypeScript sea estricto.
```
