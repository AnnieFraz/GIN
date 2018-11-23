package com.anniefraz.dissertation.gin.application;

import com.anniefraz.dissertation.example.application.A;
import com.anniefraz.dissertation.example.application.A2;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import org.hotswap.agent.config.PluginManager;
import org.hotswap.agent.plugin.hotswapper.HotSwapper;
import org.mdkt.compiler.*;

import java.util.*;

public class HotSwapExample {

    public static void main(String[] args) throws Exception {
        System.out.println("In Main");

        InMemoryJavaCompiler inMemoryJavaCompiler = InMemoryJavaCompiler.newInstance();


        String bSource = "import com.anniefraz.dissertation.example.application.A;\npublic class B{ public B(){System.out.println(\"In B!\");new A();}}";
        String b2Source = "import com.anniefraz.dissertation.example.application.A;\npublic class B{ public B(){System.out.println(\"In B2!\");new A();}}";
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

        byte[] bytes = inMemoryJavaCompiler//InMemoryJavaCompiler.newInstance()
                .compileToRawBytes("com.anniefraz.dissertation.example.application.A", aSource)
                .getKey()
                .getByteCode();

        byte[] bBytes = inMemoryJavaCompiler//InMemoryJavaCompiler.newInstance()
                .compileToRawBytes(bClass.getCanonicalName(), b2Source)
                .getKey()
                .getByteCode();

        Map<Class<?>, byte[]> reloadMap = new HashMap<>();
        reloadMap.put(A.class, bytes);
        reloadMap.put(bClass, bBytes);

        PluginManager.getInstance().hotswap(reloadMap);

        bClass.newInstance();

    }

}
