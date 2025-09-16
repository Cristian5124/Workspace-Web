package edu.escuelaing.app;

import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Spring Boot application class.
 * Entry point that starts the Spring Boot web server with custom port
 * configuration.
 */
@SpringBootApplication
public class RestServiceApplication {

    /**
     * Main method that starts the Spring Boot application.
     * Configures server port from environment variable or uses default.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(RestServiceApplication.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", getPort()));

        System.out.println("=".repeat(50));
        System.out.println("Spring Boot Docker Demo Application");
        System.out.println("=".repeat(50));
        System.out.println("Available endpoints:");
        System.out.println("  GET  /greeting?name=<name>  - Greeting with custom name");
        System.out.println("Port: " + getPort());
        System.out.println("=".repeat(50));

        app.run(args);
    }

    /**
     * Gets the server port from environment variable or returns default.
     * Reads PORT environment variable for deployment flexibility.
     *
     * @return the port number to use
     */
    private static int getPort() {
        if (System.getenv("PORT") != null) {
            try {
                return Integer.parseInt(System.getenv("PORT"));
            } catch (NumberFormatException e) {
                System.out.println("Invalid PORT environment variable, using default: 5000");
            }
        }
        return 5000;
    }
}