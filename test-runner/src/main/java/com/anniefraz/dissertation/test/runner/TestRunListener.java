package com.anniefraz.dissertation.test.runner;

import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

import java.util.List;

// following: https://www.experts-exchange.com/questions/22998085/How-do-I-capture-and-save-the-results-of-a-JUnit-4-test-suite-run.html

public class TestRunListener extends RunListener {

    private List<UnitTestResult> unitTestResult;
    private long startTime;

    public TestRunListener(List unitTestResult) {
	this.unitTestResult = unitTestResult;
	this.startTime = System.nanoTime();
    }

    public void testFailure(Failure failure) throws Exception {
        this.unitTestResult.get(unitTestResult.size()-1).addFailure(failure);
    }

    public void testFinished(Description description) throws Exception {
        this.unitTestResult.get(unitTestResult.size()-1).addExecutionTime(System.nanoTime()-startTime);
    }

    public void testStarted(Description description) throws Exception {
        this.unitTestResult.add(new UnitTestResult(description));
    	this.startTime = System.nanoTime();
    }

}
