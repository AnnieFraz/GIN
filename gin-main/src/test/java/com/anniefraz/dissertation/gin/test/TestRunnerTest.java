package com.anniefraz.dissertation.gin.test;

import com.anniefraz.dissertation.gin.source.AnnaPath;
import com.anniefraz.dissertation.gin.source.Source;
import com.anniefraz.dissertation.gin.source.SourceFactory;
import org.hotswap.agent.config.PluginManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mdkt.compiler.InMemoryJavaCompiler;

import static com.anniefraz.dissertation.gin.TestUtils.getSourceFactory;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestRunnerTest {
    private TestRunner testRunner;
    private Source source;

    @BeforeEach
    void setUp() {
        SourceFactory sourceFactory = getSourceFactory(this);

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
