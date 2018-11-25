package com.anniefraz.dissertation.gin.test;

import com.anniefraz.dissertation.gin.source.Source;
import javafx.util.Pair;
import junit.framework.TestSuite;
import org.hotswap.agent.config.PluginManager;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.mdkt.compiler.CompilationException;
import org.mdkt.compiler.InMemoryJavaCompiler;

import java.util.Map;
import java.util.stream.Collectors;

public class Junit4HotSwappingTestRunner implements TestRunner {
    private final InMemoryJavaCompiler inMemoryJavaCompiler;
    private PluginManager pluginManager;
    private JUnitCore junit;
    private TestSuite testSuite;

    public Junit4HotSwappingTestRunner(InMemoryJavaCompiler inMemoryJavaCompiler, PluginManager pluginManager, JUnitCore junit, TestSuite testSuite) {
        this.inMemoryJavaCompiler = inMemoryJavaCompiler;
        this.pluginManager = pluginManager;
        this.junit = junit;
        this.testSuite = testSuite;
    }

    @Override
    public boolean run(Source source) {
        Map<Class<?>, byte[]> hotSwapMap = null;
        try {
            hotSwapMap = source.getAnnaClasses()
                    .stream()
                    .map(annaClass -> inMemoryJavaCompiler.compileToRawBytes(annaClass.getPath().getCanonicalName(), annaClass.getJoinedLines()))
                    .collect(Collectors.toMap(Pair::getValue, pair -> pair.getKey().getByteCode()));
        } catch (CompilationException ignored){
        }
        if (hotSwapMap == null) {
            return false;
        }
        pluginManager.hotswap(hotSwapMap);
        Result result = junit.run(testSuite);

        return result.wasSuccessful();
    }
}
