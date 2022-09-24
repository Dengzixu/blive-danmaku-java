package net.dengzixu.bilvedanmaku.annotation.processor;

import net.dengzixu.bilvedanmaku.annotation.DataResolver;
import net.dengzixu.bilvedanmaku.enums.Command;
import org.reflections.Reflections;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DataResolverAnnotationProcessor {
    private static final String PACKAGE_NAME = "net.dengzixu.bilvedanmaku";

    public static final Map<Command, Class<?>> COMMAND_MAP = new HashMap<>();

    static {
        Reflections reflections = new Reflections(PACKAGE_NAME);
        Set<Class<?>> annotatedClass = reflections.getTypesAnnotatedWith(DataResolver.class);

        annotatedClass.forEach(clazz -> {
            for (Command command : clazz.getAnnotation(DataResolver.class).command()) {
                COMMAND_MAP.put(command, clazz);
            }
        });
    }
}
