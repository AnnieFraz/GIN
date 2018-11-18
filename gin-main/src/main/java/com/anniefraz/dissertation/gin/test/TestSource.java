package com.anniefraz.dissertation.gin.test;

import com.anniefraz.dissertation.gin.source.Source;

import java.util.List;
import java.util.stream.Collectors;

public interface TestSource {
    static TestSource wrap(Source source) {
        return () -> source.getAnnaClasses().stream().map(AnnaTestClass::new).collect(Collectors.toList());
    }

    List<AnnaTestClass> getAnnaTestClasses();
}
