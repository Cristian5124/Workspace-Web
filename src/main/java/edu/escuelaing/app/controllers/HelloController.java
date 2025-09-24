package edu.escuelaing.app.controllers;

import edu.escuelaing.app.annotations.GetMapping;
import edu.escuelaing.app.annotations.RestController;

/**
 * Simple controller that provides a basic Hello World endpoint.
 */
@RestController
public class HelloController {

    /**
     * Basic greeting endpoint that returns "Hello World!".
     *
     * @return greeting message
     */
    @GetMapping("/hello")
    public String hello() {
        return "Hello World!";
    }
}