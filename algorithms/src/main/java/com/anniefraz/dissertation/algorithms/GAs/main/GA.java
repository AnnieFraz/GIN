package com.anniefraz.dissertation.algorithms.GAs.main;

import com.anniefraz.dissertation.gin.edit.Edit;
import com.anniefraz.dissertation.gin.patch.Neighbour;
import com.anniefraz.dissertation.gin.patch.Offspring;
import com.anniefraz.dissertation.gin.patch.Patch;
import com.anniefraz.dissertation.gin.patch.PatchFactory;
import com.anniefraz.dissertation.gin.source.AnnaClass;
import com.anniefraz.dissertation.gin.source.Source;
import com.anniefraz.dissertation.algorithms.GAs.main.fitness.FitnessEnergy;
import com.anniefraz.dissertation.algorithms.GAs.main.fitness.FitnessMeasurement;
import com.anniefraz.dissertation.algorithms.GAs.main.fitness.FitnessUnitTests;
import com.anniefraz.dissertation.main.input.UserInput;
import com.anniefraz.dissertation.test.runner.runner.UnitTestResultSet;
import org.apache.commons.text.diff.EditScript;
import org.mdkt.compiler.InMemoryJavaCompiler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.awt.image.ImageWatched;

import java.nio.file.Paths;
import java.util.*;

public class GA {
    private static final String PATHNAME = "C:\\Users\\user\\IdeaProjects\\Anna-Gin\\test-runner\\examples\\unittests";
    public static int seed = 1000;
    //Logger - need to refactor so there is a logger at the top of every class
    static Logger LOG = LoggerFactory.getLogger(GA.class);
    private static int ITERATIONS = 100;
    //private static int NOOFEDITS = 4; //Need to discuss with Sandy
    private static boolean COMPILATIONSUCCESFUL;
    private static double FITNESSSCORE = 0;
    public double firstFitness = 0;
    public double secondFitness = 0;
    public Class<?> compileSource;
    public String output;
    private static UserInput userInput;

    public int populationSize;

    private Random random = new Random(seed);

    public GA(UserInput userInput) {
        this.userInput = userInput;
    }

    /*
        PHASE 1
        Sets the size of the population
        One character in a binary string is a gene
        A Binary string e.g. 0111010 is a Chromosone
        A collection of binary strings is a population.
         */
    public LinkedList<Patch> initializePopulation(PatchFactory patchFactory, Source source) throws Exception {
        //First Patch

       // int populationSize = new Random().nextInt(10)+1;
        this.populationSize = random.nextInt(10);
        LOG.info("Population Size: {}", populationSize);
        LinkedList<Patch> patches = generateABunchOfPatches(patchFactory, source, populationSize);
        for (int i = 0; i < patches.size(); i++) {
            LOG.info("Patch {} out of {}", i, populationSize);
            patchData(patches.get(i));
        }
        //Patch[] theBreedingPair = selection(patches);

       // Patch[] offspring = crossover(theBreedingPair, source);

       // mutation(offspring[1]);

        return patches;
    }
    public LinkedList<Patch> secondPopulation(LinkedList<Patch> population) throws Exception{
        for (int i =0; i < population.size(); i++){
            patchData(population.get(i));
        }
        return population;
    }

    public void patchData(Patch patch) throws Exception {
        LOG.info("🎇🎇🎇🎇PATCH🎇🎇🎇🎇");
        Source source1 = patch.getOutputSource();
        LOG.debug("Source:{]", source1);
        LOG.info("Edits:{} ", patch.getEdits());
        LOG.debug("Source {}", patch.getSource());
        //LOG.info(patch.toString());
        //Go to Stage 2
        calculateFitness(source1, patch);
    }

    public LinkedList<Patch> generateABunchOfPatches(PatchFactory patchFactory, Source source, int numberOfPatches) {
        LinkedList<Patch> patches = new LinkedList<>();

        for (int i = 0; i < numberOfPatches; i++) {
            Patch patch = generatePatch(patchFactory, source);
            patches.add(patch);
        }
        return patches;
    }

