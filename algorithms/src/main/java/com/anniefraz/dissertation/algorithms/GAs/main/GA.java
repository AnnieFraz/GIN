package com.anniefraz.dissertation.algorithms.GAs.main;

import com.anniefraz.dissertation.algorithms.GAs.main.crossover.CrossoverMethod;
import com.anniefraz.dissertation.algorithms.GAs.main.crossover.CrossoverThreeOffspring;
import com.anniefraz.dissertation.algorithms.GAs.main.selection.RandomSelection;
import com.anniefraz.dissertation.algorithms.GAs.main.selection.SelectionMethod;
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
import org.springframework.expression.spel.ast.Selection;
import sun.awt.image.ImageWatched;

import java.nio.file.Paths;
import java.util.*;

/**
 *
 */
public class GA {
    private static final Logger LOG = LoggerFactory.getLogger(GA.class);
    private static final String PATHNAME = "C:\\Users\\user\\IdeaProjects\\Anna-Gin\\test-runner\\examples\\unittests";
    private static int seed = 1000;
    private UserInput userInput;
    public int populationSize;
    private Random random = new Random(seed);

    public GA(UserInput userInput) {
        this.userInput = userInput;
    }

    //Methods for gaining patch data
    public void patchData(Patch patch) {
        LOG.info("🎇🎇🎇🎇PATCH🎇🎇🎇🎇");
        Source source1 = patch.getOutputSource();
        LOG.debug("Source:{]", source1);
        LOG.info("Edits:{} ", patch.getEdits());
        LOG.debug("Source {}", patch.getSource());
        calculateFitness(source1, patch); //Go to Stage 2
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

    /**
     * Phase 1: Sets the size of the population
     * One character in a binary string is a gene
     * A Binary string e.g. 0111010 is a Chromosone
     * A collection of binary strings is a population.
     *
     * @param patchFactory
     * @param source
     * @return
     */
    public List<Patch> initializePopulation(PatchFactory patchFactory, Source source) {
        this.populationSize = random.nextInt(10);
        LOG.info("Population Size: {}", populationSize);
        List<Patch> patches = generateABunchOfPatches(patchFactory, source, populationSize);
        for (int i = 0; i < patches.size(); i++) {
            LOG.info("Patch {} out of {}", i, populationSize);
            patchData(patches.get(i));
        }
        return patches;
    }

    /** PHASE 2: Finds out how fit an individual is
     * @param source
     * @param patch
     */
    public void calculateFitness(Source source, Patch patch) {
        Class<?> compileSource = null;
        List<AnnaClass> classList = source.getAnnaClasses();
        AnnaClass annaClass = classList.get(0);
        String output = String.join(System.lineSeparator(), annaClass.getLines());
        try {
            compileSource = InMemoryJavaCompiler.newInstance().compile(userInput.getPackageName() + "." + userInput.getClassFileName(), output);
        } catch (Exception e) {
            LOG.error("Error in calculate fitness method", e);
        }
        long compileTime;
        double opacitorMeasurement;
        double unitTestResult;
        if (compileSource == null) {
            compileTime = System.currentTimeMillis();
            patch.setCompileTime(compileTime);
            patch.setCompiled(false);
            LOG.info("DID NOT COMPILE");
            LOG.info("TIME:{}", compileTime);
            unitTestResult = 12345.0; //obvious bogous fitness score
            opacitorMeasurement = 12345.0;
        } else {
            compileTime = System.currentTimeMillis();
            patch.setCompileTime(compileTime);
            patch.setCompiled(true);
            LOG.info("COMPILE");
            LOG.info("TIME:{}", compileTime);
            unitTestResult = new FitnessUnitTests(userInput).measure(patch);
            if (unitTestResult == 1.0) {
                opacitorMeasurement = new FitnessEnergy(userInput).measure(output);
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

    /**PHASE 3: Purpose: to select the best individual so they pass their genes on.
     Parents are selected on fitness scores
     higher FitnessMeasurement higher Chance of being chosen
     * @param patches
     * @return
     */
    public List<Patch> selection(List<Patch> patches) { //Have a second patch to be the second parent and work out the fitness of that patch too
        RandomSelection selectionMethod = new RandomSelection();
        return selectionMethod.select(patches);
    }

    /**PHASE 4: Purpose: a random point of within the parents genes.
     * Children are made by exchanging parents genes until crossover point is reacher
     * @param parents
     * @param source
     * @return
     */
    public List<Offspring> crossover(List<Patch> parents, Source source) {
        CrossoverMethod crossoverMethod = new CrossoverThreeOffspring();
        List<Offspring> offspring = crossoverMethod.crossover(parents, source);
        for (int i = 0; i < offspring.size(); i++) {
            patchData(offspring.get(i));
        }
        return offspring;
    }

    /** PHASE 5: Where the bits are flipped
     * @param patch
     * @return
     */
    public Neighbour mutation(Offspring patch) {
        Neighbour neighbour = new Neighbour(patch);
        List<Edit> edits = neighbour.getEdits();
        if (patch.getEdits().size() > 0 && patch.getFitnessScore() < 1.0 || patch.getFitnessScore() >= 24690.0) {
            edits.remove(random.nextInt(edits.size()));
        } else {
            edits.add(random.nextInt(edits.size() + 1), patch.getEdits().get(random.nextInt(patch.getEdits().size())));
        }
        patchData(neighbour);
        LOG.info("new Neighbour generated: {}", neighbour);
        LOG.info("Neighbour number of Edits:{}", edits.size());
        LOG.info("Neighbour details:{}", neighbour);
        return neighbour;
    }
}
