package com.anniefraz.dissertation.test.runner.hotswap.test;

import com.anniefraz.dissertation.gin.source.AnnaPath;
import com.anniefraz.dissertation.gin.source.Source;
import com.anniefraz.dissertation.test.runner.hotswap.TestUtils;
import org.hotswap.agent.config.PluginManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mdkt.compiler.InMemoryJavaCompiler;

import static org.junit.jupiter.api.Assertions.assertTrue;

//needs to be run with:
//-XXaltjvm=dcevm -javaagent:D:\hotswap\hotswap-agent-1.3.0.jar
public class TestRunnerTest {
    private TestRunner testRunner;
    private Source source;

    @BeforeEach
    void setUp() {
        TestSourceFactory sourceFactory = TestUtils.getTestSourceFactory(this);

        TestSource testSource = sourceFactory.getTestSourceFromAnnaPath(AnnaPath.getBuilder().addPackage("example").setClassName("TriangleTest").build());
        source = sourceFactory.getSourceFromAnnaPath(AnnaPath.getBuilder().addPackage("example").setClassName("Triangle").build());

        InMemoryJavaCompiler inMemoryJavaCompiler = InMemoryJavaCompiler.newInstance();
        inMemoryJavaCompiler.addSource(source.getAnnaClasses().get(0).getPath().getCanonicalName(), source.getAnnaClasses().get(0).getJoinedLines());
        TestRunnerFactory testRunnerFactory = new HotSwappingTestRunnerFactory(inMemoryJavaCompiler, PluginManager.getInstance());
        testRunner = testRunnerFactory.getJunit4TestRunner(testSource);
    }

    @Test
    void testTestRunnerRunsTests() {
        boolean passed = testRunner.run(source);
        assertTrue(passed);
    }
}
