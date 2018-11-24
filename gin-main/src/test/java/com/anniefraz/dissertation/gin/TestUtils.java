package com.anniefraz.dissertation.gin;

import com.anniefraz.dissertation.gin.source.SourceFactory;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TestUtils {

    public static SourceFactory getSourceFactory(Object o) {
        URI path;
        try {
            path = o.getClass().getResource("/testClasses/TestClass.java").toURI();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        Path folder = Paths.get(path).toAbsolutePath().getParent();
        return new SourceFactory(folder);
    }

}
