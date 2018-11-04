package com.anniefraz.dissertation.gin.source;


import com.anniefraz.dissertation.gin.source.AnnaPath;
import com.anniefraz.dissertation.gin.source.Source;
import com.anniefraz.dissertation.gin.source.SourceFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SourceFactoryTest {


    private SourceFactory sourceFactory;

    @BeforeEach
    public void initialize(){
        String path = getClass().getResource("/testClasses/TestClass.java").getPath();
        Path folder = Paths.get(path).toAbsolutePath().getParent();
        sourceFactory = new SourceFactory(folder);

    }

    @Test
    public void testGetSourceFromAnnaPath(){
        AnnaPath name = new AnnaPath("TestClass");

        Source source = sourceFactory.getSourceFromAnnaPath(name);

        assertEquals(source.getPaths().get(0), name);

    }

    @Test
    public void testGetSourceFromAnnaPaths(){
        AnnaPath name = new AnnaPath("TestClass");
        AnnaPath name2 = new AnnaPath(Collections.singletonList("additional"),"Another");


        List<AnnaPath> paths = Arrays.asList(name, name2);
        Source source = sourceFactory.getSourceFromAnnaPaths(paths);

        assertTrue(source.getPaths().containsAll(paths));
    }



}

