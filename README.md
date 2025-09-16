# Spring Boot Docker Demo - Taller de Virtualización  

Una aplicación web sencilla construida con **Spring Boot**, desplegada en **Docker** y en **AWS EC2**. Incluye un framework web personalizado con anotaciones propias y demuestra el ciclo completo de despliegue moderno.  

![Java](https://img.shields.io/badge/Java-21+-orange.svg)  
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.3-brightgreen.svg)  
![Docker](https://img.shields.io/badge/Docker-Enabled-blue.svg)  
![AWS](https://img.shields.io/badge/AWS%20EC2-Deployed-yellow.svg)  
![License](https://img.shields.io/badge/License-MIT-green.svg)  
![Build](https://img.shields.io/badge/Build-Passing-brightgreen.svg)  

---

## Resumen del Proyecto  

Este proyecto implementa una aplicación REST usando **Spring Boot 3.3.3** con **Java 21**, diseñada para ejecutarse en **Docker** y desplegarse en **AWS EC2**.  
La aplicación permite comprender los fundamentos de **virtualización, containerización y despliegue en la nube**.  

---

## Características  

- **Framework Base**: Spring Boot 3.3.3  
- **Arquitectura**: RESTful Web Services  
- **Contenedores**: Docker y Docker Compose  
- **Base de Datos**: MongoDB (via Docker Compose)  
- **Despliegue**: Local, Docker Hub y AWS EC2  
- **Concurrencia**: Manejo automático de hilos por Spring Boot  
- **Apagado Elegante**: Shutdown hooks  

Extras:  
- **Framework Web Personalizado** (sin Spring MVC)  
- **Anotaciones Propias**: `@RestController`, `@GetMapping`, `@RequestParam`  
- **Docker Hub**: Imagen pública `cristian5124/workspace-web`  
- **Gestión de Puertos**: Configuración flexible (6000 / 8087 / 42000)  

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
mvn clean compile package
java -cp "target/classes;target/dependency/*" edu.escuelaing.app.Application
```
 Accede en: [http://localhost:6000/hello](http://localhost:6000/hello)  

### Ejecución con Docker  
```bash
docker-compose up --build
```
 Accede en: [http://localhost:8087/hello](http://localhost:8087/hello)  

### Desde Docker Hub  
```bash
docker pull cristian5124/workspace-web:latest
docker run -d -p 8087:6000 cristian5124/workspace-web:latest
```
 Accede en: [http://localhost:8087/hello](http://localhost:8087/hello)  

---

## Endpoint

| Método | Endpoint     | Parámetros         | Descripción                  |
|--------|-------------|--------------------|------------------------------|
| GET    | `/hello`    | `name` (opcional) | Retorna "Hello Docker!"      |



Ejemplo:  
```bash
curl http://localhost:6000/hello
# Respuesta: Hello Docker!
```

---

## Despliegue en AWS EC2  

1. **Conectar a instancia EC2**  
```bash
ssh -i "key.pem" ec2-user@<EC2_PUBLIC_IP>
```

2. **Instalar Docker**  
```bash
sudo yum update -y
sudo yum install docker -y
sudo service docker start
sudo usermod -a -G docker ec2-user
```

3. **Ejecutar contenedor**  
```bash
docker run -d -p 42000:6000 --name workspace-web-aws cristian5124/workspace-web:latest
```

Acceder en:  
`http://ec2-3-91-154-108.compute-1.amazonaws.com:42000/hello`  

---

## Estructura del Proyecto  

```text
spring-docker-demo/
├── src/main/java/edu/escuelaing/app/
│   ├── Application.java              # Clase principal
│   ├── annotations/                  # Anotaciones personalizadas
│   ├── controllers/                  # Controladores REST
│   ├── core/                         # Núcleo del framework
│   └── http/                         # Utilidades HTTP
├── target/                           # Binarios compilados
├── docker-compose.yml                # Configuración multi-contenedor
├── Dockerfile                        # Imagen Docker
├── pom.xml                           # Configuración Maven
└── README.md                         # Documentación
```

---

## Licencia

Este proyecto está licenciado bajo la Licencia MIT - ver el archivo [LICENSE](LICENSE) para más detalles.

## Autor

*Cristian David Polo Garrido* - Desarrollador - [GitHub](https://github.com/Cristian5124)