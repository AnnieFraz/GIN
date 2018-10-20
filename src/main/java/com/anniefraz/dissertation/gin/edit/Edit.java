package com.anniefraz.dissertation.gin.edit;

import com.anniefraz.dissertation.gin.source.AnnaClass;

@FunctionalInterface
public interface Edit {
    void apply(AnnaClass annaClass);
}