package edu.escuelaing.app;

import java.io.IOException;

import edu.escuelaing.app.controllers.HelloController;
import edu.escuelaing.app.core.WebServer;

/**
 * Main application class that starts the web server.
 * Simple Hello Docker application.
 */
public class Application {

    /**
     * Main method that starts the application.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        WebServer server = new WebServer();
        
        try {
            server.registerController(HelloController.class);
            
            System.out.println("Hello Docker Application");
            System.out.println("  GET  /hello - Returns 'Hello Docker!'");
            
            server.start();
            
        } catch (IOException e) {
            System.err.println("Failed to start server: " + e.getMessage());
            e.printStackTrace();
        }
    }
}