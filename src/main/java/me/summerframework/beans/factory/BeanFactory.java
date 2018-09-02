package me.summerframework.beans.factory;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import me.summerframework.beans.factory.stereotype.Component;

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
            while (iterator.hasNext()){
                URL url = iterator.next();
                File file = new File(url.toURI());
                for(File classFile : file.listFiles()){
                    String fileName = classFile.getName();//ProductService.class
                    if(fileName.endsWith(".class")){
                        String className = fileName.substring(0, fileName.lastIndexOf('.'));
                        Class classObject = Class.forName(basePackage + "." + className);
                        if(classObject.isAnnotationPresent(Component.class)){
                            System.out.println("Component: " + classObject);
                            Object instance = classObject.newInstance();
                            String beanName = className.substring(0, 1).toLowerCase() + className.substring(1);
                            singletons.put(beanName, instance);
                        }
                    }
                }
            }
        }
        catch (IOException | URISyntaxException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
