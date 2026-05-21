# Alquimia Literaria

Insentivar a la gente a entrar a un mundo de fantasías desde sus cómodos hogares.

---

## Introducción / Contexto

- Facilitar la accesibilidad a la lectura de libros mediante una plataforma digital intuitiva y moderna.  
- **Justificación:** Este proyecto es relevante porque fomenta el hábito lector en entornos digitales, aporta valor educativo al facilitar el acceso a contenido literario y permite aplicar conocimientos de desarrollo de software moderno en un contexto real.  
- **Dominio:** Plataforma web de lectura digital donde los usuarios podrán explorar libros, organizarlos y leerlos desde cualquier dispositivo.

---

## Objetivos

**Objetivo General**  
Desarrollar una plataforma web que facilite el acceso a la lectura digital mediante una experiencia intuitiva, accesible y atractiva para los usuarios.

---

**Objetivos Específicos**  
- Diseñar una arquitectura backend robusta basada en Spring Boot y arquitectura por capas.
- Implementar autenticación y gestión de usuarios.
- Crear un catálogo digital de libros con funcionalidades de búsqueda y filtrado.
- Desarrollar una interfaz frontend moderna y responsiva.
- Integrar una base de datos relacional para almacenamiento persistente de la información.

---

## Alcance del Proyecto (Scope)

**Qué se va a desarrollar:**  
- Registro e inicio de sesión de usuarios.  
- Catálogo digital de libros.
- Búsqueda y filtrado por título, autor o género.
- Vista detallada de cada libro.
- Sistema básico de lectura dentro de la plataforma.
- Panel administrativo básico para gestión de libros.
- API REST documentada.

---

**Qué NO se va a desarrollar en esta versión (fuera de alcance):**  
- Sistema de pagos o suscripciones.  
- Version Aplicacion movil.  
- Lectura offline.  
- Recomendaciones avanzadas con inteligencia artificial.
- Funcionalidades sociales (comentarios o comunidad).
- Audiolibros.

---

## Tecnologías y Herramientas (Tech Stack)

- **Backend:** Spring Boot 3.x, Java 21, Spring Data JPA, PostgreSQL
- **Frontend:** React
- **Base de datos:** Prisma, PostgreSQL en producción / H2
- **Otras herramientas:** Git, GitHub, Postman, Swagger, Docker

---

## Integrantes del Equipo

| Nombre                  | Rol principal              | Usuario GitHub     |
|-------------------------|----------------------------|--------------------|
| Santiago Sánchez Rojas  | Líder / Backend            | @piolin666satan    |
| Santiago Zapata Villada | Frontend Lead              | @SantiagoZVcesde   |
| Josue Restrepo arango   | Full-Stack Developer       | @josuerestrepo     |
| [Nombre 4]              | [rol]                      | @[usuario]         |   

---

## Diagrama de Clases del Dominio (v1)

![Diagrama de Dominio v1](docs/diagrama-dominio-v1.png)  
*Diagrama inicial del modelo de dominio – versión 1. Se actualizará en futuras entregas.*

---

---

## 🚀 Instrucciones de Instalación y Ejecución

Sigue estos pasos exactamente en el orden indicado para configurar el entorno de desarrollo y asegurar el cumplimiento de los requisitos técnicos:

### 1. Clonar el repositorio

Abre tu terminal y ejecuta el siguiente comando para obtener el código fuente:

```bash

git clone [https://github.com/piolin666satan/biblioteca-digital-grupo-6.git](https://github.com/piolin666satan/biblioteca-digital-grupo-6.git)

```

### 2. Entrar al directorio

Accede a la carpeta raíz del proyecto antes de ejecutar cualquier comando de configuración:

cd biblioteca-digital-grupo-6

### 3. Configurar la Base de Datos

Este proyecto utiliza variables de entorno para la configuración de PostgreSQL. Crea un archivo `.env` en la raíz del proyecto con estas variables:

DB_URL=jdbc:postgresql://localhost:5432/alquimia_db
DB_USERNAME=tu_usuario
DB_PASSWORD=tu_contraseña

El archivo `src/main/resources/application.properties` ya está preparado para importar estas variables mediante:

spring.config.import=optional:file:.env[.properties]

Si prefieres no usar un archivo `.env`, puedes definir las mismas variables en tu entorno antes de ejecutar la aplicación.

Escenario B: Configuración para H2 (Base de datos en memoria para pruebas)

spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.h2.console.enabled=true

### 4. Ejecutar la Aplicación

Una vez configurada la base de datos, compila y ejecuta el backend utilizando el Wrapper de Maven incluido en el proyecto:

# En sistemas Unix/Linux/macOS:

./mvnw spring-boot:run

# En sistemas Windows:

mvnw.cmd spring-boot:run

### 5. Ejecutar con Docker

Construye la imagen y ejecuta el contenedor con las variables de entorno de la base de datos:

```bash
docker build -t alquimia-literaria .
docker run -e DB_URL=jdbc:postgresql://localhost:5432/alquimia_db -e DB_USERNAME=tu_usuario -e DB_PASSWORD=tu_contraseña -p 8080:8080 alquimia-literaria
```

