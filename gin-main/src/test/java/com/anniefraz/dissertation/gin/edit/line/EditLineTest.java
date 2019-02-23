package com.anniefraz.dissertation.gin.edit.line;

import com.anniefraz.dissertation.gin.edit.Edit;
import com.anniefraz.dissertation.gin.source.AnnaClass;
import com.anniefraz.dissertation.gin.source.AnnaPath;
import com.anniefraz.dissertation.gin.source.Source;
import com.anniefraz.dissertation.gin.source.SourceFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.anniefraz.dissertation.gin.TestUtils.getSourceFactory;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


public class EditLineTest {

    @Mock
    private AnnaPath annaPath;
    @Mock
    private Source source;
    @Mock
    private AnnaClass annaClass;

    private List<String> testLines;

    @BeforeEach
    public void initialize(){
        MockitoAnnotations.initMocks(this);
        SourceFactory sourceFactory = getSourceFactory(this);
        testLines = new ArrayList<>(Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I", "J"));
        when(source.getAnnaClasses()).thenReturn(Collections.singletonList(annaClass));
        when(source.getPaths()).thenReturn(Collections.singletonList(annaPath));
        when(annaClass.getPath()).thenReturn(annaPath);
        }


    @Test
    public void testRemoveLineEdit(){
        //ARRANGE
        int lineNumber = 1;
        Edit testEdit = new RemoveLineEdit(lineNumber, annaPath);
        List<String> expectedOutput = Arrays.asList("A", "C", "D", "E", "F", "G", "H", "I", "J");

        //ACT
        testEdit.apply(source);

        //ASSERT
        assertEquals(7, testLines.size());
        assertEquals(expectedOutput, testLines);
        }

    @Test
    public void testSwapLineEdit(){
        //ARRANGE
        int firstLineIndex = 2;
        int secondLineIndex = 7;
        Edit testEdit = new SwapLineEdit(firstLineIndex, secondLineIndex, annaPath);
        List<String> expectedOutput = Arrays.asList("A","B", "H", "D", "E", "F", "G",  "C", "I", "J");
        //ACT
        testEdit.apply(source);
        //ASSERT
        assertEquals(expectedOutput.size(), testLines.size());
        assertEquals(expectedOutput, testLines);
    }

    @Test
    public void testInsertLineEdit(){
        //ARRANGE
        int lineNumber = 1;
        String lineContents = "//Triangle Class";
        Edit testEdit = new InsertLineEdit(lineNumber, lineContents, annaPath);
        List<String> expectedOutput = Arrays.asList("A", "C", "D", "E", "F", "G", "H", "I", "J");

        //ACT
        testEdit.apply(source);

        //ASSERT
        assertEquals(7, testLines.size());
        assertEquals(expectedOutput, testLines);
    }

    @Test
    public void testMoveLineEdit(){
        //Arrange
        int sourceLineIndex = 4;
        int destinationLineIndex = 7;
        Edit testEdit = new MoveLineEdit(sourceLineIndex, destinationLineIndex, annaPath);
        List<String> expectedOutput = Arrays.asList("A", "C", "D", "E", "F", "G", "H", "I", "J");

        //ACT
        testEdit.apply(source);

        //ASSERT
        assertEquals(7, testLines.size());
        assertEquals(expectedOutput, testLines);
    }
}
