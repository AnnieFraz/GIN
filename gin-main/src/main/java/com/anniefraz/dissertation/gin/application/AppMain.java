package com.anniefraz.dissertation.gin.application;

import com.anniefraz.dissertation.example.application.A;
import com.anniefraz.dissertation.example.application.A2;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import org.hotswap.agent.config.PluginManager;
import org.hotswap.agent.plugin.hotswapper.HotSwapper;
import org.mdkt.compiler.*;

import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;
import java.util.*;

public class AppMain {

    public static void main(String[] args) throws Exception {
        System.out.println("In Main");

        InMemoryJavaCompiler inMemoryJavaCompiler = InMemoryJavaCompiler.newInstance();


        String bSource = "import com.anniefraz.dissertation.example.application.A;\npublic class B{ public B(){System.out.println(\"In B!\");new A();}}";
        String aSource = "package com.anniefraz.dissertation.example.application;\n" +
                "\n" +
                "public class A {\n" +
                "\n" +
                "\n" +
                "    public A(){System.out.println(\"In string A!\");}\n" +
                "}";

        CompilationUnit parse = JavaParser.parse(aSource);



        Class<?> bClass = inMemoryJavaCompiler.compile("B", bSource);
        System.out.println(bClass.getClassLoader());


        bClass.newInstance();

        HotSwapper.swapClasses(A.class, A2.class.getCanonicalName());

        bClass.newInstance();

        byte[] bytes = InMemoryJavaCompiler
                .newInstance()
                .compileToRawBytes("com.anniefraz.dissertation.example.application.A", aSource)
                .getByteCode();


        Map<Class<?>, byte[]> reloadMap = new HashMap<>();
        reloadMap.put(A.class, bytes);

        PluginManager.getInstance().hotswap(reloadMap);

        bClass.newInstance();

    }

}
