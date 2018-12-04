package com.anniefraz.dissertation.test.runner.hotswap.application.config.test;

import com.anniefraz.dissertation.gin.source.SourceFactory;
import com.anniefraz.dissertation.test.runner.hotswap.test.DelegatingTestSourceFactory;
import com.anniefraz.dissertation.test.runner.hotswap.test.HotSwappingTestRunnerFactory;
import com.anniefraz.dissertation.test.runner.hotswap.test.TestRunnerFactory;
import com.anniefraz.dissertation.test.runner.hotswap.test.TestSourceFactory;
import org.hotswap.agent.config.PluginManager;
import org.mdkt.compiler.InMemoryJavaCompiler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestRunnerConfig {

    @Bean
    public TestRunnerFactory testRunnerFactory() {
        return new HotSwappingTestRunnerFactory(inMemoryJavaCompiler(), pluginManager());
    }

    @Bean
    public TestSourceFactory testSourceFactory(SourceFactory sourceFactory){
        return new DelegatingTestSourceFactory(sourceFactory);
    }


    @Bean
    public InMemoryJavaCompiler inMemoryJavaCompiler() {
        return InMemoryJavaCompiler.newInstance();
    }

    @Bean
    public PluginManager pluginManager() {
        return PluginManager.getInstance();
    }
}