    public Patch generatePatch(PatchFactory patchFactory, Source source) {
        Patch patch = patchFactory.getPatchForSourceWithEdits(source, 1);
        return patch;
    }

    /*
        PHASE 2
        Finds out how fit an individual is
         */
    public void calculateFitness(Source source, Patch patch) throws Exception {
        Class<?> compileSource = null;

        List<AnnaClass> classList = source.getAnnaClasses();
        AnnaClass annaClass = classList.get(0);
        output = String.join(System.lineSeparator(), annaClass.getLines());

        //System.out.println(output);

        try {
          // compileSource = InMemoryJavaCompiler.newInstance().compile(userInput.getPackageName()+"."+userInput.getClassFileName(), output);
            compileSource = InMemoryJavaCompiler.newInstance().compile("example.Triangle", output);


        } catch (Exception e) {
           // e.printStackTrace();
            LOG.error("Error in calculate fitness method: {}", e.getStackTrace());
        }

        long compileTime;
        UnitTestResultSet unitTestResultSet = null;
        double opacitorMeasurement = 0;
        double unitTestResult = 0.5;

        if (compileSource == null) {
            compileTime = System.currentTimeMillis();
            patch.setCompileTime(compileTime);
            //COMPILATIONSUCCESFUL = false;
            patch.setCompiled(COMPILATIONSUCCESFUL = false);
            LOG.info("DID NOT COMPILE");
            LOG.info("TIME:{}", compileTime);
            unitTestResult = 12345.0; //obvious bogous fitness score
            opacitorMeasurement = 12345.0;

        } else {
            compileTime = System.currentTimeMillis();
            patch.setCompileTime(compileTime);
            //COMPILATIONSUCCESFUL = true;
            patch.setCompiled(COMPILATIONSUCCESFUL = true);
            LOG.info("COMPILE");
            LOG.info("TIME:{}", compileTime);

            unitTestResult = unitTestFitnessScore(patch);

            if (unitTestResult == 1.0) {
                opacitorMeasurement = energyFitnessScore(output);
            } else {
                LOG.error("Unit tests did not pass");
                opacitorMeasurement = 10000.00;
            }
        }
        double score = unitTestResult + opacitorMeasurement;
        patch.setFitnessScore(score);
        patch.setUnitTestScore(unitTestResult);
        patch.setOpacitorMeasurement1(opacitorMeasurement);
        LOG.info("Patch Fitness Score:{}", patch.getFitnessScore());

    }

    public double unitTestFitnessScore(Patch patch) throws Exception {
        FitnessMeasurement fitnessMeasurement = new FitnessUnitTests();
        double result = fitnessMeasurement.measure(patch);
        return result;
    }

    public double energyFitnessScore(String output) {
        FitnessMeasurement fitnessMeasurement = new FitnessEnergy();
        double result = fitnessMeasurement.measure(output);
        return result;
    }

    /*
    PHASE 3
    Purpose: to select the best individual so they pass their genes on.
    Parents are selected on fitness scores
    higher FitnessMeasurement higher Chance of being chosen
     */
    public LinkedList<Patch> selection(LinkedList<Patch> patches) { //Have a second patch to be the second parent and work out the fitness of that patch too
        LinkedList<Patch> returnPatch = new LinkedList<>();
        LOG.info("Intial length of array:" + returnPatch.size());
        Patch patch1 = patches.get(random.nextInt(patches.size()));
        Patch patch2 = patches.get(random.nextInt(patches.size()));

        if (patch1.getFitnessScore() < patch2.getFitnessScore()) {
            returnPatch.add(0, patch1);
            LOG.info("Patch 1 fitness: {}", patch1.getFitnessScore());
        } else {
            returnPatch.add(0, patch2);
            LOG.info("Patch 2 fitness:{}", patch2.getFitnessScore());

        }

        Patch patch3 = patches.get(random.nextInt(patches.size()));
        Patch patch4 = patches.get(random.nextInt(patches.size()));

        if (patch3.getFitnessScore() < patch4.getFitnessScore()) {
            returnPatch.add(1,patch3);
            LOG.info("Patch 3 fitness:", patch3.getFitnessScore());
        } else {
            returnPatch.add(1,patch4);
            LOG.info("Patch 4 fitness:",  patch4.getFitnessScore());

        }
        return returnPatch;

    }

