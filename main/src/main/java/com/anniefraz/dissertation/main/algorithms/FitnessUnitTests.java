package com.anniefraz.dissertation.main.algorithms;

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
        String className = "Triangle";

        String testClassName = "ExampleTest";
        String methodName = "aMethod";

        File testFile = Paths.get(PATHNAME).toFile();

        //Tests SetUp
        UnitTest test = new UnitTest(testClassNameTriangle, "testInvalidTriangles");
        UnitTest test1 = new UnitTest(testClassNameTriangle, "testEqualateralTriangles");
        UnitTest test2 = new UnitTest(testClassNameTriangle, "testIsocelesTriangles");
        UnitTest test3 = new UnitTest(testClassNameTriangle, "testScaleneTriangles");
        List<UnitTest> tests = new LinkedList<>();
        tests.add(test);
        tests.add(test1);
        tests.add(test2);
        tests.add(test3);

        //Getting results
        UnitTestResultSet unitTestResultSet = null;

        //Sending to test runner
        TestRunner testRunner = new TestRunner(testFile, "TriangleCPUTest", PATHNAME, tests);
        unitTestResultSet = testRunner.test(patch, 1);
        LOG.debug("Unit test: {}", unitTestResultSet);
        Boolean successful = unitTestResultSet.allTestsSuccessful();
        double result = successful ? 1.0 :0.0;
        LOG.info("Unit test result: {}", result);
        return result;

    }
}
