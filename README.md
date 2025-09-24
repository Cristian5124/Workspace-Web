# Simple Web Server - Taller de Virtualización

Una aplicación web simple construida con Java, sin frameworks externos, desplegada en Docker y en Amazon AWS EC2.

![Java](https://img.shields.io/badge/Java-21+-orange.svg)  
![No Framework](https://img.shields.io/badge/Framework-None-red.svg)  
![Docker](https://img.shields.io/badge/Docker-Enabled-blue.svg)  
![License](https://img.shields.io/badge/License-MIT-green.svg)  
![Build](https://img.shields.io/badge/Build-Passing-brightgreen.svg)

---

## Resumen del Proyecto

Este proyecto implementa un servidor web HTTP simple usando Java 21 , sin frameworks externos como Spring Boot. Diseñado para ejecutarse en Docker y demostrar cómo construir aplicaciones web simples.  
La aplicación permite comprender los fundamentos de servidores HTTP, containerización y desarrollo sin dependencias.

---

## Características

- Servidor HTTP Personalizado: Implementación propia con `ServerSocket`
- Endpoint Único: Simple endpoint `/hello` que retorna "Hello World!"
- Containerización: Docker con configuración optimizada
- Puerto Estándar: 8080 (configurable vía variable de entorno)
- Concurrencia: Pool de threads para manejo de múltiples requests
- Apagado Elegante: Shutdown hooks para cierre limpio

Características técnicas:

- Anotaciones Personalizadas: `@RestController`, `@GetMapping`
- Arquitectura Simple: Servidor + Controlador + Handler
- Zero Dependencies: Solo JUnit para testing
- Gestión de Puertos: Puerto 8080 por defecto, configurable

---

## Arquitectura del Sistema

```text
┌─────────────────────┐    ┌─────────────────┐    ┌──────────────────┐
│    Application      │───▶│   WebServer     │───▶│ RequestHandler   │
│   (Entry Point)     │    │ (HTTP Server)   │    │ (Route Handling) │
└─────────────────────┘    └─────────────────┘    └──────────────────┘

        ▼
┌─────────────────┐    ┌──────────────────┐
│   Controllers   │───▶│   Annotations    │
│ (Hello Logic)   │    │ (@RestController)│
└─────────────────┘    └──────────────────┘

        ▼
┌─────────────────┐    ┌──────────────────┐    ┌─────────────────┐
│     Docker      │───▶│      AWS EC2     │    │   Web Browser   │
│  (Container)    │    │   (Production)   │◄───│   (Client)      │
└─────────────────┘    └──────────────────┘    └─────────────────┘
```

---

## Inicio Rápido

### Prerrequisitos

- [Java 21+](https://adoptium.net/)
- [Maven 3.8+](https://maven.apache.org/)
- [Docker](https://docs.docker.com/get-docker/)
- [AWS CLI](https://aws.amazon.com/cli/) (opcional)

### Ejecución Local

```bash
# Compilar el proyecto
mvn clean compile

# Ejecutar la aplicación
java -cp "./target/classes" edu.escuelaing.app.Application
```

Accede en: [http://localhost:8080/hello](http://localhost:8080/hello)

### Ejecución con Docker

```bash
# Construir la imagen
docker build -t simple-web-server .

# Ejecutar el contenedor
docker run -p 8080:8080 simple-web-server
```

Accede en: [http://localhost:8080/hello](http://localhost:8080/hello)

### Ejecución en segundo plano

```bash
# Ejecutar en modo detached
docker run -d -p 8080:8080 --name simple-web simple-web-server

# Ver logs
docker logs simple-web

# Detener contenedor
docker stop simple-web
```

---

## Endpoint

| Método | Endpoint | Parámetros | Descripción            |
| ------ | -------- | ---------- | ---------------------- |
| GET    | `/hello` | Ninguno    | Retorna "Hello World!" |

Ejemplo:

```bash
curl http://localhost:8080/hello
# Respuesta: Hello World!
```

Navegador: [http://localhost:8080/hello](http://localhost:8080/hello)

---

## Configuración de Puertos

La aplicación utiliza el puerto 8080 por defecto, pero es configurable:

```bash
# Puerto por defecto (8080)
docker run -p 8080:8080 simple-web-server

# Puerto personalizado usando variable de entorno
docker run -p 3000:3000 -e PORT=3000 simple-web-server

# En ejecución local con puerto personalizado
export PORT=9000
java -cp "./target/classes" edu.escuelaing.app.Application
```

---

## Estructura del Proyecto

```text
simple-web-server/
├── src/main/java/edu/escuelaing/app/
│   ├── Application.java
│   ├── annotations/
│   │   ├── RestController.java
│   │   ├── GetMapping.java
│   │   └── RequestParam.java
│   ├── controllers/
│   │   └── HelloController.java
│   ├── core/
│   │   ├── WebServer.java
│   │   └── RequestHandler.java
│   └── http/
│       ├── HttpRequest.java
│       └── HttpResponse.java
├── target/
├── Dockerfile
├── pom.xml
└── README.md
```

---

## Comandos de Desarrollo

```bash
# Compilar proyecto
mvn clean compile

# Ejecutar tests (si los hay)
mvn test

# Empaquetar aplicación
mvn package

# Limpiar proyecto
mvn clean

# Construir imagen Docker
docker build -t simple-web-server .

# Ver logs de contenedor en tiempo real
docker logs -f <container_id>
```

---

## Licencia

Este proyecto está licenciado bajo la Licencia MIT - ver el archivo [LICENSE](LICENSE) para más detalles.

## Autor

Cristian David Polo Garrido - Desarrollador - [GitHub](https://github.com/Cristian5124)
