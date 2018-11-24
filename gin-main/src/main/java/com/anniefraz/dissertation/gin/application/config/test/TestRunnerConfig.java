package com.anniefraz.dissertation.gin.application.config.test;

import com.anniefraz.dissertation.gin.test.HotSwappingTestRunnerFactory;
import com.anniefraz.dissertation.gin.test.TestRunnerFactory;
import org.hotswap.agent.config.PluginManager;
import org.mdkt.compiler.InMemoryJavaCompiler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestRunnerConfig {

    @Bean
    public TestRunnerFactory testRunnerFactory(){
        return new HotSwappingTestRunnerFactory(inMemoryJavaCompiler(), pluginManager());
    }

    @Bean
    public InMemoryJavaCompiler inMemoryJavaCompiler(){
        return InMemoryJavaCompiler.newInstance();
    }

    @Bean
    public PluginManager pluginManager(){
        return PluginManager.getInstance();
    }
}
