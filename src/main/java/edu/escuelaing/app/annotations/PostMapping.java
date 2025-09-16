package edu.escuelaing.app.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to map HTTP POST requests to specific handler methods.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PostMapping {
    /**
     * The path pattern for this endpoint.
     *
     * @return the path pattern
     */
    String value();
}