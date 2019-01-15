package com.anniefraz.dissertation.main.algorithms;

import com.anniefraz.dissertation.gin.application.config.ApplicationConfig;
import com.anniefraz.dissertation.gin.patch.Patch;
import com.anniefraz.dissertation.gin.patch.PatchFactory;
import com.anniefraz.dissertation.gin.source.AnnaClass;
import com.anniefraz.dissertation.gin.source.AnnaPath;
import com.anniefraz.dissertation.gin.source.Source;
import com.anniefraz.dissertation.gin.source.SourceFactory;
import com.anniefraz.dissertation.main.results.Result;
import com.anniefraz.dissertation.main.results.ResultFileWriter;
import com.anniefraz.dissertation.main.results.ResultWriter;
import com.anniefraz.dissertation.test.runner.runner.TestRunner;
import com.anniefraz.dissertation.test.runner.runner.UnitTest;
import com.anniefraz.dissertation.test.runner.runner.UnitTestResultSet;
import jeep.tuple.Tuple3;
import opacitor.Opacitor;
import opacitor.enumerations.GoalDirection;
import opacitor.enumerations.MeasurementType;
import org.mdkt.compiler.CompilationException;
import org.mdkt.compiler.InMemoryJavaCompiler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.Closeable;
import java.io.File;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class GA {
    //Logger - need to refactor so there is a logger at the top of every class
    static Logger LOG = LoggerFactory.getLogger(GA.class);

    private static int ITERATIONS = 100;
    private static int NOOFEDITS = 1; //Need to discuss with Sandy
    private static boolean COMPILATIONSUCCESFUL;

    private static ResultWriter resultWriter = new ResultFileWriter();

    static ApplicationContext applicationContext;

    private static final String PATHNAME = "C:\\Users\\user\\IdeaProjects\\Anna-Gin\\test-runner\\examples\\unittests";


    /*
        PHASE 1
        Sets the size of the population
        One character in a binary string is a gene
        A Binary string e.g. 0111010 is a Chromosone
        A collection of binary strings is a population.
         */
    public static void  initializePopulation(PatchFactory patchFactory, Source source) throws Exception {

        for (int i =0; i < ITERATIONS; i++){
            Patch patch = patchFactory.getPatchForSourceWithEdits(source, NOOFEDITS);
            Source source1 = patch.getOutputSource();
            LOG.debug("Source:{]", source1);
            LOG.info("Edits:{}", patch.getEdits());
            //Go to Stage 2
            calculateFitness(source1, patch, i);
            LOG.info("CURRENT ITERATION:{}",i);
        }
    }
    /*
        PHASE 2
        Finds out how fit an individual is
         */
    public static void calculateFitness(Source source, Patch patch, int i) throws Exception {
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

        if (compileSource == null){
            compileTime = System.currentTimeMillis();
            COMPILATIONSUCCESFUL = false;
            LOG.info("DID NOT COMPILE");
            LOG.info("TIME:{}", compileTime);
        }else{
            compileTime = System.currentTimeMillis();
            COMPILATIONSUCCESFUL = true;
            LOG.info("COMPILE");
            LOG.info("TIME:{}", compileTime);

            unitTestResultSet = sendToTestRunner(patch);
            opacitorMeasurement = sendToOpacitor(output);
        }

        Result result = Result.getBuilder()
                .setCurrentRep(i)
                .setPatch(patch)
                .setCompiledClass(compileSource)
                .setOpacitorMeasurement1(opacitorMeasurement)
                .setCompileSuccess(COMPILATIONSUCCESFUL)
                .setTime(compileTime)
                .setPassed(Optional.ofNullable(unitTestResultSet).map(UnitTestResultSet::allTestsSuccessful).orElse(false))
                .setOutputFileString(output)
                .build();
        resultWriter.writeResult(result);

    }
    private static UnitTestResultSet sendToTestRunner(Patch patch) throws Exception {

        String testClassNameTriangle = "TriangleTest";
        String className = "Triangle";
        String testClassName = "ExampleTest";
        String methodName = "aMethod";

        File exampleDir = Paths.get(PATHNAME).toFile();

        UnitTest test = new UnitTest(testClassNameTriangle, "testInvalidTriangles");
        UnitTest test1 = new UnitTest(testClassNameTriangle, "testEqualateralTriangles");
        UnitTest test2 = new UnitTest(testClassNameTriangle, "testIsocelesTriangles");
        UnitTest test3 = new UnitTest(testClassNameTriangle, "testScaleneTriangles");
        List<UnitTest> tests = new LinkedList<>();
        tests.add(test);
        tests.add(test1);
        tests.add(test2);
        tests.add(test3);
        //Here I want when I have made a new patch for it to go to the test runner automatically

        TestRunner testRunner = new TestRunner(exampleDir, "TriangleCPUTest", PATHNAME, tests);
        return testRunner.test(patch, 1);
    }
    private static double sendToOpacitor(String outputString) throws Exception {

        String testSrcDir = "C:\\Users\\user\\IdeaProjects\\Anna-Gin\\opacitor\\test_external_dir\\src\\test";
        String testBinDir = "C:\\Users\\user\\IdeaProjects\\Anna-Gin\\Opacitor\\test_external_dir\\bin\\test";

        // String testSrcDir = "/Users/annarasburn/Documents/gin/AnnaGin/opacitor/test_external_dir/src";
        // String testBinDir = "/Users/annarasburn/Documents/gin/AnnaGin/opacitor/test_external_dir/bin";

        //String testBinDir = "D:\\Users\\tglew\\intelliProjects\\UpdatedGin\\GIN\\opacitor\\test_external_dir\\bin\\test";
        //String testSrcDir = "D:\\Users\\tglew\\intelliProjects\\UpdatedGin\\GIN\\opacitor\\test_external_dir\\src\\test";


        String testReplacementCode = outputString;

        Opacitor opacitor = new Opacitor.OpacitorBuilder("", "Triangle", new String[]{})
                .srcDirectory(testSrcDir)
                .binDirectory(testBinDir)
                .measurementType(MeasurementType.CODE_LENGTH)
                .performInitialCompilation(true)
                .goalDirection(GoalDirection.MINIMISING)
                .compiler(Paths.get("C:\\Program Files\\Java\\jdk1.8.0_191\\bin\\javac.exe").toAbsolutePath().toString())
                .build();

        double measurement;

        opacitor.updateCode(Collections.singletonList(new Tuple3<>(testReplacementCode, "", "Triangle")));
        measurement = opacitor.fitness(new String[]{"test1.txt", "1000", "10000"});
        LOG.info("Measurement: {}", measurement);

        double measurement2 = opacitor.fitness(new String[]{"test1.txt", "1000", "10000"});
        LOG.info("Measurement: {}", measurement2);

        return measurement;

    }

    /*
    PHASE 3
    Purpose: to select the best individual so they pass their genes on.
    Parents are selected on fitness scores
    higher Fitness higher Chance of being chosen
     */
    public static void selection(){

    }
    /*
    PHASE 4
    Purpose: a random point of within the parents genes
    Children are made by exchanging parents genes until crossover point is reacher
     */
    public static void crossover(){

    }
    /*
   PHASE 5
   Where the bits are flipped
    */
    public static void mutation(){

    }

    public static void main(String[] args) throws Exception{
        applicationContext = new AnnotationConfigApplicationContext(ApplicationConfig.class);

        PatchFactory patchFactory = applicationContext.getBean(PatchFactory.class);
        SourceFactory sourceFactory = new SourceFactory(Paths.get(PATHNAME));

        AnnaPath annaPath = AnnaPath.getBuilder().setClassName("Triangle").build();

        Source source = sourceFactory.getSourceFromAnnaPath(annaPath);

        //STARTING THE GA
        initializePopulation(patchFactory, source);

        ((Closeable) applicationContext).close();
    }
}
