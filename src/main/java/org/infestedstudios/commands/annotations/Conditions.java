package org.infestedstudios.commands.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Specifies conditions that must be met in order to execute this command.
 * <p>
 * If used on a method or a class, will be checked before parameter context is resolved
 * If used on a parameter, will be checked after the context is resolved
 *
 * @see {@link org.infestedstudios.core.apis.command.CommandManager#checkConditions}
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
public @interface Conditions {
    String value();
}
