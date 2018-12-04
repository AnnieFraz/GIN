package com.anniefraz.dissertation.gin.testRunner;


import com.anniefraz.dissertation.gin.edit.Edit;
import com.anniefraz.dissertation.gin.edit.line.RemoveLineEdit;
import com.anniefraz.dissertation.gin.patch.Patch;
import com.anniefraz.dissertation.gin.patch.PatchFactory;
import com.anniefraz.dissertation.gin.patch.SimplePatchFactory;
import com.anniefraz.dissertation.gin.source.AnnaPath;
import com.anniefraz.dissertation.gin.source.Source;
import com.anniefraz.dissertation.gin.source.SourceFactory;
import com.sun.org.apache.bcel.internal.classfile.SourceFile;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import org.junit.Before;
import org.junit.Test;
import org.mdkt.compiler.CompiledCode;
import org.mdkt.compiler.InMemoryJavaCompiler;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Spliterators;

import static com.anniefraz.dissertation.gin.testRunner.Configuration.*;
import static org.junit.Assert.*;

public class TestRunnerTest {

    private final static File exampleDir = new File(TEST_RESOURCES_DIR);

    private final static String className = "Triangle";
    private final static String testClassNameTriangle = "TriangleTest";
    private final static String testClassName = "ExampleTest";
    private final static String methodName = "aMethod";

    private CacheClassLoader loader;
    private TestRunner testRunner;

    private SourceFactory sourceFactory;


    @Before
    public void setUp() throws URISyntaxException, MalformedURLException {

        loader = new CacheClassLoader(TEST_RESOURCES_DIR);

        UnitTest test = new UnitTest(testClassName, methodName);
        LinkedList<UnitTest> tests = new LinkedList<>();
        tests.add(test);

        testRunner = new TestRunner(exampleDir, className, TEST_RESOURCES_DIR, tests);

        sourceFactory = new SourceFactory(Paths.get(TEST_RESOURCES_DIR).toAbsolutePath());
    }

    @Test
    public void testRunner() {
        assertEquals(exampleDir, testRunner.packageDirectory);
        assertEquals(className, testRunner.className);
        assertEquals(testClassName + "." + methodName, testRunner.tests.get(0).getTestName());
    }

    @Test
    public void testCompile() throws ClassNotFoundException {
    	boolean success;
    	success = testRunner.compile("SimpleExample", "public class SimpleExample {} ", loader);
    	assertTrue(success);
    	Class compiledClass = loader.findClass("SimpleExample");
        assertNotNull(compiledClass);
        assertEquals("SimpleExample", compiledClass.getSimpleName());
    }

    /**
     * Test the Example class from the test resources directory.
     * Run an empty patch and check it passes.
     * Then delete statement to cause an assertion failure and check it fails
     */
    @Test
    public void testModifiedClassIsRun() throws Exception {

        UnitTest test = new UnitTest(testClassNameTriangle, "testInvalidTriangles");
        UnitTest test1 = new UnitTest(testClassNameTriangle, "testEqualateralTriangles");
        UnitTest test2 = new UnitTest(testClassNameTriangle, "testIsocelesTriangles");
        UnitTest test3 = new UnitTest(testClassNameTriangle, "testScaleneTriangles");
        LinkedList<UnitTest> tests = new LinkedList<>();
        tests.add(test);
        tests.add(test1);
        tests.add(test2);
        tests.add(test3);

        // Package name is null. TODO: why do we require the package name?
        // Surely a fully qualified classname makes more sense.
        TestRunner testRunner = new TestRunner(new File(TEST_RESOURCES_DIR), "Example",
                TEST_RESOURCES_DIR, tests);

        LinkedList<String> methods = new LinkedList<>();
        methods.add("returnTen:7");

        AnnaPath annaPath = AnnaPath.getBuilder().addPackage("example").setClassName("Triangle").build();
        Source sourceFromAnnaPath = sourceFactory.getSourceFromAnnaPath(annaPath);

        Patch patch = new Patch(sourceFromAnnaPath, Collections.emptyList());

        String joinedLines = patch.getOutputSource().getAnnaClasses().get(0).getJoinedLines();

        CompiledCode triangle = InMemoryJavaCompiler.newInstance().compileToRawBytes("example.Triangle", joinedLines);

        System.out.println(joinedLines);
        System.out.println(triangle);

        //Can't find the classes

        UnitTestResultSet resultSet = testRunner.test(patch, 1);
        LinkedList<UnitTestResult> results = resultSet.getResults();
        UnitTestResult result = results.get(0);
        for (UnitTestResult unitTestResult :
                results) {
            System.out.println(unitTestResult.getPassed());
        }
        assertTrue(result.getPassed());

        Edit edit = new RemoveLineEdit(12 ,annaPath); // deletes result=10 hence introducing a bug
        Patch deletePatch = new Patch(sourceFromAnnaPath, Collections.singletonList(edit));

        UnitTestResultSet modifiedResultSet = testRunner.test(deletePatch, 1);
        assertTrue(modifiedResultSet.getValidPatch());
        assertTrue(modifiedResultSet.getCleanCompile());
        assertFalse(modifiedResultSet.allTestsSuccessful());

        UnitTestResult modifiedResult = modifiedResultSet.getResults().get(0);
        assertFalse(modifiedResult.getPassed());

    }

