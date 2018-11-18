package com.anniefraz.dissertation.gin.edit;

import com.anniefraz.dissertation.gin.source.Source;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@FunctionalInterface
public interface Edit {
    void apply(Source source);




}