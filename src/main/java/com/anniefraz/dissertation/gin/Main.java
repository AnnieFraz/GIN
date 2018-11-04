package com.anniefraz.dissertation.gin;

import com.anniefraz.dissertation.gin.source.AnnaClass;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import org.mdkt.compiler.InMemoryJavaCompiler;

public class Main {

    static  Logger LOG = Logger.getLogger(Main.class.getName());

    private static final int REPS = 100;

    public static void main(String[] args) {

        if (args.length == 0 ){
            LOG.info("No Source File is specified");
        }
        else{

        }
    }

    public static void compile(AnnaClass annaClass) throws Exception{

        Class<?> compiledClass = null;

        CompilationUnit cu = new CompilationUnit();

        Path path = annaClass.getPath().toPath();

        /*
        try {
            cu = JavaParser.parse(path);
        } catch (IOException e){
            System.out.println(e);

        }*/

        compiledClass = InMemoryJavaCompiler.newInstance().compile("","");


    }
}