Nota: El servidor se iniciará por defecto en el puerto 8080. Puedes verificar que la API está activa accediendo a la documentación de Swagger en http://localhost:8080/swagger-ui/index.html.

# Biblioteca Spring Boot - Módulo de Categoría y Libro

Este módulo fue desarrollado para gestionar categorías y libros dentro del sistema de biblioteca. La idea principal fue permitir que los libros se registren directamente con el nombre de la categoría, evitando el uso de IDs crudos y asegurando estados por defecto.

# Funcionalidades implementadas

# Categoría

Entidad con atributos: id, nombre, descripcion.

Controlador que permite crear categorías usando nombres semánticos.

Servicio que valida duplicados y devuelve mensajes claros.

# Libro

Entidad con atributos: id, titulo, autor, estado (por defecto: "DISPONIBLE"), y relación con Categoria.

Controlador que permite registrar un libro indicando el nombre de la categoría.

Servicio que busca la categoría por nombre y enlaza automáticamente.

Ejemplo de Endpoint para agregar un libro

Request

POST /api/libro
Content-Type: application/json

{
  "autor": "Laura Gómez",
  "editorial": "Sombras Eternas",
  "titulo": "La Última Puerta",
  "isbn": "978-84-376-5678-9",
  "categoria": {
    "nombre": "Fantasía Oscura"
  }
}


Response


Instrucciones para agregar un libro

Crear la categoría (si no existe) mediante POST /api/categorias.

POST /api/categorias
Content-Type: application/json

{
  "nombre": "Novela",
  "descripcion": "Narrativa extensa en prosa"
}

Registrar el libro indicando el nombre de la categoría.

POST /api/libros
Content-Type: application/json

{
  "titulo": "El coronel no tiene quien le escriba",
  "autor": "Gabriel García Márquez",
  "categoria": "Novela"
}

El sistema asignará automáticamente el estado inicial DISPONIBLE y enlazará el libro con la categoría indicada.


# Implementación de DTOs y Documentación OpenAPI con Swagger
Descripción

Durante esta implementación se realizó una mejora estructural del backend de la API libros de Biblioteca Digital utilizando:

DTOs (Data Transfer Objects)
Documentación OpenAPI con Swagger
Validaciones y separación de responsabilidades

El objetivo principal fue mejorar la arquitectura del proyecto, evitar exponer directamente las entidades (Entity) y generar documentación automática y profesional de los endpoints REST.

# Beneficios implementados

+ Separación entre Entity y API REST
+ Mayor seguridad
+ Mejor organización del código
+ Control de datos enviados al frontend
+ Evita exponer relaciones JPA innecesarias
+ Facilita validaciones y mantenimiento

# Funciones implementadas en LibroService

# listarLibrosDTO()

Obtiene todos los libros registrados y los transforma a DTO.

* public List<LibroDTO> listarLibrosDTO()

# buscarPorId()

Busca un libro por su ID y devuelve un DTO.

* public Optional<LibroDTO> buscarPorId(Long id)

# guardarLibroDTO()

Función principal de creación y validación de libros.

* public LibroDTO guardarLibroDTO(LibroDTO libroDTO)

# Validaciones implementadas
* Título obligatorio
* Cantidad mayor a 0
* Categoría obligatoria

# Lógica implementada
* Creación automática de categorías
* Validación de libros duplicados
* Suma automática de stock
* Actualización de disponibilidad

# actualizarLibro()

Actualiza la información de un libro existente.

* public LibroDTO actualizarLibro(Long id, LibroDTO libroDTO)

# eliminarLibro()

Elimina un libro utilizando su ID.

* public void eliminarLibro(Long id)

# Implementación OpenAPI con Swagger
Objetivo

Se integró Swagger/OpenAPI para generar documentación automática e interactiva de la API REST.

# Documentación del Controller

Se implementaron anotaciones OpenAPI en LibroController.

# @Tag

Agrupa los endpoints por módulos.

* @Tag(
    name = "Libros",
    description = "Gestión completa de libros"
)

# @Operation

Describe la funcionalidad de cada endpoint.

* @Operation(
    summary = "Listar libros",
    description = "Obtiene todos los libros registrados"
)

# @ApiResponses

Documenta respuestas HTTP posibles.

* @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Operación exitosa"),
    @ApiResponse(responseCode = "404", description = "Libro no encontrado")
})

# @Parameter

Documenta parámetros enviados en URL.

* @Parameter(description = "ID único del libro")

# Endpoints documentados
Método|	Endpoint	      |    Descripción             |
------|-----------------|----------------------------|
GET	  |/api/libros	    |   Obtener todos los libros |
GET	  |/api/libros/{id}	|   Buscar libro por ID      |
POST	|/api/libros	    |   Crear libro              |
PUT	  |/api/libros/{id}	|   Actualizar libro         |
DELETE|/api/libros/{id}	|   Eliminar libro           |
 
# Swagger UI

La documentación interactiva puede visualizarse en:

http://localhost:8080/swagger-ui/index.html