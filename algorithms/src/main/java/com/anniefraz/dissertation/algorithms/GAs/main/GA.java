package com.anniefraz.dissertation.algorithms.GAs.main;

import com.anniefraz.dissertation.gin.patch.Patch;
import com.anniefraz.dissertation.gin.patch.PatchFactory;
import com.anniefraz.dissertation.gin.source.AnnaClass;
import com.anniefraz.dissertation.gin.source.Source;
import com.anniefraz.dissertation.algorithms.GAs.main.fitness.FitnessEnergy;
import com.anniefraz.dissertation.algorithms.GAs.main.fitness.FitnessMeasurement;
import com.anniefraz.dissertation.algorithms.GAs.main.fitness.FitnessUnitTests;
import com.anniefraz.dissertation.main.results.Result;
import com.anniefraz.dissertation.main.results.ResultFileWriter;
import com.anniefraz.dissertation.main.results.ResultWriter;
import com.anniefraz.dissertation.test.runner.runner.UnitTestResultSet;
import org.mdkt.compiler.InMemoryJavaCompiler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;

public class GA {
    //Logger - need to refactor so there is a logger at the top of every class
    static Logger LOG = LoggerFactory.getLogger(GA.class);

    private static int ITERATIONS = 100;
    private static int NOOFEDITS = 1; //Need to discuss with Sandy
    private static boolean COMPILATIONSUCCESFUL;

    private static ResultWriter RESULTWRITER = new ResultFileWriter();

    private static final String PATHNAME = "C:\\Users\\user\\IdeaProjects\\Anna-Gin\\test-runner\\examples\\unittests";

    private static double FITNESSSCORE = 0;

    public double firstFitness = 0;
    public double secondFitness = 0;

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
        for (int i = 0; i < patches.size(); i++){
            patchData(patches.get(i));
        }
        selection(patches.get(0), patches.get(1));

    }

    public void patchData(Patch patch) throws Exception {
        Source source1 = patch.getOutputSource();
        LOG.debug("Source:{]", source1);
        LOG.info("Edits:{} for Patch {}", patch.getEdits(), patch);
        //Go to Stage 2
        calculateFitness(source1, patch);
    }

    public LinkedList<Patch> generateABunchOfPatches(PatchFactory patchFactory, Source source, int numberOfPatches){
        LinkedList<Patch> patches = new LinkedList<>();

        for (int i = 0; i < numberOfPatches; i++){
            Patch patch = generatePatch(patchFactory, source);
            patches.add(patch);
        }
        return patches;
    }


    public Patch generatePatch (PatchFactory patchFactory, Source source){
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
        String output = String.join(System.lineSeparator(), annaClass.getLines());

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
            COMPILATIONSUCCESFUL = false;
            LOG.info("DID NOT COMPILE");
            LOG.info("TIME:{}", compileTime);
        } else {
            compileTime = System.currentTimeMillis();
            COMPILATIONSUCCESFUL = true;
            LOG.info("COMPILE");
            LOG.info("TIME:{}", compileTime);

             unitTestResult = unitTestFitnessScore(patch);

            if (unitTestResult == 1.0) {
                opacitorMeasurement = energyFitnessScore(output);
            } else {
                LOG.error("Unit tests did not pass");
                opacitorMeasurement = 10000.00;
            }

            double score =unitTestResult + opacitorMeasurement ;
            patch.setFitnessScore(score);

          //  selection(patch);


        }

        Result result = Result.getBuilder()
                //.setCurrentRep(i)
                .setPatch(patch)
                .setCompiledClass(compileSource)
                .setOpacitorMeasurement1(opacitorMeasurement)
                .setCompileSuccess(COMPILATIONSUCCESFUL)
                .setTime(compileTime)
                .setUnitTestScore(unitTestResult)
                //.setPassed(Optional.ofNullable(unitTestResultSet).map(UnitTestResultSet::allTestsSuccessful).orElse(false))
                .setOutputFileString(output)
                .build();
        RESULTWRITER.writeResult(result);

    }


    public double unitTestFitnessScore(Patch patch) throws Exception {
        FitnessMeasurement fitnessMeasurement = new FitnessUnitTests();
        double result = fitnessMeasurement.measure(patch);
        return result;
    }

    public double energyFitnessScore(String output) {
        FitnessMeasurement fitnessMeasurement = new FitnessEnergy();
        double result = fitnessMeasurement.measure(output);
        //selection();
        return result;
    }


    /*
    PHASE 3
    Purpose: to select the best individual so they pass their genes on.
    Parents are selected on fitness scores
    higher FitnessMeasurement higher Chance of being chosen
     */
    public void selection(Patch patch1, Patch patch2) { //Have a second patch to be the second parent and work out the fitness of that patch too
        firstFitness = patch1.getFitnessScore();

        //get second fitness
        double secondFitness = patch2.getFitnessScore();
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
