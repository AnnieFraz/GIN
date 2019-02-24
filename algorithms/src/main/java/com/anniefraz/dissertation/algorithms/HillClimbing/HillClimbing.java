package com.anniefraz.dissertation.algorithms.HillClimbing;

import com.anniefraz.dissertation.algorithms.GAs.main.fitness.FitnessEnergy;
import com.anniefraz.dissertation.algorithms.GAs.main.fitness.FitnessUnitTests;
import com.anniefraz.dissertation.gin.patch.Patch;
import com.anniefraz.dissertation.gin.patch.PatchFactory;
import com.anniefraz.dissertation.gin.source.AnnaClass;
import com.anniefraz.dissertation.gin.source.Source;
import com.anniefraz.dissertation.main.input.UserInput;
import org.mdkt.compiler.InMemoryJavaCompiler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class HillClimbing {
    private static final Logger LOG = LoggerFactory.getLogger(HillClimbing.class);
    private UserInput userInput;
    private double bestScore = Double.MAX_VALUE;
    private Patch bestPatch;

    private double worstScore = -Double.MAX_VALUE;
    private Patch worstPach;

    private double score = Double.MAX_VALUE;
    private double totalScore = 0.0;

    private double previousScore = Double.MAX_VALUE;
    private Patch previousPatch;

    private static int seed = 1000;
    public int populationSize;
    private Random random = new Random(seed);

    private boolean stop = false;

    private int currentIteration = 0;

    private PatchFactory patchFactory;
    private Source source;


    public HillClimbing(UserInput userInput, PatchFactory patchFactory, Source source) {
        this.userInput = userInput;
        this.patchFactory = patchFactory;
        this.source = source;
    }

    public void patchData(Patch patch) {
        LOG.info("🎇🎇🎇🎇PATCH🎇🎇🎇🎇");
        Source source1 = patch.getOutputSource();
        LOG.debug("Source:{]", source1);
        LOG.info("Edits:{} ", patch.getEdits());
        LOG.debug("Source {}", patch.getSource());
        evaluate(patch);
    }

    public LinkedList<Patch> generateABunchOfPatches(PatchFactory patchFactory, Source source, int numberOfPatches) {
        LinkedList<Patch> patches = new LinkedList<>();
        for (int i = 0; i < numberOfPatches; i++) {
            Patch patch = generatePatch(patchFactory, source);
            patchData(patch);
            patches.add(patch);
        }
        return patches;
    }

    public Patch generatePatch(PatchFactory patchFactory, Source source) {
        return patchFactory.getPatchForSourceWithEdits(source, 1);
    }

    public void evaluate(Patch patch) {
        score = fitnessScore(patch);

        if (score < bestScore) {
            bestScore = score;
            bestPatch = patch;
            LOG.info("The best score is:{} with patch {}", bestScore, bestPatch);
        }
        if (score > worstScore) {
            worstScore = score;
            worstPach = patch;
            LOG.info("The worst score is:{} with patch {}", worstScore, worstPach);
        }
        totalScore += score;
        LOG.info("Total score: {}", totalScore);
        optimize(patch);
    }

    public void optimize(Patch patch) {
        evaluate(patch);

        while (!stop && currentIteration < userInput.getIterations()) {
            if (score < previousScore) {
                replacePrevious(score, patch);
            }
            patch = generatePatch(patchFactory, source);
            evaluate(patch);
        }
        // currentIteration++;
    }

    private void replacePrevious(double score, Patch patch) {
        previousScore = score;
        previousPatch = patch;
    }

    public double fitnessScore(Patch patch) {
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
        double result = unitTestResult + opacitorMeasurement;
        patch.setFitnessScore(result);
        patch.setUnitTestScore(unitTestResult);
        patch.setOpacitorMeasurement1(opacitorMeasurement);
        LOG.info("Patch Fitness Score:{}", patch.getFitnessScore());
        return result;
    }

    public Source getSource() {
        return source;
    }

    public PatchFactory getPatchFactory() {
        return patchFactory;
    }

    public void stop() {
        stop = true;
    }
}
