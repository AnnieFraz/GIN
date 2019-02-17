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
import com.anniefraz.dissertation.main.input.UserInput;

import com.anniefraz.dissertation.main.csvResults.CSVResult;
import com.anniefraz.dissertation.main.csvResults.CSVResultFileWriter;
import com.anniefraz.dissertation.main.csvResults.CSVResultWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.Closeable;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.LinkedList;

public class GAMain {
    private static int ITERATIONS = 1;
    private static int editNumberSeed = 4;
    private static int noofEditsNoRandom = 1;
    private static boolean compileSuccess;
    static Logger LOG = LoggerFactory.getLogger(GAMain.class);
    static ApplicationContext APPLICATIONCONTEXT;
    //private static final String PATHNAME = "/Users/annarasburn/Documents/gin/AnnaGin/test-runner/examples/unittests/";
    private static final String PATHNAME = "C:\\Users\\user\\IdeaProjects\\Anna-Gin\\test-runner\\examples\\unittests";
    private static CSVResultWriter RESULTWRITER;
    private static UserInput userInput = new UserInput();
    private static GA genetic; // = new GA();

    static {
        try {
            RESULTWRITER = new CSVResultFileWriter("test");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        if (args.length < 4) {
            LOG.error("There are not enough Parameters");
        } else {
            userInput.setClassFileName(args[0]);
            userInput.setTestFileName(args[1]);
            userInput.setPackageName(args[2]);
            userInput.setIterations(Integer.parseInt(args[3]));

            System.out.println(args[0]);
            System.out.println(args[1]);
            System.out.println(args[2]);
            System.out.println(args[3]);
            initialise();
        }
    }

    public static void initialise() throws Exception {
        APPLICATIONCONTEXT = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        PatchFactory patchFactory = APPLICATIONCONTEXT.getBean(PatchFactory.class);
        SourceFactory sourceFactory = new SourceFactory(Paths.get(PATHNAME));
        AnnaPath annaPath = AnnaPath.getBuilder().addPackage(userInput.getPackageName()).setClassName(userInput.getClassFileName()).build();
        Source source = sourceFactory.getSourceFromAnnaPath(annaPath);
        GA genetic = new GA(userInput);
        LinkedList<Patch> startPopulation = genetic.initializePopulation(patchFactory, source);
        GA ga = getGa(patchFactory, source, startPopulation);
        ((Closeable) APPLICATIONCONTEXT).close();
    }

    private static GA getGa(PatchFactory patchFactory, Source source, LinkedList<Patch> population) throws Exception {

        for (int i = 0; i < userInput.getIterations(); i++) { //Would the reps be in initialize population or main?
            LinkedList<Patch> selectedPopulation = genetic.selection(population);
            LinkedList<Offspring> offspring = genetic.crossover(selectedPopulation, source);
            LinkedList<Neighbour> neighbours = new LinkedList();
            for (int child = 0; child < offspring.size(); child++) {
                Neighbour neighbour = genetic.mutation(offspring.get(child));
                neighbours.add(child, neighbour);
            }
            LOG.info("GA DATA");
            LOG.info("Number of initial Population: {}", population.size());
            LOG.info("Number of neighbours:{}", neighbours.size());
            LOG.info("Neighbour 1 edits:{}", neighbours.get(1).getEdits());
            LOG.info("Offspring 1 edits {}", offspring.get(1).getEdits());

            CSVResult csvResult = CSVResult.getCsvResultBuilder()
                    .setIteration(i)
                    .setPopulationSize(genetic.populationSize)
                    .setPopulation(selectedPopulation)
                    .setOffspring(offspring)
                    .setNeighbour(neighbours)
                    .build();
            RESULTWRITER.writeResult(csvResult);

            LinkedList<Patch> newPopulation = secondPopulation(offspring, neighbours);
            population.clear();
            for (int j = 0; j < newPopulation.size(); j++) {
                population.add(newPopulation.get(j));
            }
            //population = newPopulation;
            System.out.println();
            LOG.info("CURRENT ITERATION:{} ", i + 1);
        }

        return genetic;
    }

    private static LinkedList<Patch> secondPopulation(LinkedList<Offspring> offspring, LinkedList<Neighbour> neighbours) {
        LinkedList<Patch> nextPopulation = new LinkedList<Patch>();
        for (int i = 0; i < offspring.size(); i++) {

            nextPopulation.add(offspring.get(i));
        }
        for (int i = 0; i < neighbours.size(); i++) {
            nextPopulation.add(neighbours.get(i));
        }

        return nextPopulation;
    }


}