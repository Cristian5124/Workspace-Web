package edu.escuelaing.app.core;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.escuelaing.app.http.HttpRequest;
import edu.escuelaing.app.http.HttpResponse;

/**
 * Main web server class that handles HTTP requests concurrently.
 * Provides elegant shutdown mechanism and thread pool management.
 */
public class WebServer {
    private static final int DEFAULT_PORT = 8080;
    private static final int THREAD_POOL_SIZE = 10;

    private final int port;
    private final RequestHandler requestHandler;
    private ServerSocket serverSocket;
    private ExecutorService executorService;
    private volatile boolean running = false;

    /**
     * Creates a new WebServer with default port.
     */
    public WebServer() {
        this(getPortFromEnvironment());
    }

    /**
     * Creates a new WebServer with specified port.
     *
     * @param port the port to listen on
     */
    public WebServer(int port) {
        this.port = port;
        this.requestHandler = new RequestHandler();
        this.executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
    }

    /**
     * Gets the port from environment variable or returns default.
     *
     * @return the port number
     */
    private static int getPortFromEnvironment() {
        String portEnv = System.getenv("PORT");
        if (portEnv != null && !portEnv.isEmpty()) {
            try {
                return Integer.parseInt(portEnv);
            } catch (NumberFormatException e) {
                System.out.println("Invalid PORT environment variable, using default: " + DEFAULT_PORT);
            }
        }
        return DEFAULT_PORT;
    }

    /**
     * Registers a controller class with the request handler.
     *
     * @param controllerClass the controller class to register
     */
    public void registerController(Class<?> controllerClass) {
        requestHandler.registerController(controllerClass);
    }

    /**
     * Starts the web server.
     *
     * @throws IOException if server cannot start
     */
    public void start() throws IOException {
        serverSocket = new ServerSocket(port);
        running = true;

        System.out.println("Simple web server started on port " + port + ": http://localhost:" + port + "/hello");

        Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));

        while (running) {
            try {
                Socket clientSocket = serverSocket.accept();
                executorService.submit(() -> handleClient(clientSocket));
            } catch (IOException e) {
                if (running) {
                    System.err.println("Error accepting client connection: " + e.getMessage());
                }
            }
        }
    }

    /**
     * Handles a client connection in a separate thread.
     *
     * @param clientSocket the client socket
     */
    private void handleClient(Socket clientSocket) {
        try (Socket socket = clientSocket) {
            HttpRequest request = new HttpRequest(socket.getInputStream());
            HttpResponse response = new HttpResponse();

            System.out.println("Received request: " + request.getMethod() + " " + request.getPath());

            requestHandler.handle(request, response);
            response.write(socket.getOutputStream());

        } catch (IOException e) {
            System.err.println("Error handling client request: " + e.getMessage());
        }
    }

    /**
     * Stops the web server gracefully.
     */
    public void shutdown() {
        if (!running) {
            return;
        }

        System.out.println("Shutting down server...");
        running = false;

        if (serverSocket != null && !serverSocket.isClosed()) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                System.err.println("Error closing server socket: " + e.getMessage());
            }
        }

        System.out.println("Server shutdown complete");
    }

    /**
     * Checks if the server is running.
     *
     * @return true if server is running
     */
    public boolean isRunning() {
        return running;
    }

    /**
     * Gets the server port.
     *
     * @return the port number
     */
    public int getPort() {
        return port;
    }
}