package com.anniefraz.dissertation.test.runner.hotswap.test;

import com.anniefraz.dissertation.gin.source.AnnaPath;
import com.anniefraz.dissertation.gin.source.Source;
import com.anniefraz.dissertation.gin.source.SourceFactory;

public class DelegatingTestSourceFactory implements TestSourceFactory {

    private SourceFactory sourceFactory;

    public DelegatingTestSourceFactory(SourceFactory sourceFactory) {
        this.sourceFactory = sourceFactory;
    }


    @Override
    public Source getSourceFromAnnaPath(AnnaPath annaPath) {
        return sourceFactory.getSourceFromAnnaPath(annaPath);
    }

    @Override
    public TestSource getTestSourceFromAnnaPath(AnnaPath annaPath) {
        return TestSource.wrap(sourceFactory.getSourceFromAnnaPath(annaPath));
    }
}
