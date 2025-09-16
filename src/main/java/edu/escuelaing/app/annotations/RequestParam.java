package edu.escuelaing.app.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to bind request parameters to method parameters.
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestParam {
    /**
     * The name of the request parameter.
     *
     * @return the parameter name
     */
    String value();

    /**
     * The default value if parameter is not present.
     *
     * @return the default value
     */
    String defaultValue() default "";
}