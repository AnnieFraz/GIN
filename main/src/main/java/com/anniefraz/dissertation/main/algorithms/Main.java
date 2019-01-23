package com.anniefraz.dissertation.main.algorithms;

import com.anniefraz.dissertation.algorithms.GAs.main.GA;
import com.anniefraz.dissertation.gin.application.config.ApplicationConfig;
import com.anniefraz.dissertation.gin.patch.PatchFactory;
import com.anniefraz.dissertation.gin.source.AnnaPath;
import com.anniefraz.dissertation.gin.source.Source;
import com.anniefraz.dissertation.gin.source.SourceFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.Closeable;
import java.nio.file.Paths;

public class Main {

    private static int ITERATIONS = 100;
    private static int editNumberSeed = 4;
    private static int noofEditsNoRandom = 1;
    private static boolean compileSuccess;
    //private Opacitor opacitor;
    static Logger LOG = LoggerFactory.getLogger(Main.class);

    static ApplicationContext APPLICATIONCONTEXT;
    //private static final String PATHNAME = "/Users/annarasburn/Documents/gin/AnnaGin/test-runner/examples/unittests/";

    private static final String PATHNAME = "C:\\Users\\user\\IdeaProjects\\Anna-Gin\\test-runner\\examples\\unittests";


    public static void main(String[] args) throws Exception{
        APPLICATIONCONTEXT = new AnnotationConfigApplicationContext(ApplicationConfig.class);

        PatchFactory patchFactory = APPLICATIONCONTEXT.getBean(PatchFactory.class);
        SourceFactory sourceFactory = new SourceFactory(Paths.get(PATHNAME));

        AnnaPath annaPath = AnnaPath.getBuilder().setClassName("Triangle").build();

        Source source = sourceFactory.getSourceFromAnnaPath(annaPath);

        //STARTING THE GA
        GA genetic = new GA();
        for (int i =0; i < ITERATIONS; i++) { //Would the reps be in initialize poulation or main?
            genetic.initializePopulation(patchFactory, source, i);
        }

        ((Closeable) APPLICATIONCONTEXT).close();
    }
}