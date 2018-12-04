package com.anniefraz.dissertation.gin.application;

import com.anniefraz.dissertation.gin.application.config.ApplicationConfig;
import com.anniefraz.dissertation.gin.patch.Patch;
import com.anniefraz.dissertation.gin.patch.PatchFactory;
import com.anniefraz.dissertation.gin.source.AnnaClass;
import com.anniefraz.dissertation.gin.source.AnnaPath;
import com.anniefraz.dissertation.gin.source.Source;
import com.anniefraz.dissertation.gin.source.SourceFactory;
import opacitor.Opacitor;
import opacitor.enumerations.GoalDirection;
import opacitor.enumerations.MeasurementType;
import opacitor.exceptions.FailedToCompileException;
import opacitor.exceptions.NoSuchMeasurementTypeException;
import opacitor.exceptions.UnsupportedArchitectureException;
import opacitor.exceptions.UnsupportedOSException;
import org.mdkt.compiler.CompiledCode;
import com.anniefraz.dissertation.gin.testRunner.*;
import org.mdkt.compiler.CompiledCode;
import org.mdkt.compiler.InMemoryJavaCompiler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

//import opacitor.Opacitor;

public class ApplicationMain {

    private static int REPS = 100;
    private static int editNumberSeed = 4;
    private static int noofEditsNoRandom = 1;
    private static boolean compileSuccess;
    //private Opacitor opacitor;
    private static TestRunner testRunner;
    static Logger LOG = LoggerFactory.getLogger(ApplicationMain.class);

    public static void main(String[] args) throws IOException, Exception {
        //ApplicationContext allows to spring to properly interject beans into the application
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ApplicationConfig.class);

        //Configures/gets the beans from the factories
        PatchFactory patchFactory = applicationContext.getBean(PatchFactory.class);
        SourceFactory sourceFactory = applicationContext.getBean(SourceFactory.class);

        //Gets the file we want to apply edits
        AnnaPath annaPath = AnnaPath.getBuilder().addPackage("example").setClassName("Triangle").build();

        //Gets the source file from that anna path
        Source source = sourceFactory.getSourceFromAnnaPath(annaPath);

        //random number of edits generator. Maximum is 4
        //Random random = new Random();
        // int noOfEdits = random.nextInt(editNumberSeed) + 1;
        // LOG.info("Number of Edits: " + noOfEdits);
        int noOfEdits = noofEditsNoRandom;

        compile((Closeable) applicationContext, patchFactory, source, noOfEdits);

    }

    private static void compile(Closeable applicationContext, PatchFactory patchFactory, Source source, int noOfEdits) throws Exception {
        int i = 0;
        while (i < REPS) {

            //Creation of a patch with many different edits
            Patch patch = patchFactory.getPatchForSourceWithEdits(source, noOfEdits);

            //This gives the source with the edits applied so 'changed code'
            Source outputSource = patch.getOutputSource();
            LOG.info("Output source: " + outputSource);

            applicationContext.close();

            LOG.info("Which edits: " + patch.getEdits());
            System.out.println("which edits: " + patch.getEdits());

            //This needs to be in seperate method. will show to sandy tho - 12/11
            Class<?> compiledClass = null;

            //Getting the right class.
            //Maybe do as a loop for multiple classes
            List<AnnaClass> classList = outputSource.getAnnaClasses();
            AnnaClass ac = classList.get(0);

            //Changing into a string so it can be put into in memory java compiler
            String outputFileString = String.join(System.lineSeparator(), ac.getLines());
            //System.out.println(outputFileString);

            compiledClass = InMemoryJavaCompiler.newInstance().compile("example.Triangle", outputFileString);

            //Here I want to call the testRunner
            //Here I want to call the opacitor

            long time = 0;

            if (compiledClass == null) {
                System.out.println("Didn't compile");
                System.out.println("time:" + System.currentTimeMillis());
                time = System.currentTimeMillis();
                compileSuccess = false;
                i++;
            } else {
                System.out.println("Compiled Successfully");
                System.out.println("time:" + System.currentTimeMillis());
                time = System.currentTimeMillis();
                compileSuccess = true;
                i++;
            }

            Results results = new Results(i, patch, outputFileString, time, compiledClass, compileSuccess);
            sendToTestRunner(outputFileString, patch, results);
            setResults(i, patch, outputFileString, time, compiledClass, compileSuccess);

        }
    }

    private static void setResults(int i, Patch patch, String outputFileString, long time, Class<?> compiledClass, boolean compileSuccess) throws FileNotFoundException {
        Results results = new Results(i, patch, outputFileString, time, compiledClass, compileSuccess);
        results.setCurrentRep(i);
        results.setPatch(patch);
        results.setOutputFileString(outputFileString);
        results.setTime(time);
        results.setCompiledClass(compiledClass);
        results.setCompileSuccess(compileSuccess);
        results.writeToFile();
    }

    private static void sendToTestRunner(String outputString, Patch patch, Results results) {


        String testClassNameTriangle = "TriangleTest";
        String className = "Triangle";
        String testClassName = "ExampleTest";
        String methodName = "aMethod";

        File exampleDir = new File(Configuration.TEST_RESOURCES_DIR);

        UnitTest test = new UnitTest(testClassNameTriangle, "testInvalidTriangles");
        UnitTest test1 = new UnitTest(testClassNameTriangle, "testEqualateralTriangles");
        UnitTest test2 = new UnitTest(testClassNameTriangle, "testIsocelesTriangles");
        UnitTest test3 = new UnitTest(testClassNameTriangle, "testScaleneTriangles");
        LinkedList<UnitTest> tests = new LinkedList<>();
        tests.add(test);
        tests.add(test1);
        tests.add(test2);
        tests.add(test3);
        //Here I want when I have made a new patch for it to go to the test runner automatically

        CompiledCode triangle = InMemoryJavaCompiler.newInstance().compileToRawBytes("example.Triangle", outputString);

        UnitTestResultSet resultSet = testRunner.test(patch, 1);
        LinkedList<UnitTestResult> unitTestResults = resultSet.getResults();
        UnitTestResult result = unitTestResults.get(0);
        for (UnitTestResult unitTestResult :
                unitTestResults) {
            System.out.println(unitTestResult.getPassed());
        }

        testRunner = new TestRunner(exampleDir, className, Configuration.TEST_RESOURCES_DIR, tests);


        results.setPassedTests(result.getPassed());

    }

    private void sendToOpacitor(String outputString) throws Exception {


        String testSrcDir = "C:/Users/user/IdeaProjects/AnnaGin/Opacitor/test_external_dir/src";
        String testBinDir = "C:/Users/user/IdeaProjects/AnnaGin/Opacitor/test_external_dir/bin";
        String testReplacementCode = outputString;

        Opacitor opacitor = new Opacitor.OpacitorBuilder("test", "TestClass2", new String[]{})
                .srcDirectory(testSrcDir)
                .binDirectory(testBinDir)
                .measurementType(MeasurementType.CODE_LENGTH)
                .performInitialCompilation(true)
                .goalDirection(GoalDirection.MINIMISING)
                .build();


    }

}
