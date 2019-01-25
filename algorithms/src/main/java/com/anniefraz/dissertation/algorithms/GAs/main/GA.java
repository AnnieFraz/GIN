package com.anniefraz.dissertation.algorithms.GAs.main;

import com.anniefraz.dissertation.gin.patch.Patch;
import com.anniefraz.dissertation.gin.patch.PatchFactory;
import com.anniefraz.dissertation.gin.source.AnnaClass;
import com.anniefraz.dissertation.gin.source.Source;
import com.anniefraz.dissertation.algorithms.GAs.main.fitness.FitnessEnergy;
import com.anniefraz.dissertation.algorithms.GAs.main.fitness.FitnessMeasurement;
import com.anniefraz.dissertation.algorithms.GAs.main.fitness.FitnessUnitTests;
import com.anniefraz.dissertation.test.runner.runner.UnitTestResultSet;
import org.mdkt.compiler.InMemoryJavaCompiler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class GA {
    //Logger - need to refactor so there is a logger at the top of every class
    static Logger LOG = LoggerFactory.getLogger(GA.class);

    private static int ITERATIONS = 100;
    private static int NOOFEDITS = 1; //Need to discuss with Sandy
    private static boolean COMPILATIONSUCCESFUL;

    private static final String PATHNAME = "C:\\Users\\user\\IdeaProjects\\Anna-Gin\\test-runner\\examples\\unittests";

    private static double FITNESSSCORE = 0;

    public double firstFitness = 0;
    public double secondFitness = 0;

    public Class<?> compileSource;

    public String output;

    /*
        PHASE 1
        Sets the size of the population
        One character in a binary string is a gene
        A Binary string e.g. 0111010 is a Chromosone
        A collection of binary strings is a population.
         */
    public void initializePopulation(PatchFactory patchFactory, Source source, int iteration) throws Exception {
        //First Patch
        LOG.info("CURRENT ITERATION:{} ", iteration);
        LinkedList<Patch> patches = generateABunchOfPatches(patchFactory, source, 2);
        for (int i = 0; i < patches.size(); i++) {
            patchData(patches.get(i));
        }
        Patch[] theBreedingPair = selection(patches);

    }

    public void patchData(Patch patch) throws Exception {
        LOG.info("🎇🎇🎇🎇PATCH🎇🎇🎇🎇");
        Source source1 = patch.getOutputSource();
        LOG.debug("Source:{]", source1);
        LOG.info("Edits:{} ", patch.getEdits());
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
        Patch patch = patchFactory.getPatchForSourceWithEdits(source, NOOFEDITS);
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

        try {
            compileSource = InMemoryJavaCompiler.newInstance().compile("Triangle", output);

        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("Error in calculate fitness method: {}", e);
        }

        long compileTime;
        UnitTestResultSet unitTestResultSet = null;
        double opacitorMeasurement = 0;
        double unitTestResult = 0.5;

        if (compileSource == null) {
            compileTime = System.currentTimeMillis();
            patch.setTime(compileTime);
            //COMPILATIONSUCCESFUL = false;
            patch.setSuccess(COMPILATIONSUCCESFUL = false);
            LOG.info("DID NOT COMPILE");
            LOG.info("TIME:{}", compileTime);
            unitTestResult = 12345.0; //obvious bogous fitness score
            opacitorMeasurement = 12345.0;

        } else {
            compileTime = System.currentTimeMillis();
            patch.setTime(compileTime);
            //COMPILATIONSUCCESFUL = true;
            patch.setSuccess(COMPILATIONSUCCESFUL = true);
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
    public Patch[] selection(LinkedList<Patch> patches) { //Have a second patch to be the second parent and work out the fitness of that patch too
        Random random = new Random();
        Patch[] returnPatch = new Patch[2];
        ArrayList<Patch> returnedPatches = new ArrayList<Patch>();
        LOG.info("Intial length of array:"+ returnPatch.length);
        int i = 0;
       // while (returnedPatches.size() < 2) {
            Patch patch1 = patches.get(random.nextInt(patches.size()));
            Patch patch2 = patches.get(random.nextInt(patches.size()));

            if (patch1.getFitnessScore() < patch2.getFitnessScore()) {
                returnPatch[0] = patch1;
                //returnedPatches.add(patch1);
                LOG.info("patch1 fitness:"+ patch1.getFitnessScore());
            } else {
               returnPatch[0] = patch2;
               // returnedPatches.add(patch2);
                LOG.info("patch2 fitness:"+ patch2.getFitnessScore());

            }
        Patch patch3 = patches.get(random.nextInt(patches.size()));
        Patch patch4 = patches.get(random.nextInt(patches.size()));

        if (patch3.getFitnessScore() < patch4.getFitnessScore()) {
            returnPatch[1] = patch3;
            //returnedPatches.add(patch1);
            LOG.info("patch3 fitness:"+ patch3.getFitnessScore());
        } else {
            returnPatch[1] = patch4;
            // returnedPatches.add(patch2);
            LOG.info("patch4 fitness:"+ patch4.getFitnessScore());

        }
           // i++;
           // LOG.info("Current length of array:"+ returnedPatches.size());
            //LOG.info("patch 1:" + returnedPatches.get(0).getFitnessScore());
            //LOG.info("patch 1:" + returnedPatches.get(1).getFitnessScore());

            //LOG.info("Current length of array:"+ returnPatch[1]);
        //}

        return returnPatch;

    }

    /*
    PHASE 4
    Purpose: a random point of within the parents genes
    Children are made by exchanging parents genes until crossover point is reacher
     */
    public void crossover() {

    }

    /*
   PHASE 5
   Where the bits are flipped
    */
    public static void mutation() {

    }


}
