package com.anniefraz.dissertation.gin.application;

import com.anniefraz.dissertation.gin.application.config.ApplicationConfig;
import com.anniefraz.dissertation.gin.patch.Patch;
import com.anniefraz.dissertation.gin.patch.PatchFactory;
import com.anniefraz.dissertation.gin.source.AnnaPath;
import com.anniefraz.dissertation.gin.source.Source;
import com.anniefraz.dissertation.gin.source.SourceFactory;
import com.anniefraz.dissertation.gin.test.HotSwappingTestRunnerFactory;
import com.anniefraz.dissertation.gin.test.TestRunner;
import com.anniefraz.dissertation.gin.test.TestRunnerFactory;
import com.anniefraz.dissertation.gin.test.TestSource;
import org.hotswap.agent.config.PluginManager;
import org.mdkt.compiler.InMemoryJavaCompiler;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.stream.Collectors;

public class HotSwapTestRunnerExample {

    public static final int ITERATIONS = 100;
    public static final int MAN_NO_OF_EDITS = 8;

    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ApplicationConfig.class);

        PatchFactory patchFactory = applicationContext.getBean(PatchFactory.class);

        SourceFactory sourceFactory = applicationContext.getBean(SourceFactory.class);

        AnnaPath annaPath = AnnaPath.getBuilder().addPackage("example").setClassName("Triangle").build();

        Source source = sourceFactory.getSourceFromAnnaPath(annaPath);

        System.out.println();
        System.out.println("===============================================");
        System.out.println();
        System.out.println(source.getAnnaClasses().get(0).getJoinedLines());
        System.out.println();
        System.out.println("===============================================");
        System.out.println();
        InMemoryJavaCompiler inMemoryJavaCompiler = applicationContext.getBean(InMemoryJavaCompiler.class);

        inMemoryJavaCompiler.addSource(source.getAnnaClasses().get(0).getPath().getCanonicalName(), source.getAnnaClasses().get(0).getJoinedLines());


        AnnaPath testPath = AnnaPath.getBuilder().addPackage("example").setClassName("TriangleTest").build();
        TestSource testSource = sourceFactory.getTestSourceFromAnnaPath(testPath);

        TestRunnerFactory testRunnerFactory = new HotSwappingTestRunnerFactory(inMemoryJavaCompiler, PluginManager.getInstance());
        TestRunner testRunner = testRunnerFactory.getJunit4TestRunner(testSource);

        testRunner.run(source);

        for (int i = 0; i < ITERATIONS; i++) {

            Patch patch;
            try {
                patch = patchFactory.getPatchForSourceWithEdits(source, MAN_NO_OF_EDITS);
            } catch (IndexOutOfBoundsException e){
                System.out.println(e.getMessage());
                continue;
            }
            System.out.println(patch.getEdits().stream().map(Object::getClass).map(Class::getName).collect(Collectors.toList()));
            Source outputSource = patch.getOutputSource();

            boolean run = testRunner.run(source);
            if (run){
                System.out.println("New source generated");// + outputSource.getAnnaClasses().get(0).getJoinedLines());
                source = outputSource;
            } else {
                System.out.println("Tests/compilation failed");
            }
        }

        System.out.println();
        System.out.println("===============================================");
        System.out.println();
        System.out.println(source.getAnnaClasses().get(0).getJoinedLines());


    }
}
