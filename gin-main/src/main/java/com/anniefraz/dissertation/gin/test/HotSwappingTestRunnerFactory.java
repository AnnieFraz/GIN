package com.anniefraz.dissertation.gin.test;

import junit.framework.JUnit4TestAdapter;
import junit.framework.TestSuite;
import org.hotswap.agent.config.PluginManager;
import org.junit.internal.TextListener;
import org.junit.runner.JUnitCore;
import org.mdkt.compiler.InMemoryJavaCompiler;

public class HotSwappingTestRunnerFactory implements TestRunnerFactory {
    private InMemoryJavaCompiler inMemoryJavaCompiler;
    private PluginManager pluginManager;

    public HotSwappingTestRunnerFactory(InMemoryJavaCompiler inMemoryJavaCompiler, PluginManager pluginManager) {
        this.inMemoryJavaCompiler = inMemoryJavaCompiler;
        this.pluginManager = pluginManager;
    }

    @Override
    public TestRunner getJunit4TestRunner(TestSource testSource) {
        TestSuite testSuite = new TestSuite();
        testSource.getAnnaTestClasses()
                .stream()
                .map(annaClass -> inMemoryJavaCompiler.compile(annaClass.getPath().getCanonicalName(), annaClass.getJoinedLines()))
                .map(JUnit4TestAdapter::new)
                .forEach(testSuite::addTest);

        JUnitCore junit = new JUnitCore();
        junit.addListener(new TextListener(System.out));
        return new Junit4HotSwappingTestRunner(inMemoryJavaCompiler, pluginManager, junit, testSuite);
    }
}
