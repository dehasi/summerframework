package me.summerframework.beans.factory;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import me.summerframework.beans.factory.stereotype.Component;
import me.summerframework.beans.factory.stereotype.Service;

/** Created by Ravil on 02/09/2018. */
public class BeanFactory {
    private Map<String, Object> singletons = new HashMap<>();

    public Object getBean(String name) {
        return singletons.get(name);
    }

    public void instantiate(String basePackage) {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        String path = basePackage.replace('.', '/'); //"com.kciray" -> "com/kciray"
        try {
            Enumeration<URL> resources = classLoader.getResources(path);
            Iterator<URL> iterator = resources.asIterator();
            while (iterator.hasNext()) {
                URL url = iterator.next();
                File file = new File(url.toURI());

                Arrays.stream(file.listFiles())
                    .map(File::getName)
                    .filter(name -> name.endsWith(".class"))
                    .map(name -> name.substring(0, name.lastIndexOf('.')))
                    .map(name -> getaClass(basePackage, name))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .filter(this::hasFrameworkAnnotation)
                    .map(this::createBean)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .forEach(bean -> {
                        singletons.put(extractName(bean), bean);
                    });
            }
        }
        catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private boolean hasFrameworkAnnotation(Class clazz) {
        return clazz.isAnnotationPresent(Component.class)
            || clazz.isAnnotationPresent(Service.class);
    }

    private String extractName(Object bean) {
        String className = bean.getClass().getSimpleName();
        return className.substring(0, 1).toLowerCase() + className.substring(1);
    }

    private Optional<Object> createBean(Class classObject) {
        try {
            Object instance = classObject.newInstance();
            return Optional.of(instance);
        }
        catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    private Optional<Class> getaClass(String basePackage, String className) {
        try {
            Class<?> clazz = Class.forName(basePackage + "." + className);
            return Optional.of(clazz);
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
