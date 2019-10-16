package model;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for method in game that can be run from the view.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface UserCommand {
    /**
     * Get the description of the method. This description is meant for user to read from a help call in the view.
     *
     * @return The method description
     */
    String description();
}