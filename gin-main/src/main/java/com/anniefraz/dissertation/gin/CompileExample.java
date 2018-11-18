package com.anniefraz.dissertation.gin;

import org.mdkt.compiler.InMemoryJavaCompiler;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

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
            System.out.println(compiledClass);
            System.out.println(compiledClass.newInstance());

            File file = new File("classes/HelloWorld.java");


        ClassLoader classLoader1 = ClassLoader.getSystemClassLoader();

        ClassLoader classLoader2 = String.class.getClassLoader();

       String resource = classLoader1.getResource("classes/HelloWorld.java").getPath();

        Path path = Paths.get(resource);



        List<String> strings = Files.readAllLines(path);
        String join = String.join(System.lineSeparator(), strings);

        String testClass = file.toString();

            StringBuffer sb = new StringBuffer();



            compiledClass2 = InMemoryJavaCompiler.newInstance().compile("HelloWorld", join);


//File.readAllLines and pass into string buffer - for source code param
        //Avoiding writing to disk
        //Alternative: call to java C
        //Its in opacitor, look at that
    }

}
