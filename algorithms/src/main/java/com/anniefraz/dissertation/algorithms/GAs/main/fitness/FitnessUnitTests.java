package com.anniefraz.dissertation.algorithms.GAs.main.fitness;

import com.anniefraz.dissertation.gin.patch.Patch;
import com.anniefraz.dissertation.test.runner.runner.TestRunner;
import com.anniefraz.dissertation.test.runner.runner.UnitTest;
import com.anniefraz.dissertation.test.runner.runner.UnitTestResultSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

public class FitnessUnitTests implements FitnessMeasurement<Patch> {

    static Logger LOG = LoggerFactory.getLogger(FitnessUnitTests.class);
    private static final String PATHNAME = "C:\\Users\\user\\IdeaProjects\\Anna-Gin\\test-runner\\examples\\unittests";


    @Override
    public double measure(Patch patch) {

        //Files
        String testClassNameTriangle = "TriangleTest";
        //String className = "Triangle";

        //Reverse String
        String testClassNameString = "ReverseStringTest";
        String className = "ReverseString";

        String testClassName = "ExampleTest";
        String methodName = "aMethod";

        File testFile = Paths.get(PATHNAME).toFile();

        //Tests SetUp
        /*
        UnitTest test = new UnitTest(testClassNameTriangle, "testInvalidTriangles");
        UnitTest test1 = new UnitTest(testClassNameTriangle, "testEqualateralTriangles");
        UnitTest test2 = new UnitTest(testClassNameTriangle, "testIsocelesTriangles");
        UnitTest test3 = new UnitTest(testClassNameTriangle, "testScaleneTriangles");
       */
        UnitTest test = new UnitTest(testClassNameString, "test1");
        UnitTest test1 = new UnitTest(testClassNameString, "test2");
        UnitTest test2 = new UnitTest(testClassNameString, "canDealWithUpperCaseTest");
        UnitTest test3 = new UnitTest(testClassNameString, "failWithNumbers");
        System.out.println(test3.toString());
        List<UnitTest> tests = new LinkedList<>();
        tests.add(test);
        tests.add(test1);
        tests.add(test2);
        tests.add(test3);

        //Getting com.anniefraz.dissertation.experiments.results
        UnitTestResultSet unitTestResultSet = null;


        //Sending to test runner
        TestRunner testRunner = new TestRunner(testFile, "ReverseStringTest", PATHNAME, tests);
        unitTestResultSet = testRunner.test(patch, 1);
        LOG.debug("Unit test: {}", unitTestResultSet);
        Boolean successful = unitTestResultSet.allTestsSuccessful();
        LOG.debug("Unit test a success?:", successful.toString());
        double result; // = successful ? 1.0 :0.0;
        if (successful.equals(true)){
            result = 1.0;
        } else{
            result = 0.0;
        }

        LOG.info("Unit test result: {}", result);
        return result;

    }
}
