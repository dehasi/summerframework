package me.summerframework.beans.factory;

import java.util.HashMap;
import java.util.Map;

/** Created by Ravil on 02/09/2018. */
public class BeanFactory {
    private Map<String, Object> singletons = new HashMap<>();

    public Object getBean(String name) {
        return singletons.get(name);
    }

    public void instantiate(String basePackage) {

    }
}
