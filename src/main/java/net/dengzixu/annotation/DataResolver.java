package net.dengzixu.annotation;

import net.dengzixu.enums.Command;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface DataResolver {
    Command[] command();
}
