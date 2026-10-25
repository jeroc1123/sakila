# Sakila Database Studio

Panel Vue 3 para consultar y administrar los endpoints REST del proyecto Spring Boot `boot.vue\sb_sakila`.

## Requisitos

- Node.js 20.19+ o 22.12+
- Spring Boot ejecutándose en `http://localhost:8080`
- Base de datos Sakila configurada para el backend

## Desarrollo

Desde esta carpeta:

```powershell
npm install
npm run dev
```

Inicia primero la API desde `boot.vue\sb_sakila`:

```powershell
mvn spring-boot:run
```

Abre la URL que muestra Vite (por defecto `http://127.0.0.1:5173`). Vite reenvía `/api` a `http://127.0.0.1:8080`, así que el navegador no necesita configuración CORS adicional durante el desarrollo.

## Producción

```powershell
npm run build
npm run preview
```

La aplicación espera que el servidor que la aloja también publique `/api`. Para usar otra URL de API, define `VITE_API_BASE_URL` antes de compilar.

## Funcionalidad

- Navegación agrupada por catálogo, direcciones, ventas y vistas.
- Listado, búsqueda local, casillas de selección y paginación de 10, 25 o 50 filas.
- Detalle de un registro y creación de registros para las entidades con CRUD.
- Eliminación de varios registros seleccionados, con confirmación y reporte de errores.
- Las siete entidades del paquete `vistas` son de solo lectura.
