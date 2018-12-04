package com.anniefraz.dissertation.gin.testRunner;



import com.anniefraz.dissertation.gin.patch.Patch;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/*
    Holds the results of running a set of tests.
    This class holds commonalities between tests relating to patch compilation;
    the rest of the data is held in a list of UnitTestResult.
 */
public class UnitTestResultSet {

    private LinkedList<UnitTestResult> results;

    private Patch patch;
    private boolean patchValid;
    private boolean compiledOK;

    public Patch getPatch() {
        return patch;
    }
    public boolean getCleanCompile() {
        return compiledOK;
    }
    public boolean getValidPatch() {
        return patchValid;
    }
    public LinkedList<UnitTestResult> getResults () { return results; }


    public UnitTestResultSet(Patch patch, boolean patchValid, boolean compiledOK) {
        this.patch = patch;
        this.patchValid = patchValid;
        this.compiledOK = compiledOK;
        this.results =  new LinkedList<>();
    }

    public void setResults(LinkedList<UnitTestResult> results) {
        this.results = results;
    }

    public boolean allTestsSuccessful() {
        for (UnitTestResult testResult: results) {
            if (!testResult.getPassed()) {
                return false;
            }
        }
        return true;
    }

    public long totalExecutionTime() {
        long totalTime = 0;
        for (UnitTestResult testResult: results) {
            totalTime += testResult.getExecutionTime();
        }
        return totalTime;
    }

    public Map<UnitTest, long[]> getUnitTestTimes() {
	List<UnitTestResult> testResults = this.results.stream().filter(result -> result.getRepNumber() == 0).collect(Collectors.toList());
	Map<UnitTest, long[]> testRunTimes = new HashMap<UnitTest, long[]>();
	for (UnitTestResult testResult : testResults) {
		List<Long> runtimes = new LinkedList<Long>();
		this.results.stream().filter(result -> testResult.getTest() == result.getTest()).forEach(result -> runtimes.add(result.getExecutionTime()));
		testRunTimes.put(testResult.getTest(), runtimes.stream().mapToLong(l -> l).toArray());
	}
        return testRunTimes;
    }

    @Override
    public String toString() {
        String myrep = String.format("UnitTestResultSet. Patch %s;  Valid: %b; Compiled: %b.",
                patch, patchValid, compiledOK);
        if (results.size() > 0) {
            myrep += " Results follow: ";
        }
        for (UnitTestResult result: results) {
            myrep += " [" + result.toString() + "]";
        }
        return myrep;
    }
}
