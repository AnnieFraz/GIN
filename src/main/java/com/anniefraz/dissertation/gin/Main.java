package com.anniefraz.dissertation.gin;

import com.anniefraz.dissertation.gin.source.AnnaClass;
import com.anniefraz.dissertation.gin.source.AnnaPackage;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import org.mdkt.compiler.InMemoryJavaCompiler;

public class Main {

    static  Logger LOG = Logger.getLogger(Main.class.getName());

    private static final int REPS = 100;

    public static void compile_output_code(AnnaClass annaClass) throws Exception{

        Class<?> compiledClass = null;

        CompilationUnit cu = new CompilationUnit();

        Path path = annaClass.getPath().toPath();

        /*
        try {
            cu = JavaParser.parse(path);
        } catch (IOException e){
            System.out.println(e);

        }*/

        String name = annaClass.getClassName();

        compiledClass = InMemoryJavaCompiler.newInstance().compile(name,"");


    }

    public static void compilePackage(AnnaPackage annaPackage) throws Exception{
        Class<?> compiledClass = null;

        CompilationUnit cu = new CompilationUnit();




    }


    public static void main(String[] args) {
        /*
        load class
        make x edits that apply to class - edit factory to instandchate edits
        use edits to make patch - patch factory
        clone class
        patch apply to class
        compile_output_code class
        see if class runs
         */
    }












}
