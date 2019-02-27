package com.anniefraz.dissertation.gin.edit.block;

import com.anniefraz.dissertation.gin.edit.Edit;
import com.anniefraz.dissertation.gin.source.AnnaClass;
import com.anniefraz.dissertation.gin.source.AnnaPath;
import com.anniefraz.dissertation.gin.source.Source;
import com.anniefraz.dissertation.gin.source.SourceFactory;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.stmt.BlockStmt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.anniefraz.dissertation.gin.TestUtils.getSourceFactory;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

//@RunWith(MockitoJUnitRunner.class)
public class BlockTest {

    @Mock
    private AnnaPath annaPath;
    @Mock
    private Source source;
    @Mock
    private AnnaClass annaClass;

    private List<String> testLines;

    @BeforeEach
    public void initialize() {
        MockitoAnnotations.initMocks(this);
        SourceFactory sourceFactory = getSourceFactory(this);
        testLines = new ArrayList<>(Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I", "J"));
        when(source.getAnnaClasses()).thenReturn(Collections.singletonList(annaClass));
        when(source.getPaths()).thenReturn(Collections.singletonList(annaPath));
        when(annaClass.getPath()).thenReturn(annaPath);
    }

    @Test
    public void testRemoveBlockEdit(){
        //ARRANGE
        int startBlockInt = 3;
        int endBlockInt = 5;
        Edit testEdit = new RemoveBlockEdit(startBlockInt, endBlockInt, annaPath);
        when(annaClass.getLines()).thenReturn(testLines);

        List<String> expectedOutput = Arrays.asList("A", "B", "C", "G", "H", "I", "J");

        //ACT
        testEdit.apply(source);

        //ASSERT
        assertEquals(7,testLines.size());
        assertEquals(expectedOutput, testLines);
    }

    @Test
    public void testMoveBlockEdit(){
        //ARRANGE
        int startBlockInt = 2;
        int endBlockInt = 4;
        int newLocation = 7;
        Edit testEdit = new MoveBlockEdit(startBlockInt, endBlockInt, newLocation, annaPath);
        when(annaClass.getLines()).thenReturn(testLines);

        List<String> expectedOutput = Arrays.asList("A", "B", "F", "G", "C", "D", "E", "H", "I", "J");
        //ACT
        testEdit.apply(source);

        //ASSERT
        assertEquals(expectedOutput.size(),testLines.size());
        assertEquals(expectedOutput, testLines);
    }

    @Test
    public void testSwapBlockEdit(){
        //ARRANGE
        int blockOneStartNo = 2;
        int blockOneEndNo = 4;

        int blockTwoStartNo = 6;
        int blockTwoEndNo = 8;

        List<String> expectedOutput = Arrays.asList("A", "B", "G", "H", "I", "F", "C", "D", "E", "J");

        Edit testEdit = new SwapBlockEdit(blockOneStartNo, blockOneEndNo, blockTwoStartNo, blockTwoEndNo, annaPath);

        when(annaClass.getLines()).thenReturn(testLines);


        //ACT
        testEdit.apply(source);

        //ASSERT
        assertEquals(expectedOutput.size(),testLines.size());
        assertEquals(expectedOutput, testLines);
    }

}
