package com.anniefraz.dissertation.main.algorithms;

import com.anniefraz.dissertation.algorithms.GAs.main.GA;
import com.anniefraz.dissertation.gin.application.config.ApplicationConfig;
import com.anniefraz.dissertation.gin.patch.Neighbour;
import com.anniefraz.dissertation.gin.patch.Offspring;
import com.anniefraz.dissertation.gin.patch.Patch;
import com.anniefraz.dissertation.gin.patch.PatchFactory;
import com.anniefraz.dissertation.gin.source.AnnaPath;
import com.anniefraz.dissertation.gin.source.Source;
import com.anniefraz.dissertation.gin.source.SourceFactory;

import com.anniefraz.dissertation.main.csvResults.CSVResult;
import com.anniefraz.dissertation.main.csvResults.CSVResultFileWriter;
import com.anniefraz.dissertation.main.csvResults.CSVResultWriter;
import com.anniefraz.dissertation.main.results.Result;
import com.anniefraz.dissertation.main.results.ResultFileWriter;
import com.anniefraz.dissertation.main.results.ResultWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.Closeable;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.LinkedList;

public class GAMain {
    private static int ITERATIONS = 10;
    private static int editNumberSeed = 4;
    private static int noofEditsNoRandom = 1;
    private static boolean compileSuccess;
    static Logger LOG = LoggerFactory.getLogger(GAMain.class);
    static ApplicationContext APPLICATIONCONTEXT;
    //private static final String PATHNAME = "/Users/annarasburn/Documents/gin/AnnaGin/test-runner/examples/unittests/";
    private static final String PATHNAME = "C:\\Users\\user\\IdeaProjects\\Anna-Gin\\test-runner\\examples\\unittests";
    private static CSVResultWriter RESULTWRITER;
   // private static GA genetic; // = new GA();

    static {
        try {
            RESULTWRITER = new CSVResultFileWriter("test2");
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
            GA ga = getGa(patchFactory, source,i);
            /*Result result = Result.getBuilder()
                    .setCurrentRep(i)
                    .setPopulation(ga.generatePatch(patchFactory, source))
                    .setCompiledClass(ga.compileSource)
                    .setOpacitorMeasurement1((ga.firstFitness - 1.0))
                    .setOpacitorMeasurement2((ga.secondFitness - 1.0))
                    .setCompileSuccess(ga.generatePatch(patchFactory, source).isSuccess())
                    .setTime(ga.generatePatch(patchFactory, source).getTime())
                    .setUnitTestScore(ga.unitTestFitnessScore(ga.generatePatch(patchFactory,source)))
                   // .setFitnessScore(ga.generatePatch(patchFactory, source).getFitnessScore())
                   // .setPassed(Optional.ofNullable(unitTestResultSet).map(UnitTestResultSet::allTestsSuccessful).orElse(false))
                    .setOutputFileString(ga.output)
                    .build();
            RESULTWRITER.writeResult(result); */

            System.out.println();
            LOG.info("CURRENT ITERATION:{} ", i + 1);
        }
        ((Closeable) APPLICATIONCONTEXT).close();
    }

    private static GA getGa(PatchFactory patchFactory, Source source, int rep ) throws Exception {

        GA genetic = new GA();
       // LinkedList<Patch> populatio
       // if (i == 0) {
          LinkedList<Patch> population = genetic.initializePopulation(patchFactory, source);
        //} else {
          //  population = (LinkedList<Patch>)secondPopulation.clone();
        //}
        LinkedList<Patch> selectedPopulation = genetic.selection(population);
        LinkedList<Offspring> offspring = genetic.crossover(selectedPopulation, source);
        LinkedList<Neighbour> neighbours = new LinkedList();
        for (int i = 0; i < offspring.size(); i++) {
            Neighbour neighbour = genetic.mutation(offspring.get(i));
            neighbours.add(i, neighbour );
        }
        LOG.info("GA DATA");
        LOG.info("Number of initial Population: {}", population.size());
        LOG.info("Number of neighbours:{}", neighbours.size());
        LOG.info("Neighbour 1 edits:{}", neighbours.get(1).getEdits());
        LOG.info("Offspring 1 edits {}", offspring.get(1).getEdits());

        //LinkedList<Patch> secondPopulation = secondPopulation(offspring, neighbours);

       // population = (LinkedList<Patch>)secondPopulation.clone();

       //genetic.secondPopulation(secondPopulation);

        CSVResult csvResult = CSVResult.getCsvResultBuilder()
                .setIteration(rep)
                .setPopulationSize(genetic.populationSize)
                .setPopulation(selectedPopulation)
                .setOffspring(offspring)
                .setNeighbour(neighbours)
                .build();
        RESULTWRITER.writeResult(csvResult);

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