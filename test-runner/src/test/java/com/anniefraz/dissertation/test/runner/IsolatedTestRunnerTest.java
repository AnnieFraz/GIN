package com.anniefraz.dissertation.test.runner;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.Assert.*;

public class IsolatedTestRunnerTest {

    //final String ROOT_DIR = "./tmp";

    final String EXAMPLE_DIR = "examples" + File.separator + "unittests";

  //  final String EXAMPLE_DIR = "C:\\Users\\user\\IdeaProjects\\Anna-Gin\\test-runner\\examples\\unittests";

    CacheClassLoader classLoader;

    Object runner;
    Method runnerMethod;


    // Instantiate TestRunner by loading via CacheClassLoader. Necessary for any test using patches.
    @Before
    public void setUp() throws Exception {

        System.out.println(EXAMPLE_DIR);

        classLoader = new CacheClassLoader(EXAMPLE_DIR);

        System.out.println(classLoader);
        Class<?> runnerClass = null;
        try {
            runnerClass = classLoader.loadClass(IsolatedTestRunner.class.getName());
        } catch (ClassNotFoundException e) {
            System.err.println("Could not load isolated test runner - class not found.");
            System.exit(-1);
        }

        runner = null;
        try {
            runner = runnerClass.newInstance();
        } catch (InstantiationException e) {
            System.err.println("Could not instantiate isolated test runner: " + e);
            System.exit(-1);
        } catch (IllegalAccessException e) {
            System.err.println("Could not instantiate isolated test runner: " + e);
            System.exit(-1);
        }

        try {
            runnerMethod = runner.getClass().getMethod(TestRunner.ISOLATED_TEST_RUNNER_METHOD_NAME, UnitTest.class);
        } catch (NoSuchMethodException e) {
            System.err.println("Could not run isolated test runner, can't find method: " + TestRunner.ISOLATED_TEST_RUNNER_METHOD_NAME);
            System.exit(-1);
        }

    }

    @Test
    public void runTestWithException() throws Exception {

        UnitTest test = new UnitTest("ErrorTest", "testException");

        Object resultObj = null;
        try {
            resultObj = runnerMethod.invoke(runner, test);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        UnitTestResult result = (UnitTestResult) resultObj;

        assertFalse(result.getPassed());
        assertEquals(result.getExceptionType(), "java.lang.NullPointerException");
        assertTrue(result.getExecutionTime() > 0);
    }

    // Changing timeouts requires class overlaying, so need to use reflection to invoke.
    @Test
    public void timeout() throws Exception {

        // This test will run for one second, and has a timeout of 10 seconds.
        UnitTest test = new UnitTest("ErrorTest", "testTimeout");

        Object result = null;
        try {
            result = runnerMethod.invoke(runner, test);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        UnitTestResult res = (UnitTestResult)result;

        // So first time: all good
        assertTrue(res.getPassed());
        assertFalse(res.getTimedOut());

        // Now the test should time out
	    test.setTimeoutMS(500);

        Object resultWithTimeout = null;
        try {
            resultWithTimeout = runnerMethod.invoke(runner, test);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        UnitTestResult resTimeout = (UnitTestResult)resultWithTimeout;

        assertFalse(resTimeout.getPassed());
        assertTrue(resTimeout.getTimedOut());

    }

    @Test
    public void assertionError() {
        UnitTest test = new UnitTest("ErrorTest", "testAssertionError");

        Object resultObj = null;
        try {
            resultObj = runnerMethod.invoke(runner, test);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        UnitTestResult result = (UnitTestResult) resultObj;
        assertFalse(result.getPassed());
        assertEquals(result.getAssertionActualValue(), "10");
        assertEquals(result.getAssertionExpectedValue(), "15");
    }

}
