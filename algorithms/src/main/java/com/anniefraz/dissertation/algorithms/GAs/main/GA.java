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

    private static final Logger LOG = LoggerFactory.getLogger(GA.class);
    private static final String PATHNAME = "C:\\Users\\user\\IdeaProjects\\Anna-Gin\\test-runner\\examples\\unittests";

    public static int seed = 1000;
    //Logger - need to refactor so there is a logger at the top of every class

    private UserInput userInput;
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
    public List<Patch> initializePopulation(PatchFactory patchFactory, Source source) {
        //First Patch

       // int populationSize = new Random().nextInt(10)+1;
        this.populationSize = random.nextInt(10);
        LOG.info("Population Size: {}", populationSize);
        List<Patch> patches = generateABunchOfPatches(patchFactory, source, populationSize);
        for (int i = 0; i < patches.size(); i++) {
            LOG.info("Patch {} out of {}", i, populationSize);
            patchData(patches.get(i));
        }
        //Patch[] theBreedingPair = selection(patches);

       // Patch[] offspring = crossover(theBreedingPair, source);

       // mutation(offspring[1]);

        return patches;
    }
    public LinkedList<Patch> secondPopulation(LinkedList<Patch> population){
        for (int i =0; i < population.size(); i++){
            patchData(population.get(i));
        }
        return population;
    }

    public void patchData(Patch patch) {
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
        return patchFactory.getPatchForSourceWithEdits(source, 1);
    }

    /*
        PHASE 2
        Finds out how fit an individual is
         */
    public void calculateFitness(Source source, Patch patch) {
        Class<?> compileSource = null;

        List<AnnaClass> classList = source.getAnnaClasses();
        AnnaClass annaClass = classList.get(0);
        String output = String.join(System.lineSeparator(), annaClass.getLines());

        try {
           compileSource = InMemoryJavaCompiler.newInstance().compile(userInput.getPackageName()+"."+userInput.getClassFileName(), output);
        /* if (className.equals("example.Triangle")) {
             compileSource = InMemoryJavaCompiler.newInstance().compile("example.Triangle", output);
         }else if (className.equals("foo.ReverseString")) {
                compileSource = InMemoryJavaCompiler.newInstance().compile("foo.ReverseString", output);
         }*/


        } catch (Exception e) {
           // e.printStackTrace();
            LOG.error("Error in calculate fitness method", e);
        }

        long compileTime;
        UnitTestResultSet unitTestResultSet = null;
        double opacitorMeasurement = 0;
        double unitTestResult = 0.5;

        //private static int NOOFEDITS = 4; //Need to discuss with Sandy
        boolean COMPILATIONSUCCESFUL;
        if (compileSource == null) {
            compileTime = System.currentTimeMillis();
            patch.setCompileTime(compileTime);
            //COMPILATIONSUCCESFUL = false;
            patch.setCompiled(COMPILATIONSUCCESFUL = false);//todo check if this sets it to false
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

    public double unitTestFitnessScore(Patch patch) {
        FitnessMeasurement<Patch> fitnessMeasurement = new FitnessUnitTests(userInput);
        return fitnessMeasurement.measure(patch);
    }

    public double energyFitnessScore(String output) {
        FitnessMeasurement<String> fitnessMeasurement = new FitnessEnergy(userInput);
        return fitnessMeasurement.measure(output);
    }

    /*
    PHASE 3
    Purpose: to select the best individual so they pass their genes on.
    Parents are selected on fitness scores
    higher FitnessMeasurement higher Chance of being chosen
     */
    public List<Patch> selection(List<Patch> patches) { //Have a second patch to be the second parent and work out the fitness of that patch too
        List<Patch> returnPatch = new LinkedList<>();
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
            LOG.info("Patch 3 fitness: {}", patch3.getFitnessScore());
        } else {
            returnPatch.add(1,patch4);
            LOG.info("Patch 4 fitness: {}",  patch4.getFitnessScore());

        }
        return returnPatch;

    }

    /*
    PHASE 4
    Purpose: a random point of within the parents genes
    Children are made by exchanging parents genes until crossover point is reacher
     */
    public List<Offspring> crossover(List<Patch> parents, Source source) {
        Patch parent1 = parents.get(0);
        Patch parent2 = parents.get(1);
        List<Offspring>offspring = new LinkedList<>();


        List<Edit> offspringEdits1 = new LinkedList<>(parent1.getEdits());
        offspringEdits1.add(parent2.getEdits().get(new Random().nextInt(parent2.getEdits().size())));

        List<Edit> offspringEdits2 = new LinkedList<>(parent2.getEdits());
        offspringEdits2.add(parent1.getEdits().get(new Random().nextInt(parent1.getEdits().size())));

        List<Edit> offspringEdits3 = new LinkedList<>();
        offspringEdits3.addAll(parent1.getEdits());
        offspringEdits3.addAll(parent2.getEdits());

        Offspring offspring1 = new Offspring(source, offspringEdits1);
        LOG.info("Offpring 1:Patch edit list: " + offspring1.getEdits().size());
        LOG.debug("Offpring 1:Edits list size: " + offspringEdits1.size());
        offspring1.setOrigin("All of P2, bit of P1");
        patchData(offspring1);

        Offspring offspring2 = new Offspring(source, offspringEdits2);
        LOG.info("Offpring 2:Patch edit list: " + offspring2.getEdits().size());
        LOG.debug("Offpring 2:Edits list size: " + offspringEdits2.size());
        offspring2.setOrigin("All of P1, bit of P2");
        patchData(offspring2);


        Offspring offspring3 = new Offspring(source, offspringEdits3);
        LOG.info("Offpring 3:Patch edit list: " + offspring3.getEdits().size());
        LOG.debug("Offpring 3:Edits list size: " + offspringEdits3.size());
        offspring3.setOrigin("All of P2, All of P1");
        patchData(offspring3);


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

        List<Edit> edits = neighbour.getEdits();
        if (patch.getEdits().size() > 0 && patch.getFitnessScore() < 1.0 || patch.getFitnessScore() >= 24690.0) {
            edits.remove(random.nextInt(edits.size()));
        } else {
            edits.add(random.nextInt(edits.size()+1), patch.getEdits().get(random.nextInt(patch.getEdits().size())));
        }

        patchData(neighbour);
        LOG.info("new Neighbour generated: {}", neighbour);
        LOG.info("Neighbour number of Edits:{}", edits.size());
        LOG.info("Neighbour details:{}",neighbour);

        return neighbour;
    }


}
