package net.dengzixu.bilvedanmaku.annotation;

import net.dengzixu.bilvedanmaku.enums.Command;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface DataResolver {
    Command[] command();
}
