package com.anniefraz.dissertation.gin.edit.block;

import com.anniefraz.dissertation.gin.edit.Edit;
import com.anniefraz.dissertation.gin.source.AnnaPath;
import com.anniefraz.dissertation.gin.source.Source;
import com.anniefraz.dissertation.gin.source.SourceFactory;
import com.github.javaparser.JavaParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BlockTest {

    private AnnaPath annaPath;
    private Source sourceFromAnnaPath;

    @BeforeEach
    public void initialize(){
        String path = getClass().getResource("/testClasses/TestClass.java").getPath();
        Path folder = Paths.get(path).toAbsolutePath().getParent();
        SourceFactory sourceFactory = new SourceFactory(folder);
        annaPath = new AnnaPath(Collections.singletonList("example"), "Triangle");
        sourceFromAnnaPath = sourceFactory.getSourceFromAnnaPath(annaPath);
    }

    @Test
    public void testRemoveBlockEdit(){
        //ARRANGE
        int startBlockInt = 7;
        int endBlockInt = 9;
        Edit testEdit = new RemoveBlockEdit(startBlockInt, endBlockInt, annaPath);

        //ACT
        sourceFromAnnaPath.apply(testEdit);

        //ASSERT
        assertEquals(41, sourceFromAnnaPath.getAnnaClasses().get(0).getLines().size());
    }

    @Test
    public void testMoveBlockEdit(){
        //ARRANGE
        int startBlockInt = 2;
        int endBlockInt = 7;
        int newLocation = 9;
        Edit testEdit = new MoveBlockEdit(startBlockInt, endBlockInt, newLocation, annaPath);

        List<String> lines = sourceFromAnnaPath.getAnnaClasses().get(0).getLines();
        String sourceLine = lines.get(startBlockInt);
        //ACT
        sourceFromAnnaPath.apply(testEdit);

        //ASSERT
        assertEquals(43, sourceFromAnnaPath.getAnnaClasses().get(0).getLines().size());
        assertEquals(newLocation, lines.indexOf(sourceLine));
    }

    @Test
    public void testSwapBlockEdit(){
        //ARRANGE
        int blockOneStartNo = 1;
        int blockOneEndNo = 7;

        int blockTwoStartNo = 14;
        int blockTwoEndNo = 18;

        int location = 10;
        Edit testEdit = new SwapBlockEdit(blockOneStartNo, blockOneEndNo, blockTwoStartNo, blockTwoEndNo, annaPath);

        List<String> lines = sourceFromAnnaPath.getAnnaClasses().get(0).getLines();

        //ACT
        sourceFromAnnaPath.apply(testEdit);

        //ASSERT
        int blockOneLineIndex = lines.indexOf(blockOneStartNo);
        int blockTwoLineIndex = lines.indexOf(blockTwoStartNo);

        assertEquals(43, sourceFromAnnaPath.getAnnaClasses().get(0).getLines().size());
        assertEquals(blockOneStartNo, blockTwoLineIndex);
        assertEquals(blockTwoStartNo, blockOneLineIndex);
    }

}
