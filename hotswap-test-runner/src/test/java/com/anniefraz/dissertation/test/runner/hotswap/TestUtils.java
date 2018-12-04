package com.anniefraz.dissertation.test.runner.hotswap;

import com.anniefraz.dissertation.gin.source.SourceFactory;
import com.anniefraz.dissertation.test.runner.hotswap.test.DelegatingTestSourceFactory;
import com.anniefraz.dissertation.test.runner.hotswap.test.TestSourceFactory;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TestUtils {

    public static TestSourceFactory getTestSourceFactory(Object o) {
        URI path;
        try {
            path = o.getClass().getResource("/testClasses/TestClass.java").toURI();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        Path folder = Paths.get(path).toAbsolutePath().getParent();
        return new DelegatingTestSourceFactory(new SourceFactory(folder));
    }

}
