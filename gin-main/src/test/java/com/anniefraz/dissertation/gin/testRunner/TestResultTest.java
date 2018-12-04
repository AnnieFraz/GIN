package com.anniefraz.dissertation.gin.testRunner;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.Result;

import static org.junit.Assert.assertEquals;

public class TestResultTest {

    UnitTestResult testResult;
    Result result = new Result();

    private static final String patchedProgram = "public class SimpleExample() { }";
    private static final String expectedToString = "UnitTestResult ExampleClass.exampleMethod. " +
            "Passed: true; Timed out: false; Exception Type: N/A; Exception Message: N/A; Assertion Expected: N/A" +
            "; AssertionActual: N/A; Execution Time: 0;";

    @Before
    public void setUp() throws Exception {
        UnitTest test = new UnitTest("ExampleClass", "exampleMethod");
        testResult = new UnitTestResult(test, true);
    }

    @Test
    public void getExecutionTime() {
        testResult.addExecutionTime(253);
        assertEquals(253, testResult.getExecutionTime(), 1e-15);
    }


    @Test
    public void testToString() {
        String actual = testResult.toString();
        assertEquals(expectedToString, actual);
    }

}
