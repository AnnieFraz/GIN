package com.anniefraz.dissertation.test.runner.runner;

/**
 * Represents a test that needs to be run by Gin.
 */
public class UnitTest {

	public static long defaultTimeoutMS = 10000L;

	private String className;
	private String methodName = null;
	private long timeoutMS;

	public UnitTest(String classname, String methodName) {
	    this.className = classname;
	    this.methodName = methodName;
	    this.timeoutMS = defaultTimeoutMS;
    }

    public void setTimeoutMS(long timeoutInMS) {
	    this.timeoutMS = timeoutInMS;
    }

    public String getTestName() {
	    return className + "." + methodName;
    }

    public String getClassName() {
	    return className;
    }

    public long getTimeoutMS() {
	    return timeoutMS;
    }

    public String getMethodName() {
	    return methodName;
    }

	@Override
	public String toString() {
		return String.format("Class: %s Method: %s Timeout: %d", className, methodName, timeoutMS);
	}

}
