package com.anniefraz.dissertation.main.algorithms;

import com.anniefraz.dissertation.algorithms.HillClimbing.HillClimbing;
import com.anniefraz.dissertation.gin.application.config.ApplicationConfig;
import com.anniefraz.dissertation.gin.patch.Patch;
import com.anniefraz.dissertation.gin.patch.PatchFactory;
import com.anniefraz.dissertation.gin.source.AnnaPath;
import com.anniefraz.dissertation.gin.source.Source;
import com.anniefraz.dissertation.gin.source.SourceFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;

public class HCMain {

    static ApplicationContext APPLICATIONCONTEXT;
    private static final String PATHNAME = "C:\\Users\\user\\IdeaProjects\\Anna-Gin\\test-runner\\examples\\unittests";



    public static void main(String[] args) {
        APPLICATIONCONTEXT = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        PatchFactory patchFactory = APPLICATIONCONTEXT.getBean(PatchFactory.class);
        SourceFactory sourceFactory = new SourceFactory(Paths.get(PATHNAME));
        AnnaPath annaPath = AnnaPath.getBuilder().setClassName("Triangle").build();
        Source source = sourceFactory.getSourceFromAnnaPath(annaPath);


        HillClimbing hillClimbing = new HillClimbing();

        LinkedList<Patch> initialSolution = hillClimbing.generateRandomSolution(patchFactory, source);

        double[] listOfValues = new double[100];

        for (int i = 0; i < 100 ; i++) {

            LinkedList<Patch> neighbours = hillClimbing.generateRandomSolution(patchFactory, source);

            double totalNeighbourFitness = hillClimbing.evaluate(neighbours);
            double totalInitialSolutionFitness = hillClimbing.evaluate(initialSolution);

            if (totalNeighbourFitness < totalInitialSolutionFitness){
                listOfValues[i] = (totalNeighbourFitness);
                initialSolution.clear();
                initialSolution = neighbours;
            } else{
                listOfValues[i] = totalInitialSolutionFitness;
            }
        }
        }
}
