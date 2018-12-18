package com.anniefraz.dissertation.test.runner.runner;

import org.junit.runner.JUnitCore;
import org.junit.runner.Request;
import org.pmw.tinylog.Logger;

import java.lang.annotation.Annotation;
import java.lang.reflect.Executable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// see https://stackoverflow.com/questions/24319697/java-lang-exception-no-runnable-methods-exception-in-running-junits/24319836

public class IsolatedTestRunner {

    /**
     * This method is called using reflection to ensure tests are run in an environment that employs a separate
     * classloader.
     */
    public UnitTestResult runTest(UnitTest test) {

        Request request = buildRequest(test);

        // Run and write com.anniefraz.dissertation.main.application.results to unitTestResults
        JUnitCore jUnitCore = new JUnitCore();
        List<UnitTestResult> unitTestResults = new ArrayList<>();
        jUnitCore.addListener(new TestRunListener(unitTestResults));

        try {
            jUnitCore.run(request);
        } catch (Exception e) {
            System.err.println("Error running junit: " + e);
            System.exit(-1);
        }

        UnitTestResult result = unitTestResults.get(0);
        result.setTest(test);
        return result;

    }

    public Request buildRequest(UnitTest test) {

        Class<?> clazz = null;

        try {
            String testClassname = test.getClassName();
            ClassLoader loader = this.getClass().getClassLoader();
            clazz = loader.loadClass(testClassname);
        } catch (ClassNotFoundException e) {
            String msg = "Unable to find class file for test [" + test + "]. Ensure that test classes can be found" +
                    "on the supplied classpath";
            System.err.println(msg);
            System.exit(-1);
        }

        String methodName = test.getMethodName();

        annotateTestWithTimeout(clazz, methodName, test.getTimeoutMS());

        return Request.method(clazz, methodName);

    }

    /**
     * A hack to add a timeout to the method using Java reflection. Yikes.
     * @param clazz
     * @param methodName
     * @param timeout
     */
    public void annotateTestWithTimeout(Class<?> clazz, String methodName, long timeout) {

        try {

            Field annotations = Executable.class.getDeclaredField("declaredAnnotations");
            annotations.setAccessible(true);

            Class<?> clazzCopy = clazz;

            while (clazzCopy != java.lang.Object.class) {
                Method[] methods = clazzCopy.getDeclaredMethods();
                for (Method m : methods) {
                    if (m.getName().equals(methodName)) {
                        m = clazzCopy.getDeclaredMethod(methodName);
                        m.getAnnotation(Annotation.class);
                        Map<Class<? extends Annotation>, Annotation> map;
                        map = (Map<Class<? extends Annotation>, Annotation>) annotations.get(m);
                        org.junit.Test jTest = (org.junit.Test) map.get(org.junit.Test.class);
                        if (jTest != null) {
                            ModifiableTest newTest = new ModifiableTest(timeout, jTest);
                            map.put(org.junit.Test.class, newTest);
                        }
                    }
                }
                clazzCopy = clazzCopy.getSuperclass();
            }



        } catch (Exception e) {
            Logger.error("Exception when instrumenting tests with a timeout: " + e);
            System.exit(-1);
        }

    }

}
