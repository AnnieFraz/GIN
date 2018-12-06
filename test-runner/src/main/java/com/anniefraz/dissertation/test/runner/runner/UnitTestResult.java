package com.anniefraz.dissertation.test.runner.runner;

import org.junit.runner.Description;
import org.junit.runner.notification.Failure;

public class UnitTestResult {

	private UnitTest test;
	private int repNumber;
	private Boolean passed;
	private Boolean timedOut;
	private String exceptionType;
	private String exceptionMessage;
	private long executionTime;
    private String expectedValue;
    private String actualValue;

	public UnitTest getTest() {
		return test;
	}

	public void setTest(UnitTest test) { this.test = test; }

	public Boolean getPassed() {
		return passed;
	}

	public Boolean getTimedOut() {
		return timedOut;
	}

	public String getExceptionType() {
		return exceptionType;
	}

	public String getExceptionMessage() {
		return exceptionMessage;
	}

	public String getAssertionExpectedValue() {
		return expectedValue;
	}

	public String getAssertionActualValue() {
		return actualValue;
	}

	public long getExecutionTime() { return executionTime; }

	public void setRepNumber(int rep) { this.repNumber = rep; }

	public int getRepNumber() { return this.repNumber; }

	public UnitTestResult(Description description) {
		this.passed = true;
		this.executionTime = 0;
		this.exceptionType = "N/A";
		this.exceptionMessage = "N/A";
		this.timedOut = false;
		this.expectedValue = "N/A";
		this.actualValue = "N/A";
	}

	public UnitTestResult(UnitTest test, boolean passed) {
		this.executionTime = 0;
		this.exceptionType = "N/A";
		this.exceptionMessage = "N/A";
		this.timedOut = false;
		this.expectedValue = "N/A";
		this.actualValue = "N/A";

	    this.passed = passed;
	    this.test = test;
    }

	public void addFailure(Failure f)  {
		if (f != null) {
			this.passed = false;

			Throwable rootCause = f.getException();
			rootCause.printStackTrace();
			while(rootCause.getCause() != null &&  rootCause.getCause() != rootCause) {
			    rootCause = rootCause.getCause();
			}
			this.exceptionType = rootCause.getClass().getName();
			this.exceptionMessage = rootCause.getMessage();
		
			if (this.exceptionType == "org.junit.runners.model.TestTimedOutException") {
				this.timedOut = true;
			} else if (this.exceptionType == "java.lang.AssertionError")  {
					// based on messages thrown: https://github.com/junit-team/junit4/blob/master/src/main/java/org/junit/Assert.java
					// Actual: ACTUAL, 'expected null, but was:<ACTUAL>', 'expected not same', 'expected same:<EXPECTED> was not:<ACTUAL>
					// 'expected: EXPECTED but was: ACTUAL', 'expected:<EXPECTED> but was:<ACTUAL>'
					String s = this.exceptionMessage;
					if ( s.contains("expected:<") && s.contains(" but was:<") ) {
						s = s.substring(s.lastIndexOf("expected:<")+10);
						s = s.substring(0, s.indexOf(">"));
						this.expectedValue = s;
						s = this.exceptionMessage;
						s = s.substring(s.lastIndexOf(" but was:<")+10);
						s = s.substring(0, s.indexOf(">"));
						this.actualValue = s;
					}
					else if ( s.contains("expected: ") && s.contains(" but was: ") ) {
						s = s.substring(s.lastIndexOf("expected: ")+10);
						s = s.substring(0, s.indexOf(" but was: "));
						this.expectedValue = s;
						s = this.exceptionMessage;
						s = s.substring(s.lastIndexOf(" but was: ")+10);
						this.actualValue = s;
					}
					else if ( s.contains("expected same:<") && s.contains(" was not:<") ) {
						s = s.substring(s.lastIndexOf("expected same:<")+15);
						s = s.substring(0, s.indexOf(">"));
						this.expectedValue = s;
						s = this.exceptionMessage;
						s = s.substring(s.lastIndexOf(" was not:<")+10);
						s = s.substring(0, s.indexOf(">"));
						this.actualValue = s;
					}
					else if (s.contains("expected null, but was:<")) {
						this.expectedValue = "null";
						this.actualValue = s.substring(s.lastIndexOf("expected null, but was:<")+24);
					}
					else if (s.contains("Actual: ")) {
						this.actualValue = s.substring(s.lastIndexOf("Actual: ")+8);
					}
			}
		}
	} 

	public void addExecutionTime(long testExecutionTime)  {
		this.executionTime = testExecutionTime;
	}
	
	@Override
    public String toString() {
        return String.format(
                "UnitTestResult %s. " +
                        "Passed: %b; Timed out: %b; Exception Type: %s; Exception Message: %s; " +
                        "Assertion Expected: %s; AssertionActual: %s; Execution Time: %d;",
                test.getTestName(),
                passed,
                timedOut,
                exceptionType,
                exceptionMessage,
                expectedValue,
                actualValue,
                executionTime);
    }

}