    /*
    PHASE 4
    Purpose: a random point of within the parents genes
    Children are made by exchanging parents genes until crossover point is reacher
     */
    public LinkedList<Offspring> crossover(LinkedList<Patch> parents, Source source) {
        Patch parent1 = parents.get(0);
        Patch parent2 = parents.get(1);
        LinkedList<Offspring>offspring = new LinkedList();

        int parent1ArraySize = parent1.getEdits().size();
        int parent2ArraySize = parent2.getEdits().size();

        //System.out.println(parent1ArraySize);
        //System.out.println(parent2ArraySize);

        LinkedList<Edit> offspringEdits1 = new LinkedList() ;
        LinkedList<Edit> offspringEdits2 = new LinkedList();
        LinkedList<Edit> offspringEdits3 = new LinkedList() ;

            for (int i = 0; i < parent1.getEdits().size(); i++) {
                offspringEdits1.add(parent1.getEdits().get(i));
            }
            offspringEdits2.add(parent2.getEdits().get(new Random().nextInt(parent2.getEdits().size())));

            for (int i = 0; i < parent1.getEdits().size(); i++) {
                offspringEdits2.add(parent1.getEdits().get(i));
            }
            offspringEdits2.add(parent1.getEdits().get(new Random().nextInt(parent1.getEdits().size())));

            for (int i = 0; i < parent1.getEdits().size(); i++) {
                offspringEdits3.add(parent1.getEdits().get(i));
            }
            for (int i = 0; i < parent2.getEdits().size(); i++) {
                offspringEdits3.add(parent2.getEdits().get(i));

            }

        Offspring offspring1 = new Offspring(source, offspringEdits1);
        LOG.info("Offpring 1:Patch edit list: " + offspring1.getEdits().size());
        LOG.debug("Offpring 1:Edits list size: " + offspringEdits1.size());
        offspring1.setOrigin("All of P2, bit of P1");

        Offspring offspring2 = new Offspring(source, offspringEdits2);
        LOG.info("Offpring 2:Patch edit list: " + offspring2.getEdits().size());
        LOG.debug("Offpring 2:Edits list size: " + offspringEdits2.size());
        offspring2.setOrigin("All of P1, bit of P2");


        Offspring offspring3 = new Offspring(source, offspringEdits3);
        LOG.info("Offpring 3:Patch edit list: " + offspring3.getEdits().size());
        LOG.debug("Offpring 3:Edits list size: " + offspringEdits3.size());
        offspring3.setOrigin("All of P2, All of P1");


        offspring.add(0, offspring1);
        offspring.add(1, offspring2);
        offspring.add(2, offspring3);
        return offspring;

    }

    /*
   PHASE 5
   Where the bits are flipped
    */
    public Neighbour mutation(Offspring patch) {
        Neighbour neighbour = new Neighbour(patch);

        //System.out.println(random.nextFloat());
        System.out.println(neighbour.getFitnessScore());
        neighbour.setParent(patch);

        if (neighbour.getEdits().size() > 0 && neighbour.getFitnessScore() < 1.0 || neighbour.getFitnessScore() >= 24690.0) {
            neighbour.getEdits().remove(random.nextInt(neighbour.getEdits().size()));
        } else {
            neighbour.getEdits().add(random.nextInt(), patch.getEdits().get(random.nextInt(patch.getEdits().size())));
        }

        LOG.info("Neighbour number of Edits:{}",neighbour.getEdits().size());
        LOG.info("Neighbour details:{}",neighbour);

        return neighbour;
    }


}
