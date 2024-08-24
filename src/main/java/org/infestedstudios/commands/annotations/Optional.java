package org.infestedstudios.commands.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks the parameter this is attached to as optional.
 * This will set the parameter as null if it was not provided.
 * In the case the language used is Kotlin, Ceylon or any other null-enforcing JVM language,
 * you will need to allow for a nullable value.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
public @interface Optional {
}
