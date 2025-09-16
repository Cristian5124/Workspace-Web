package edu.escuelaing.app.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller that handles HTTP requests for greetings.
 * Provides a simple greeting endpoint with optional name parameter.
 */
@RestController
public class HelloRestController {

    private static final String template = "Hello, %s!";

    /**
     * Handles GET requests to /greeting endpoint.
     *
     * @param name the name to include in the greeting (defaults to "World")
     * @return a greeting message
     */
    @GetMapping("/greeting")
    public String greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format(template, name);
    }
}