package edu.escuelaing.app.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to mark a class as a REST controller.
 * Classes annotated with this will be scanned for mapping annotations.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RestController {
    /**
     * Base path for all endpoints in this controller.
     *
     * @return the base path
     */
    String value() default "";
}