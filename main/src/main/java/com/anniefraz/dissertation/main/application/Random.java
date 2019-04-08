package com.anniefraz.dissertation.main.application;

import com.anniefraz.dissertation.algorithms.GAs.main.GA;
import com.anniefraz.dissertation.gin.application.config.ApplicationConfig;
import com.anniefraz.dissertation.gin.patch.Patch;
import com.anniefraz.dissertation.gin.patch.PatchFactory;
import com.anniefraz.dissertation.gin.source.AnnaClass;
import com.anniefraz.dissertation.gin.source.AnnaPath;
import com.anniefraz.dissertation.gin.source.Source;
import com.anniefraz.dissertation.gin.source.SourceFactory;
//import com.anniefraz.dissertation.main.results.Result;
//import com.anniefraz.dissertation.main.results.ResultFileWriter;
//import com.anniefraz.dissertation.main.results.ResultWriter;
import com.anniefraz.dissertation.main.csvResults.CSVResult;
import com.anniefraz.dissertation.main.csvResults.CSVResultFileWriter;
import com.anniefraz.dissertation.main.csvResults.CSVResultWriter;
import com.anniefraz.dissertation.main.input.UserInput;
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
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class Random {

    private static int REPS = 2;
    private static int editNumberSeed = 4;
    private static int noofEditsNoRandom = 1;
    private static boolean compileSuccess;
    //private Opacitor opacitor;
    static ApplicationContext APPLICATIONCONTEXT;
    private static final Logger LOG = LoggerFactory.getLogger(Random.class);

    static ApplicationContext applicationContext;
    //private static final String PATHNAME = "/Users/annarasburn/Documents/gin/AnnaGin/test-runner/examples/unittests/";

   // private static final String PATHNAME = "C:\\Users\\user\\IdeaProjects\\Anna-Gin\\test-runner\\examples\\unittests";
   private static final String PATHNAME = "C:\\Users\\user\\IdeaProjects\\Anna-Gin\\test-runner\\examples\\unittests";

    private static CSVResultWriter RESULTWRITER;
    static {
        try {
            RESULTWRITER = new CSVResultFileWriter("RandomSearchIteration1000");
        } catch (IOException e) {
            throw new RuntimeException("Failed to run CSV", e);
        }
    }

    public static void main(String[] args) throws IOException, Exception {
        LOG.info("Please enter: 1.Class file name  2.Test file name  3.Package name  4.No.iterations  5.Initial pop size");
        if (args.length < 5) {
            LOG.error("There are not enough Parameters");
        } else {
            UserInput userInput = UserInput.getBuilder()
                    .setClassFileName(args[0])
                    .setTestFileName(args[1])
                    .setPackageName(args[2])
                    .setIterations(Integer.parseInt(args[3]))
                    .setPopulationSize(Integer.parseInt(args[4]))
                    .build();

            LOG.info("Recieved User Input: {}", userInput);
            initialise(userInput);
        }
        return;
    }

    private static void initialise(UserInput userInput) throws Exception {
        //ApplicationContext allows to spring to properly interject beans into the application
      /*  applicationContext = new AnnotationConfigApplicationContext(ApplicationConfig.class);

        //Configures/gets the beans from the factories
        PatchFactory patchFactory = applicationContext.getBean(PatchFactory.class);
        SourceFactory sourceFactory = new SourceFactory(Paths.get(PATHNAME));
        //SourceFactory sourceFactory = applicationContext.getBean(SourceFactory.class);

        //Gets the file we want to apply edits
     //   AnnaPath annaPath = AnnaPath.getBuilder().addPackage("example").setClassName("Triangle").build();

         AnnaPath annaPath = AnnaPath.getBuilder().addPackage(userInput.getPackageName()).setClassName(userInput.getClassFileName()).build();

        //Gets the source file from that anna path
        Source source = sourceFactory.getSourceFromAnnaPath(annaPath);
        //Applies a number of edits
        int noOfEdits = noofEditsNoRandom;

        compile(patchFactory, source, noOfEdits, userInput);

        ((Closeable) applicationContext).close();*/
        APPLICATIONCONTEXT = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        PatchFactory patchFactory = APPLICATIONCONTEXT.getBean(PatchFactory.class);
        SourceFactory sourceFactory = new SourceFactory(Paths.get(PATHNAME));
        //   AnnaPath annaPath = AnnaPath.getBuilder().setClassName("example.Triangle").build();

        AnnaPath annaPath = AnnaPath.getBuilder().addPackage(userInput.getPackageName()).setClassName(userInput.getClassFileName()).build();
        Source source = sourceFactory.getSourceFromAnnaPath(annaPath);
        int noOfEdits = 2;
        compile(patchFactory, source, noOfEdits, userInput);
        ((Closeable) APPLICATIONCONTEXT).close();

    }

    private static void compile(PatchFactory patchFactory, Source source, int noOfEdits, UserInput userInput) throws Exception {
        int i = 0;

        List<Patch> currentPopulation = new LinkedList<>();
        CSVResult csvResult = CSVResult.getCsvResultBuilder()
                .setIteration(0)
                .setPopulation(currentPopulation)
                .setPopulationSize(currentPopulation.size())
                .build();
        RESULTWRITER.writeResult(csvResult);

        // ResultWriter resultWriter = new ResultFileWriter("Random");
        while (i < userInput.getIterations()  ) {

            //Creation of a patch with many different edits
            Patch patch = patchFactory.getPatchForSourceWithEdits(source, noOfEdits);
            currentPopulation.add(patch);

            //This gives the source with the edits applied so 'changed code'
            Source outputSource = patch.getOutputSource();
            LOG.debug("Output source: " + outputSource);


            LOG.info("Which edits: " + patch.getEdits());

            //This needs to be in seperate method. will show to sandy tho - 12/11
            Class<?> compiledClass = null;

            //Getting the right class.
            //Maybe do as a loop for multiple classes
            List<AnnaClass> classList = outputSource.getAnnaClasses();
            AnnaClass ac = classList.get(0);

            //Changing into a string so it can be put into in memory java compiler
            String outputFileString = String.join(System.lineSeparator(), ac.getLines());
            //System.out.println(outputFileString);

            try {
                compiledClass = InMemoryJavaCompiler.newInstance().compile(userInput.getPackageName()+"."+userInput.getClassFileName(), outputFileString);
            } catch (CompilationException e){
                LOG.warn("Error in GAMain: {}", e);
            }

            long time;

            UnitTestResultSet unitTestResultSet = null;
            double unitTestScore = 12345.0;
            double measurement = 0;


            if (compiledClass == null) {
                LOG.info("DID NOT COMPILE");
                LOG.info("TIME:" + System.currentTimeMillis());
                time = System.currentTimeMillis();
                LOG.info("🤬");
                compileSuccess = false;
                unitTestScore = 1.0;
                i++;
            } else {
                LOG.info("Compiled Successfully");
                LOG.info("TIME:" + System.currentTimeMillis());
                time = System.currentTimeMillis();
                compileSuccess = true;
                LOG.info("😇");
                unitTestScore = 0.0;
                //Here I want to call the testRunner
                unitTestResultSet = sendToTestRunner(patch, userInput);
                measurement = sendToOpacitor(outputFileString, userInput);
                patch.setOpacitorMeasurement1(measurement);
                patch.setUnitTestScore(unitTestScore);
                i++;
            }
            csvResult = CSVResult.getCsvResultBuilder()
                    .setIteration(i)
                    .setPopulationSize(currentPopulation.size())
                    .setPopulation(currentPopulation)
                    .build();
            RESULTWRITER.writeResult(csvResult);
            System.out.println();
            LOG.info("Current Repetition: {}", i);
/*
           Result result = Result.getBuilder()
                    .setCurrentRep(i)
                    .setPatch(patch)
                    .setOutputFileString(outputFileString)
                    .setTime(time)
                    .setCompiledClass(compiledClass)
                    .setCompileSuccess(compileSuccess)
                    .setPassed(Optional.ofNullable(unitTestResultSet).map(UnitTestResultSet::allTestsSuccessful).orElse(false))
                    .setOpacitorMeasurement1(measurement)
                    .build();

           resultWriter.writeResult(result);*/
            //setResults(i, patch, outputFileString, time, compiledClass, compileSuccess, unitTestResultSet);

        }
    }

    private static UnitTestResultSet sendToTestRunner(Patch patch , UserInput userInput) throws Exception {

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

        TestRunner testRunner = new TestRunner(exampleDir, "TriangleTest", PATHNAME, tests);
        return testRunner.test(patch, 1);
    }
    private static double sendToOpacitor(String outputString, UserInput userInput) throws Exception {

        String testSrcDir = "C:\\Users\\user\\IdeaProjects\\Anna-Gin\\test-runner\\examples\\unittests";
       String testBinDir = "C:\\Users\\user\\IdeaProjects\\Anna-Gin\\test-runner\\target\\classes";

       // String testSrcDir = "/Users/annarasburn/Documents/gin/AnnaGin/opacitor/test_external_dir/src";
       // String testBinDir = "/Users/annarasburn/Documents/gin/AnnaGin/opacitor/test_external_dir/bin";

        //String testBinDir = "D:\\Users\\tglew\\intelliProjects\\UpdatedGin\\GIN\\opacitor\\test_external_dir\\bin\\test";
        //String testSrcDir = "D:\\Users\\tglew\\intelliProjects\\UpdatedGin\\GIN\\opacitor\\test_external_dir\\src\\test";


        String testReplacementCode = outputString;

        Opacitor opacitor = new Opacitor.OpacitorBuilder(userInput.getPackageName(), userInput.getClassFileName(), new String[]{})
                .srcDirectory(testSrcDir)
                .binDirectory(testBinDir)
                .measurementType(MeasurementType.BYTECODE_HISTOGRAM)
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
}