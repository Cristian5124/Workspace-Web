package edu.escuelaing.app.controllers;

import edu.escuelaing.app.annotations.GetMapping;
import edu.escuelaing.app.annotations.RequestParam;
import edu.escuelaing.app.annotations.RestController;

/**
 * API controller that provides REST endpoints for demonstration.
 * Shows JSON responses and various parameter handling.
 */
@RestController(value = "/api")
public class ApiController {

    /**
     * Gets user information by ID.
     *
     * @param id the user ID
     * @return user information in JSON format
     */
    @GetMapping("/user")
    public String getUser(@RequestParam("id") int id) {
        return String.format(
                "{\"id\":%d,\"name\":\"User %d\",\"email\":\"user%d@example.com\",\"active\":true}",
                id, id, id);
    }

    /**
     * Lists all users with optional limit parameter.
     *
     * @param limit maximum number of users to return
     * @return list of users in JSON format
     */
    @GetMapping("/users")
    public String listUsers(@RequestParam(value = "limit", defaultValue = "5") int limit) {
        StringBuilder json = new StringBuilder();
        json.append("{\"users\":[");

        for (int i = 1; i <= limit; i++) {
            if (i > 1)
                json.append(",");
            json.append(String.format(
                    "{\"id\":%d,\"name\":\"User %d\",\"email\":\"user%d@example.com\"}",
                    i, i, i));
        }

        json.append("],\"total\":").append(limit).append("}");
        return json.toString();
    }

    /**
     * Gets server statistics.
     *
     * @return server statistics in JSON format
     */
    @GetMapping("/stats")
    public String getStats() {
        Runtime runtime = Runtime.getRuntime();
        long totalMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        long usedMemory = totalMemory - freeMemory;

        return String.format(
                "{\"server\":{\"uptime\":%d,\"memory\":{\"total\":%d,\"used\":%d,\"free\":%d},\"processors\":%d}}",
                System.currentTimeMillis(), totalMemory, usedMemory, freeMemory, runtime.availableProcessors());
    }

    /**
     * Simple greeting endpoint for the API.
     *
     * @param lang language for greeting (optional)
     * @return greeting in JSON format
     */
    @GetMapping("/greeting")
    public String greeting(@RequestParam(value = "lang", defaultValue = "en") String lang) {
        String message;
        message = switch (lang.toLowerCase()) {
            case "es" -> "Hola desde el API personalizado!";
            case "fr" -> "Bonjour depuis l'API personnalisé!";
            default -> "Hello from the custom API!";
        };

        return String.format("{\"message\": \"%s\", \"language\": \"%s\"}", message, lang);
    }
}