package com.anniefraz.dissertation.gin;

import org.mdkt.compiler.InMemoryJavaCompiler;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

public class CompileExample {



    public static void main(String[] args) throws Exception {

        Class<?> compiledClass = null;

        Class<?> compiledClass2 = null;

        String className = "HelloWorld";
        String source = "public class HelloWorld { " +
                "           public static void main() { " +
                "               System.out.println(\"Hello World!\");" +
                "           }" +
                "}";

        //try {

            URL classUrl = new File("~Documents/gin/AnnaGin/test/resources/testClasses/example/HelloWorld.java").toURI().toURL();
            URL[] urls = new URL[]{classUrl};
            ClassLoader classLoader = new URLClassLoader(urls, null);



            compiledClass = InMemoryJavaCompiler.newInstance().compile(className, source);
            System.out.println(compiledClass.newInstance());

        // compiledClass2 = InMemoryJavaCompiler.newInstance().compile("HelloWorld", "HelloWorld.java");


    }

}
