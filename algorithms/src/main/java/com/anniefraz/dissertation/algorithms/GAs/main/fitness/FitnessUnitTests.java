package com.anniefraz.dissertation.algorithms.GAs.main.fitness;

import com.anniefraz.dissertation.gin.patch.Patch;
import com.anniefraz.dissertation.main.input.UserInput;
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

    private static final Logger LOG = LoggerFactory.getLogger(FitnessUnitTests.class);
    private static final String PATHNAME = "C:\\Users\\user\\IdeaProjects\\Anna-Gin\\test-runner\\examples\\unittests";
    private UserInput userInput;

    public FitnessUnitTests(UserInput userInput) {
        this.userInput = userInput;
    }


    @Override
    public double measure(Patch patch) {

        //Files
        //String testClassNameTriangle = "example.TriangleTest";
      //  String className = "Triangle";

        //Reverse String
        //String testClassNameString = "foo.ReverseStringTest";
       // String className = "foo.ReverseString";



        String testClassName = userInput.getPackageName()+"."+userInput.getTestFileName();
        String methodName = "aMethod";

        File testFile = Paths.get(PATHNAME).toFile();

        List<UnitTest> tests = new LinkedList<>();

        //Tests SetUp
if (testClassName.equals("example.TriangleTest")) {
    //System.out.println("yeet");
    UnitTest test = new UnitTest(testClassName, "testInvalidTriangles");
    UnitTest test1 = new UnitTest(testClassName, "testEqualateralTriangles");
    UnitTest test2 = new UnitTest(testClassName, "testIsocelesTriangles");
    UnitTest test3 = new UnitTest(testClassName, "testScaleneTriangles");
    tests.add(test);
    tests.add(test1);
    tests.add(test2);
    tests.add(test3);
} else if (testClassName.equals("foo.ReverseStringTest")) {
    UnitTest test = new UnitTest(testClassName, "test1");
    UnitTest  test1 = new UnitTest(testClassName, "test2");
    UnitTest test2 = new UnitTest(testClassName, "canDealWithUpperCaseTest");
    UnitTest  test3 = new UnitTest(testClassName, "failWithNumbers");
    tests.add(test);
    tests.add(test1);
    tests.add(test2);
    tests.add(test3);
} else if (testClassName.equals("boop.ExampleTest")){
    UnitTest test = new UnitTest(testClassName, "testReturnTen");
    UnitTest  test1 = new UnitTest(testClassName, "emptyTest");
    UnitTest test2 = new UnitTest(testClassName, "testReturnOneHundred");
    tests.add(test);
    tests.add(test1);
    tests.add(test2);
}else if (testClassName.equals("cscu9a2.ProductChartTest")){
    UnitTest test = new UnitTest(testClassName, "testCreateGUI");
    UnitTest  test1 = new UnitTest(testClassName, "testPaintScreen");
    tests.add(test);
    tests.add(test1);
}
else if (testClassName.equals("complex.ComplexTest")){
    UnitTest test = new UnitTest(testClassName, "testReturn");
    tests.add(test);
}


        //Getting com.anniefraz.dissertation.experiments.results
        UnitTestResultSet unitTestResultSet = null;


        //Sending to test runner
       // TestRunner testRunner = new TestRunner(testFile, "ReverseStringTest", PATHNAME, tests);
        TestRunner testRunner = new TestRunner(testFile, userInput.getClassFileName(), PATHNAME, tests);
        unitTestResultSet = testRunner.test(patch, 1);
        LOG.debug("Unit test: {}", unitTestResultSet);
        Boolean successful = unitTestResultSet.allTestsSuccessful();
        LOG.debug("Unit test a success?:", successful.toString());
        double result; // = successful ? 1.0 :0.0;
        if (successful.equals(true)){
            result = 0.0;
        } else{
            result = 1.0;
        }

        LOG.info("Unit test result: {}", result);
        return result;

    }
}
