package com.anniefraz.dissertation.test.runner.hotswap.application.config;

import com.anniefraz.dissertation.gin.application.config.ApplicationConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = "com.anniefraz.dissertation.test.runner.hotswap.application.config")
@Import(ApplicationConfig.class)
public class HotswapTestRunnerApplicationConfig {
}
