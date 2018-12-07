package com.anniefraz.dissertation.test.runner.application;

import com.anniefraz.dissertation.gin.application.Results;
import com.anniefraz.dissertation.gin.application.config.ApplicationConfig;
import com.anniefraz.dissertation.gin.patch.Patch;
import com.anniefraz.dissertation.gin.patch.PatchFactory;
import com.anniefraz.dissertation.gin.source.AnnaClass;
import com.anniefraz.dissertation.gin.source.AnnaPath;
import com.anniefraz.dissertation.gin.source.Source;
import com.anniefraz.dissertation.gin.source.SourceFactory;
import com.anniefraz.dissertation.test.runner.results.Result;
import com.anniefraz.dissertation.test.runner.results.ResultFileWriter;
import com.anniefraz.dissertation.test.runner.results.ResultWriter;
import com.anniefraz.dissertation.test.runner.runner.TestRunner;
import com.anniefraz.dissertation.test.runner.runner.UnitTest;
import com.anniefraz.dissertation.test.runner.runner.UnitTestResultSet;
import org.mdkt.compiler.CompilationException;
import org.mdkt.compiler.InMemoryJavaCompiler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class Main {

    private static int REPS = 100;
    private static int editNumberSeed = 4;
    private static int noofEditsNoRandom = 1;
    private static boolean compileSuccess;
    //private Opacitor opacitor;
    static Logger LOG = LoggerFactory.getLogger(Main.class);

    static ApplicationContext applicationContext;
    private static final String PATHNAME = "/Users/annarasburn/Documents/gin/AnnaGin/test-runner/examples/unittests/";

    public static void main(String[] args) throws IOException, Exception {
        //ApplicationContext allows to spring to properly interject beans into the application
        applicationContext = new AnnotationConfigApplicationContext(ApplicationConfig.class);

        //Configures/gets the beans from the factories
        PatchFactory patchFactory = applicationContext.getBean(PatchFactory.class);
        SourceFactory sourceFactory = new SourceFactory(Paths.get(PATHNAME));
        //SourceFactory sourceFactory = applicationContext.getBean(SourceFactory.class);



        //Gets the file we want to apply edits
        AnnaPath annaPath = AnnaPath.getBuilder().setClassName("Triangle").build();

        //Gets the source file from that anna path
        Source source = sourceFactory.getSourceFromAnnaPath(annaPath);

        //Applies a number of edits
        int noOfEdits = noofEditsNoRandom;

        compile(patchFactory, source, noOfEdits);

        ((Closeable) applicationContext).close();
    }

    private static void compile(PatchFactory patchFactory, Source source, int noOfEdits) throws Exception {
        int i = 0;
        ResultWriter resultWriter = new ResultFileWriter();
        while (i < REPS) {

            //Creation of a patch with many different edits
            Patch patch = patchFactory.getPatchForSourceWithEdits(source, noOfEdits);

            //This gives the source with the edits applied so 'changed code'
            Source outputSource = patch.getOutputSource();
            LOG.info("Output source: " + outputSource);


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
                compiledClass = InMemoryJavaCompiler.newInstance().compile("Triangle", outputFileString);
            } catch (CompilationException e){
                LOG.warn("Error in Main: {}", e);
            }

            long time;

            UnitTestResultSet unitTestResultSet = null;


            if (compiledClass == null) {
                LOG.info("DID NOT COMPILE");
                LOG.info("TIME:" + System.currentTimeMillis());
                time = System.currentTimeMillis();
                LOG.info("🤬");
                compileSuccess = false;
                i++;
            } else {
                LOG.info("Compiled Successfully");
                LOG.info("TIME:" + System.currentTimeMillis());
                time = System.currentTimeMillis();
                compileSuccess = true;
                LOG.info("😇");
                //Here I want to call the testRunner
                unitTestResultSet = sendToTestRunner(patch);
                i++;
            }

            Result result = Result.getBuilder()
                    .setCurrentRep(i)
                    .setPatch(patch)
                    .setOutputFileString(outputFileString)
                    .setTime(time)
                    .setCompiledClass(compiledClass)
                    .setCompileSuccess(compileSuccess)
                    .setPassed(Optional.ofNullable(unitTestResultSet).map(UnitTestResultSet::allTestsSuccessful).orElse(false))
                    .build();

            resultWriter.writeResult(result);
            //setResults(i, patch, outputFileString, time, compiledClass, compileSuccess, unitTestResultSet);

        }
    }

    private static void setResults(int i, Patch patch, String outputFileString, long time, Class<?> compiledClass, boolean compileSuccess, UnitTestResultSet unitTestResultSet) throws FileNotFoundException {
        Results results = new Results(i, patch, outputFileString, time, compiledClass, compileSuccess);
        results.setCurrentRep(i);
        results.setPatch(patch);
        results.setOutputFileString(outputFileString);
        results.setTime(time);
        results.setCompiledClass(compiledClass);
        results.setCompileSuccess(compileSuccess);
        results.setPassedTests(Optional.ofNullable(unitTestResultSet).map(UnitTestResultSet::allTestsSuccessful).orElse(false));
        results.writeToFile();
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

        TestRunner testRunner = new TestRunner(exampleDir, "TriangleTest", PATHNAME, tests);

        //CompiledCode triangle = InMemoryJavaCompiler.newInstance().compileToRawBytes("example.Triangle", outputString);

        return testRunner.test(patch, 1);
        /*LinkedList<UnitTestResult> unitTestResults = resultSet.getResults();
        UnitTestResult result = unitTestResults.get(0);
        for (UnitTestResult unitTestResult :
                unitTestResults) {
            System.out.println(unitTestResult.getPassed());
        }

        testRunner = new TestRunner(exampleDir, className, PATHNAME, tests);


        results.setPassedTests(result.getPassed());*/


    }
}
