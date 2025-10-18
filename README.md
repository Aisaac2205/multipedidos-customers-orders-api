# MultiPedidos API - Clientes y Pedidos

Microservicio para gestión de clientes y pedidos con MySQL/MariaDB.

## Puerto

8080

## Base de Datos

MySQL / MariaDB

## Documentación API

- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI JSON: http://localhost:8080/api-docs

## Endpoints

### Clientes
- POST /clientes - Crear cliente
- GET /clientes - Listar todos
- GET /clientes/{id} - Obtener por ID

### Pedidos
- POST /pedidos - Crear pedido (cálculo automático de total)
- GET /pedidos - Listar todos
- GET /pedidos/{id} - Obtener por ID

## Dependencias

Requiere:
- Common Library (multipedidos-common-lib)
- MySQL/MariaDB

## Instalación Local

```bash
# 1. Instalar common-library primero
cd ../multipedidos-common-lib
mvn clean install

# 2. Compilar este servicio
mvn clean compile

# 3. Ejecutar
mvn spring-boot:run
```

## Configuración

Copiar `.env.example` a `.env` y configurar variables de entorno.

## Despliegue en Railway

1. Crear nuevo servicio desde este repositorio
2. Conectar base de datos MySQL
3. Configurar variables de entorno según `.env.example`
4. Railway detectará automáticamente el `railway.toml`

### Variables Requeridas en Railway

```
DATABASE_URL (o usar variables de MySQL de Railway)
SPRING_PROFILES_ACTIVE=production
```

## Versión

1.0.0