    /*
    @Test
    public void testMultipleTestsProvided() {

        LinkedList<UnitTest> tests = new LinkedList<>();

        UnitTest test = new UnitTest("ExampleTest", "testReturnTen");
        tests.add(test);

        UnitTest test2 = new UnitTest("ExampleTest", "emptyTest");
        tests.add(test2);

        TestRunner testRunner = new TestRunner(new File(TestConfiguration.TEST_RESOURCES_DIR), "Example",
                TestConfiguration.TEST_RESOURCES_DIR, tests);

        LinkedList<String> methods = new LinkedList<>();
        methods.add("returnTen:7");
        SourceFile sourceFile = new SourceFile(TestConfiguration.TEST_RESOURCES_DIR + "Example.java", methods);
        Patch patch = new Patch(sourceFile);

        UnitTestResultSet resultSet = testRunner.test(patch, 1);
        LinkedList<UnitTestResult> results = resultSet.getResults();
        UnitTestResult result = results.get(0);
        assertTrue(result.getPassed());

    }

    @Test
    public void testUseOfSuperClass() {

        LinkedList<UnitTest> tests = new LinkedList<>();

        UnitTest test = new UnitTest("ExampleTest", "testReturnOneHundred");
        tests.add(test);

        String classPath = TestConfiguration.TEST_RESOURCES_DIR;
        TestRunner testRunner = new TestRunner(new File(TestConfiguration.TEST_RESOURCES_DIR),
                "Example", classPath, tests);

        LinkedList<String> methods = new LinkedList<>();
        methods.add("returnOneHundred:15");

        SourceFile sourceFile = new SourceFile(TestConfiguration.TEST_RESOURCES_DIR + "Example.java", methods);

        Patch patch = new Patch(sourceFile);

        UnitTestResultSet resultSet = testRunner.test(patch, 1);
        LinkedList<UnitTestResult> results = resultSet.getResults();
        UnitTestResult result = results.get(0);

        assertTrue(result.getPassed());

    }

    @Test
    public void testInnerClass() {

        LinkedList<UnitTest> tests = new LinkedList<>();

        UnitTest test = new UnitTest("ExampleWithInnerClassTest", "testSimpleMethod");
        tests.add(test);

        String classPath = TestConfiguration.TEST_RESOURCES_DIR;

        TestRunner testRunner = new TestRunner(new File(TestConfiguration.TEST_RESOURCES_DIR),
                "ExampleWithInnerClass", classPath, tests);

        LinkedList<String> methods = new LinkedList<>();
        methods.add("simpleMethod:3");

        SourceFile sourceFile = new SourceFile(TestConfiguration.TEST_RESOURCES_DIR + "ExampleWithInnerClass.java", methods);

        Patch patch = new Patch(sourceFile);

        UnitTestResultSet resultSet = testRunner.test(patch, 1);
        LinkedList<UnitTestResult> results = resultSet.getResults();
        UnitTestResult result = results.get(0);

        assertTrue(result.getPassed());

    }
    */

    /**
     * Test that compiling a class that implements an interface works, where the compiled class for that interface
     * is on the classpath.
     * @throws Exception
     */
    @Test
    public void usesInterfaceAndInterfaceClassOnClassPath() throws Exception {

        InMemoryJavaCompiler compiler = InMemoryJavaCompiler.newInstance();
        compiler.useOptions("-classpath", TEST_RESOURCES_DIR);

        String srcCode = "package mypackage;\n" +
                "public class Example implements ExampleInterface {\n" +
                "\n" +
                "    public static void main(String args[]) {\n" +
                "        System.out.println(\"This class doesn't really do anything.\");\n" +
                "    }\n" +
                "\n" +
                "    public void exampleMethod() {\n" +
                "        System.out.println(\"Implementing example method.\");\n" +
                "    }\n" +
                "\n" +
                "}";

       CompiledCode compilationOutput = compiler.compileToRawBytes("mypackage.Example", srcCode);

        //CompiledCode compilationOutput = compiler.compile("mypackage.Example", srcCode);

        CacheClassLoader loader = new CacheClassLoader(TEST_RESOURCES_DIR);
        loader.setCustomCompiledCode("mypackage.Example", compilationOutput);

        Class example = loader.findClass("mypackage.Example");


    }

}