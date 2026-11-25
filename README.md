# ms-employees


Servicio REST para gestionar empleados, con **arquitectura hexagonal**, Java 17, Spring Boot 3.4, Spring Data JPA, MySQL 8.

- [Arquitectura](#arquitectura)
- [Requisitos](#requisitos)
- [Ejecución](#ejecución)
- [API](#api)
- [Seguridad, logs y salud](#seguridad-logs-y-salud)
- [Git Flow](#git-flow)

## Arquitectura
```
com.invex.scr.ms.employee
├── employees/
│   ├── domain/                      Java puro (sin Spring, JPA ni Jackson)
│   │   ├── model/                   Employee, NewEmployee, EmployeeChanges, Gender
│   │   └── exception/               errores de dominio tipados
│   ├── application/                 
│   │   ├── create/                  Casos de uso (interfaces) y handler de escritura
│   │   ├── uodate/                  Casos de uso (interfaces) y handler de actualización
│   │   ├── delete/                  Casos de uso (interfaces) y handler de eliminación 
│   │   ├── find/                    Casos de uso (interfaces) y handlers de lectura: obtener, listar, buscar
│   └── infrastructure/adapter/
│       ├── in/controllers/          Controlador REST, DTOs, mapper, GlobalExceptionHandler (ProblemDetail)
│       └── out/persistence/         Entidad JPA, repositorio Spring Data, mapper, adaptador del puerto
├── config/                          Beans, OpenAPI, propiedades validadas (@ConfigurationProperties)
├── security/                        HTTP Basic sin estado, Filtro de peticiones, enmascarado de cabeceras 
└── logging/                         Puerto + adaptador SLF4J
```


## Requisitos

- JDK 17 y Maven 3.9 (solo para compilar y probar en local).

## Ejecución

### Sin Docker para la aplicación

Con un MySQL 8 propio, que ya tenga el esquema `employees`, el usuario dueño y el usuario `app`:

```bash
export DB_URL=jdbc:mysql://localhost:3306/employees
export DB_OWNER_USER=... DB_OWNER_PASSWORD=...      # Flyway (DDL)
export DB_APP_USER=...   DB_APP_PASSWORD=...        # aplicación (DML)
export APP_SECURITY_USERNAME=... APP_SECURITY_PASSWORD=...   # credenciales de la API
mvn spring-boot:run
```

## API

Todas las rutas, salvo salud y documentación, exigen **HTTP Basic**.

| Método y ruta | Éxito | Errores |
|---|---|---|
| `GET /employees| 200 `lista` | 400 |
| `GET /employees/{id}` | 200 | 404 |
| `POST /employees` (lista de 1 a 100) | 201 (lista creada) | 400 |
| `PUT /employees/{id}` (parcial) | 200 | 400, 404 |
| `DELETE /employees/{id}` (baja lógica) | 204 | 404 |
| `GET /employees/search?name=` | 200 `lista` | 400 |

Ejemplo:

```bash
curl -u "$API_USER:$API_PASSWORD" -X POST http://localhost:8080/employees \
  -H 'Content-Type: application/json' \
  -d '[{"firstName":"Ana","middleName":"María","paternalSurname":"García","maternalSurname":"López",
        "gender":"FEMALE","birthDate":"17-05-1990","jobTitle":"Ingeniera"}]'
```

Campos: `firstName`, `paternalSurname`, `gender` (`MALE`, `FEMALE`, `OTHER`), `birthDate` (`dd-MM-yyyy`, pasada) y
`jobTitle` son obligatorios. `middleName`, `maternalSurname`, `age` y `active` (por defecto `true`) son opcionales.
La respuesta añade `id`, `age` (calculada) y `createdAt` (timestamp UTC automático).

Los errores usan **ProblemDetail** (`application/problem+json`). Las validaciones incluyen `errors: [{field, message}]`.
Nunca se devuelven trazas ni mensajes del driver.


## Seguridad, logs y salud

- **Spring Security**: HTTP Basic sin estado; contraseña con BCrypt; credenciales solo por variables de entorno. CSRF está
  desactivado de forma deliberada: no hay sesión ni cookies, así que no existe la superficie que protege.
- **Logs** (`application.yml`): cada petición registra método, ruta, estado, duración y **cabeceras**; `Authorization`, `Cookie`,
  `Set-Cookie` y `X-Api-Key` se enmascaran y se neutralizan los caracteres de control. Se propaga `X-Correlation-Id`.
- **Actuator**: solo `health`, con `/actuator/health/liveness` y `/actuator/health/readiness`, sin detalles internos.


## Git Flow

Ramas `main` y `develop`, y una rama `feature/<nombre>-<fecha>` por cada fase, integrada con `--no-ff`. Los commits siguen
`tipo: descripción`. No hay remoto configurado.
