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

<img width="1919" height="362" alt="image" src="https://github.com/user-attachments/assets/c4f52bc8-8cb5-4191-abda-65ffef7910a2" />

Accede en: [http://localhost:8080/hello](http://localhost:8080/hello)

<img width="1844" height="299" alt="image" src="https://github.com/user-attachments/assets/c655d1cb-4fa1-4965-9899-c25ceb52ac12" />

### Ejecución con Docker

```bash
# Construir la imagen
docker build -t simple-web-server .

# Ejecutar el contenedor
docker run -p 8080:8080 simple-web-server
```

<img width="1915" height="356" alt="image" src="https://github.com/user-attachments/assets/b1ee4af1-46b2-4ac4-be45-bd2945067ad2" />

Accede en: [http://localhost:8080/hello](http://localhost:8080/hello)

<img width="1844" height="299" alt="image" src="https://github.com/user-attachments/assets/c655d1cb-4fa1-4965-9899-c25ceb52ac12" />

### Ejecución en Docker Hub

```bash
docker pull cristian5124/simple-web-server:latest
docker run -p 9090:8080 cristian5124/simple-web-server:latest
```

<img width="1916" height="1143" alt="image" src="https://github.com/user-attachments/assets/c7fd83a9-a4a4-495a-bab7-2b50c74d390d" />


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

## Despliegue en AWS EC2

 1. Conectar a instancia EC2

```bash
ssh -i "key.pem" ec2-user@<EC2_PUBLIC_IP>
```

 2. Instalar Docker
```bash
sudo yum update -y
sudo yum install docker -y
sudo service docker start
sudo usermod -a -G docker ec2-user
```

 3. Ejecutar contenedor
```bash
docker run -d -p 42000:6000 --name simple-web-server-aws cristian5124/simple-web-server:latest
```

 4. Acceder en:
`http://ec2-3-91-154-108.compute-1.amazonaws.com:42000/hello`
---

## Video con Despliegues

https://github.com/user-attachments/assets/c8fc78c2-66a0-44c7-b763-cacdfcadf8fd

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
