package com.anniefraz.dissertation.main.algorithms;

import com.anniefraz.dissertation.algorithms.GAs.main.GA;
import com.anniefraz.dissertation.gin.application.config.ApplicationConfig;
import com.anniefraz.dissertation.gin.patch.Patch;
import com.anniefraz.dissertation.gin.patch.PatchFactory;
import com.anniefraz.dissertation.gin.source.AnnaPath;
import com.anniefraz.dissertation.gin.source.Source;
import com.anniefraz.dissertation.gin.source.SourceFactory;

import com.anniefraz.dissertation.main.results.Result;
import com.anniefraz.dissertation.main.results.ResultFileWriter;
import com.anniefraz.dissertation.main.results.ResultWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import sun.rmi.runtime.Log;

import java.io.Closeable;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.Optional;

public class Main {
    private static int ITERATIONS = 100;
    private static int editNumberSeed = 4;
    private static int noofEditsNoRandom = 1;
    private static boolean compileSuccess;
    static Logger LOG = LoggerFactory.getLogger(Main.class);
    static ApplicationContext APPLICATIONCONTEXT;
    //private static final String PATHNAME = "/Users/annarasburn/Documents/gin/AnnaGin/test-runner/examples/unittests/";
    private static final String PATHNAME = "C:\\Users\\user\\IdeaProjects\\Anna-Gin\\test-runner\\examples\\unittests";
    private static ResultWriter RESULTWRITER;
    static {
        try {
            RESULTWRITER = new ResultFileWriter("Results");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception{
        APPLICATIONCONTEXT = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        PatchFactory patchFactory = APPLICATIONCONTEXT.getBean(PatchFactory.class);
        SourceFactory sourceFactory = new SourceFactory(Paths.get(PATHNAME));
        AnnaPath annaPath = AnnaPath.getBuilder().setClassName("Triangle").build();
        Source source = sourceFactory.getSourceFromAnnaPath(annaPath);


        for (int i =0; i < ITERATIONS; i++) { //Would the reps be in initialize population or main?
           // genetic.initializePopulation(patchFactory, source);
            //STARTING THE GA
            GA genetic = getGa(patchFactory, source);
            Result result = Result.getBuilder()
                    .setCurrentRep(i)
                    .setPatch(genetic.generatePatch(patchFactory, source))
                    .setCompiledClass(genetic.compileSource)
                    .setOpacitorMeasurement1((genetic.firstFitness - 1.0))
                    .setOpacitorMeasurement2((genetic.secondFitness - 1.0))
                    .setCompileSuccess(genetic.generatePatch(patchFactory, source).isSuccess())
                    .setTime(genetic.generatePatch(patchFactory, source).getTime())
                    .setUnitTestScore(genetic.unitTestFitnessScore(genetic.generatePatch(patchFactory,source)))
                    //.setFitnessScore(genetic.generatePatch(patchFactory, source).getFitnessScore())
                    //.setPassed(Optional.ofNullable(unitTestResultSet).map(UnitTestResultSet::allTestsSuccessful).orElse(false))
                    .setOutputFileString(genetic.output)
                    .build();
            RESULTWRITER.writeResult(result);
            System.out.println();
            LOG.info("CURRENT ITERATION:{} ", i + 1);
        }
        ((Closeable) APPLICATIONCONTEXT).close();
    }

    private static GA getGa(PatchFactory patchFactory, Source source) throws Exception {
        GA genetic = new GA();
        LinkedList<Patch> population = genetic.initializePopulation(patchFactory,source);
        LinkedList<Patch> selectedPopulation = genetic.selection(population);
        LinkedList<Patch> offspring = genetic.crossover(selectedPopulation, source);
        LinkedList<Patch> neighbours = new LinkedList();
        for (int i = 0; i < offspring.size(); i++) {
            Patch neighbour = genetic.mutation(offspring.get(i));
            neighbours.add(i, neighbour );
        }
        LOG.info("GA DATA");
        LOG.info("Number of initial Population: {}", population.size());
        LOG.info("Number of neighbours:{}", neighbours.size());
        LOG.info("Neighbour 1 edits:{}", neighbours.get(1).getEdits());
        LOG.info("Offspring 1 edits {}", offspring.get(1).getEdits());

        LinkedList<Patch> secondPopulation = secondPopulation(offspring, neighbours);

        genetic.secondPopulation(secondPopulation);

        return genetic;
    }

    private static LinkedList<Patch> secondPopulation(LinkedList<Patch> offspring, LinkedList<Patch> neighbours) {
        LinkedList<Patch> nextPopulation = new LinkedList<Patch>();
        for (int i = 0; i < offspring.size() ; i++) {

            nextPopulation.add(offspring.get(i));
        }
        for (int i = 0; i < neighbours.size() ; i++) {
            nextPopulation.add(neighbours.get(i));
        }

        return nextPopulation;
    }


}