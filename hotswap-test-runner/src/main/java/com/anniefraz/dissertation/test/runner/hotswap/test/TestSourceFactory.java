package com.anniefraz.dissertation.test.runner.hotswap.test;

import com.anniefraz.dissertation.gin.source.AnnaPath;
import com.anniefraz.dissertation.gin.source.Source;

public interface TestSourceFactory {
    Source getSourceFromAnnaPath(AnnaPath annaPath);

    TestSource getTestSourceFromAnnaPath(AnnaPath annaPath);
}
