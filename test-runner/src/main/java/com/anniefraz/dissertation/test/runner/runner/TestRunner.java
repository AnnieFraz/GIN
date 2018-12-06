package com.anniefraz.dissertation.test.runner.runner;


import com.anniefraz.dissertation.gin.patch.Patch;
import com.anniefraz.dissertation.gin.source.Source;
import org.junit.Test;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.TestClass;
import org.mdkt.compiler.CompilationException;
import org.mdkt.compiler.CompiledCode;
import org.mdkt.compiler.InMemoryJavaCompiler;
import org.pmw.tinylog.Logger;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

public class TestRunner {

    public static final String ISOLATED_TEST_RUNNER_METHOD_NAME = "runTest";
    protected File packageDirectory;
    protected String className;
    protected List<UnitTest> tests;
    protected String classPath;
    protected CacheClassLoader classLoader;

    protected String outputfileString;

    public TestRunner(File packageDirectory, String className, String classPath, List<UnitTest> unitTests) {

        this.packageDirectory = packageDirectory;
        this.className = className; //(packageName != null ? packageName + "." : "") + className;
        this.tests = unitTests;
        this.classPath = classPath;
        classLoader = new CacheClassLoader(classPath);  // Will be replaced for each compilation.

    }

    public TestRunner(File packageDirectory, String className, String classPath, String testClassName) {
        this(packageDirectory, className, classPath, new LinkedList<UnitTest>());
        classLoader = new CacheClassLoader(classPath);  // Replace for each compilation. Needed here for test extraction
        this.tests = testsForClass(testClassName);
    }

    private List<UnitTest> testsForClass(String testClassName) {

        // Set up list of tests based on the class name
        List<UnitTest> tests = new LinkedList<>();

        Class clazz = null;

        try {
            clazz = this.classLoader.loadClass(testClassName);
        } catch (ClassNotFoundException e) {
            System.err.println("Failed to find test class: ");
        }

        List<FrameworkMethod> methods = new TestClass(clazz).getAnnotatedMethods(Test.class);

        for (FrameworkMethod eachTestMethod : methods){

            String methodName = eachTestMethod.getName();
            UnitTest test = new UnitTest(testClassName, methodName);
            tests.add(test);

        }

        return tests;

    }

    public UnitTestResultSet test(Patch patch, int reps) {

        // We have to create a new class loader for every compilation, because otherwise java will cache
        // the modified class for us (in the findLoadedClass method) and we can't stop it.
        classLoader = new CacheClassLoader(classPath);

        // Apply the patch.
        Source patchedSource = patch.getOutputSource();
        boolean patchValid = (patchedSource != null);
        boolean compiledOK = false;
        if (patchedSource != null){
            compiledOK = patchedSource
                    .getAnnaClasses()
                    .stream()
                    .allMatch(
                            annaClass -> compile(
                                    annaClass.getPath().getCanonicalName(),
                                    annaClass.getJoinedLines(),
                                    classLoader
                            )
                    );
        }

        UnitTestResultSet resultSet = new UnitTestResultSet(patch, patchValid, compiledOK);

        LinkedList<UnitTestResult> results;
        if (compiledOK) {
            results = runTests(reps, classLoader);
        } else {
            results = emptyResults(reps);
        }

        resultSet.setResults(results);
        
        try {
        	classLoader.close();
        } catch (IOException e) {
        	e.printStackTrace();
        }
        return resultSet;

    }

    /**
     * Helper method to return an empty results list when failed to compile.
     * @return
     */
    private LinkedList<UnitTestResult> emptyResults(int reps) {
        LinkedList<UnitTestResult> results = new LinkedList<>();
        for (int rep=0; rep<reps; rep++) {
            for (UnitTest test : tests) {
                UnitTestResult result = new UnitTestResult(test, false);
                result.setRepNumber(rep);
                results.add(result);
            }
        }
        return results;
    }

    /**
     * Compile the temporary (patched) source file and a copy of the test class.
     *
     * @return Boolean indicating whether the compilation was successful.
     */
    protected boolean compile(String className, String source, CacheClassLoader classLoader)  {

        boolean success = false;

        try {

        	InMemoryJavaCompiler compiler = InMemoryJavaCompiler.newInstance();
        	compiler = compiler.ignoreWarnings();
        	//compiler.useParentClassLoader(classLoader);
        	if (this.classPath != null) {
        		//compiler.useOptions("-classpath", this.classPath, "-Xlint:unchecked");
        	}
        	
        	CompiledCode code = compiler.compileToRawBytes(className, source);

        	classLoader.setCustomCompiledCode(className, code);

        	success = true;

        } catch (CompilationException e) {

            Logger.error("Error compiling class " + className);
            Logger.error(e.getMessage());

            if (e.getMessage().contains("does not exist")) {
            	Logger.error("Did you set the classpath with -cp=?");
            }

        } catch (Exception e) {
            Logger.error("Unrecognised exception compiling class " + className);
            Logger.error(e.getMessage());
            System.exit(-1);
        }

        return success;

    }

    public LinkedList<UnitTestResult> runTests(int reps, CacheClassLoader classLoader) {

        LinkedList<UnitTestResult> results = new LinkedList<>();

        for (int r=0; r < reps; r++) {
            for (UnitTest test: tests) {
                results.add(runSingleTest(test, classLoader, r));
            }
        }

        return results;

    }

    /**
     * Run the test class for a modified class.
     * Loads IsolatedTestRunner using a separate classloader and invokes jUnit using reflection.
     * This allows us to have jUnit load all classes from a CacheClassLoader, enabling us to override the modified
     * class with the freshly compiled version.
     * @return
     */
    public UnitTestResult runSingleTest(UnitTest test, CacheClassLoader classLoader, int rep) {

        Class<?> runnerClass = null;
        try {
            runnerClass = classLoader.loadClass(IsolatedTestRunner.class.getName());
        } catch (ClassNotFoundException e) {
            System.err.println("Could not load isolated test runner - class not found.");
            System.exit(-1);
        }

        Object runner = null;
        try {
            runner = runnerClass.newInstance();
        } catch (InstantiationException e) {
            System.err.println("Could not instantiate isolated test runner: " + e);
            System.exit(-1);
        } catch (IllegalAccessException e) {
            System.err.println("Could not instantiate isolated test runner: " + e);
            System.exit(-1);
        }

        Method method = null;
        try {
            method = runner.getClass().getMethod(ISOLATED_TEST_RUNNER_METHOD_NAME, UnitTest.class);
        } catch (NoSuchMethodException e) {
            System.err.println("Could not run isolated test runner, can't find method: " + ISOLATED_TEST_RUNNER_METHOD_NAME);
            System.exit(-1);
        }

        Object result = null;
        try {
            result = method.invoke(runner, test);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        
        UnitTestResult res = (UnitTestResult)result;
        res.setRepNumber(rep);

        return res;

    }

    /*To do this test runner:
    - Load in the tests
    - Using source factory

    */

}
