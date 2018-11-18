package com.anniefraz.dissertation.gin.application.config;

import com.anniefraz.dissertation.gin.edit.Edit;
import com.anniefraz.dissertation.gin.edit.ListBasedRandomEditFactory;
import com.anniefraz.dissertation.gin.edit.RandomEditFactory;
import com.anniefraz.dissertation.gin.patch.PatchFactory;
import com.anniefraz.dissertation.gin.patch.SimplePatchFactory;
import com.anniefraz.dissertation.gin.source.AnnaClass;
import com.anniefraz.dissertation.gin.source.SourceFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;
import java.util.function.Function;

@Configuration
@ComponentScan(basePackages = "com.anniefraz.dissertation.gin.application.config")
@PropertySource("classpath:AnnaGin.properties")
public class ApplicationConfig {


    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer(){
        return new PropertySourcesPlaceholderConfigurer();
    }
    @Value("${source.class.path}")
    private String classPath;


    @Bean
    public RandomEditFactory randomEditFactory(List<Function<AnnaClass, Edit>> editsFunction){
        return new ListBasedRandomEditFactory(editsFunction, Math::random);
    }

    @Bean
    public PatchFactory patchFactory(RandomEditFactory randomEditFactory){
        return new SimplePatchFactory(randomEditFactory, Math::random);
    }

    @Bean
    public SourceFactory sourceFactory(){
        Path path = Paths.get(classPath);
        return new SourceFactory(path);
    }
}
