package com.anniefraz.dissertation.gin.application;

import com.anniefraz.dissertation.gin.application.config.ApplicationConfig;
import com.anniefraz.dissertation.gin.patch.Patch;
import com.anniefraz.dissertation.gin.patch.PatchFactory;
import com.anniefraz.dissertation.gin.source.AnnaPath;
import com.anniefraz.dissertation.gin.source.Source;
import com.anniefraz.dissertation.gin.source.SourceFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.Closeable;
import java.io.IOException;
import java.nio.file.Paths;

public class PatchExample {


    public static void main(String[] args) throws IOException {

        System.out.println(Paths.get("").toAbsolutePath().toString());

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ApplicationConfig.class);

        PatchFactory patchFactory = applicationContext.getBean(PatchFactory.class);

        SourceFactory sourceFactory = applicationContext.getBean(SourceFactory.class);

        AnnaPath annaPath = AnnaPath.getBuilder().addPackage("example").setClassName("TestFile").build();

        Source source = sourceFactory.getSourceFromAnnaPath(annaPath);

        Patch patch = patchFactory.getPatchForSourceWithEdits(source, 4);

        Source outputSource = patch.getOutputSource();

        System.out.println(outputSource);

        ((Closeable) applicationContext).close();

        System.out.println(patch.getEdits());

        
    }

}
