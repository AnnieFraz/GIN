package com.anniefraz.dissertation.gin.application;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CustomClassLoader extends ClassLoader {
    private static CustomClassLoader INSTANCE;

    private ClassLoader contained = null;

    public CustomClassLoader(ClassLoader classLoader) {
        super(classLoader);
        INSTANCE=this;
    }

    public static CustomClassLoader getInstance(){
        return INSTANCE;
    }


    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        System.out.println("Loading: " + name);
        if (contained == null) {
            return super.loadClass(name);
        }
        return contained.loadClass(name);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        System.out.println("Finding: " + name);
        if (contained == null) {
            return super.findClass(name);
        }
        Method findClass = null;
        try {
            findClass = ClassLoader.class.getDeclaredMethod("findClass", String.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        findClass.setAccessible(true);
        Object invoke = null;
        try {
            invoke = findClass.invoke(contained, name);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return (Class<?>) invoke;

    }


    public void setContained(ClassLoader contained) {
        this.contained = contained;
    }

}
