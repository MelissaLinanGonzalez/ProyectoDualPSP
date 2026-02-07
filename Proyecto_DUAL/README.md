# PSP Actividad DUAL - Backend Spring Boot

Proyecto backend para la actividad de FP Dual que cubre los Resultados de Aprendizaje:
- **RA4**: Servicios en red (API REST con Spring Web)
- **RA5**: Seguridad (Spring Security + Validación de datos)

## 📋 Requisitos Previos

- **Java 21** o superior
- **Maven 3.8+**
- **MySQL 8.0+**

## 🗄️ Configuración de Base de Datos

> **⚠️ IMPORTANTE**: Antes de ejecutar la aplicación, debes crear la base de datos manualmente.

### Paso 1: Conectar a MySQL

```bash
mysql -u root -p
```

### Paso 2: Crear la base de datos

```sql
CREATE DATABASE psp_dual_db;
```

### Paso 3 (Opcional): Configurar credenciales

Si tu usuario/contraseña de MySQL no son `root/root`, edita el archivo `src/main/resources/application.properties`:

```properties
spring.datasource.username=TU_USUARIO
spring.datasource.password=TU_CONTRASEÑA
```

O usa variables de entorno:

```bash
export DB_USERNAME=tu_usuario
export DB_PASSWORD=tu_contraseña
```

## 🚀 Ejecución del Proyecto

### Opción 1: Con Maven

```bash
# Compilar el proyecto
mvn clean compile

# Ejecutar la aplicación
mvn spring-boot:run
```

### Opción 2: Generar JAR ejecutable

```bash
# Empaquetar
mvn clean package -DskipTests

# Ejecutar
java -jar target/psp-dual-app-1.0.0.jar
```

La aplicación estará disponible en: `http://localhost:8080`

## 👥 Usuarios de Prueba

| Usuario | Contraseña | Rol   | Permisos                    |
|---------|------------|-------|------------------------------|
| admin   | admin123   | ADMIN | GET, POST, PUT, DELETE       |
| user    | user123    | USER  | Solo GET (consultar)         |

## 📡 API REST - Endpoints

Base URL: `http://localhost:8080/api/productos`

| Método | Endpoint           | Descripción            | Acceso    |
|--------|-------------------|------------------------|-----------|
| GET    | `/api/productos`  | Listar productos       | USER, ADMIN |
| GET    | `/api/productos/{id}` | Obtener producto   | USER, ADMIN |
| POST   | `/api/productos`  | Crear producto         | Solo ADMIN |
| PUT    | `/api/productos/{id}` | Actualizar producto | Solo ADMIN |
| DELETE | `/api/productos/{id}` | Eliminar producto   | Solo ADMIN |

## 🧪 Ejemplos de Prueba con curl

### 1. Listar productos (como USER) ✅

```bash
curl -X GET http://localhost:8080/api/productos \
  -u user:user123
```

**Respuesta esperada**: `200 OK` con lista de productos (vacía al inicio)

### 2. Crear producto (como ADMIN) ✅

```bash
curl -X POST http://localhost:8080/api/productos \
  -u admin:admin123 \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Laptop HP",
    "descripcion": "Laptop HP Pavilion 15 pulgadas",
    "precio": 899.99,
    "stock": 10
  }'
```

**Respuesta esperada**: `201 Created` con el producto creado

### 3. Actualizar producto (como ADMIN) ✅

```bash
curl -X PUT http://localhost:8080/api/productos/1 \
  -u admin:admin123 \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Laptop HP Actualizada",
    "descripcion": "Laptop HP Pavilion 15 pulgadas - Edición 2024",
    "precio": 799.99,
    "stock": 15
  }'
```

**Respuesta esperada**: `200 OK` con el producto actualizado

### 4. Eliminar producto (como USER) ❌ DENEGADO

```bash
curl -X DELETE http://localhost:8080/api/productos/1 \
  -u user:user123
```

**Respuesta esperada**: `403 Forbidden` - El usuario USER no tiene permisos para eliminar

### 5. Eliminar producto (como ADMIN) ✅

```bash
curl -X DELETE http://localhost:8080/api/productos/1 \
  -u admin:admin123
```

**Respuesta esperada**: `204 No Content` - Producto eliminado correctamente

### 6. Crear producto con datos inválidos (prueba de validación) ❌

```bash
curl -X POST http://localhost:8080/api/productos \
  -u admin:admin123 \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "",
    "precio": -50,
    "stock": -5
  }'
```

**Respuesta esperada**: `400 Bad Request` con errores de validación

## 📁 Estructura del Proyecto

```
Proyecto_DUAL/
├── pom.xml                           # Configuración Maven
├── README.md                         # Este archivo
└── src/
    └── main/
        ├── java/com/dualpsp/app/
        │   ├── PspDualApplication.java      # Clase principal
        │   ├── config/
        │   │   └── SecurityConfig.java      # Configuración de seguridad
        │   ├── controller/
        │   │   └── ProductoController.java  # Controlador REST
        │   ├── entity/
        │   │   └── Producto.java            # Entidad JPA
        │   ├── repository/
        │   │   └── ProductoRepository.java  # Repositorio JPA
        │   └── service/
        │       └── ProductoService.java     # Lógica de negocio
        └── resources/
            └── application.properties       # Configuración de la app
```

## 🔒 Resultados de Aprendizaje Cubiertos

### RA4 - Servicios en Red
- ✅ API REST con Spring Web
- ✅ Endpoints CRUD (GET, POST, PUT, DELETE)
- ✅ Respuestas HTTP estándar (200, 201, 204, 400, 403, 404)
- ✅ Formato JSON para intercambio de datos

### RA5 - Seguridad
- ✅ Autenticación con HTTP Basic
- ✅ Autorización basada en roles (ADMIN, USER)
- ✅ Validación de datos de entrada (@NotBlank, @Positive, @Min)
- ✅ Control de acceso granular por endpoint y método HTTP
- ✅ Encriptación de contraseñas con BCrypt

## 📝 Tecnologías Utilizadas

- **Java 21**
- **Spring Boot 4.0.2**
- **Spring Web** - API REST
- **Spring Security** - Autenticación y autorización
- **Spring Validation** - Validación de datos
- **Spring Data JPA** - Persistencia
- **MySQL** - Base de datos
- **Lombok** - Reducción de código boilerplate
- **Maven** - Gestión de dependencias

---

**Autor**: Estudiante FP Dual  
**Fecha**: Febrero 2026  
**Asignatura**: PSP - Programación de Servicios y Procesos
