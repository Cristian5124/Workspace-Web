package edu.escuelaing.app.controllers;

import edu.escuelaing.app.annotations.GetMapping;
import edu.escuelaing.app.annotations.RestController;

/**
 * Simple controller that provides a basic Hello Docker endpoint.
 */
@RestController
public class HelloController {

    /**
     * Basic greeting endpoint that returns "Hello Docker!".
     *
     * @return greeting message
     */
    @GetMapping("/hello")
    public String hello() {
        return "Hello Docker!";
    }
}