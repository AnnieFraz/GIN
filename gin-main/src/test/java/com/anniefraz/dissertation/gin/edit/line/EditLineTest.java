package com.anniefraz.dissertation.gin.edit.line;

import com.anniefraz.dissertation.gin.edit.Edit;
import com.anniefraz.dissertation.gin.source.AnnaPath;
import com.anniefraz.dissertation.gin.source.Source;
import com.anniefraz.dissertation.gin.source.SourceFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

//import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EditLineTest {

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
    public void testRemoveLineEdit(){
        //ARRANGE
        int lineNumber = 1;
        Edit testEdit = new RemoveLineEdit(lineNumber, annaPath);

        //ACT
        sourceFromAnnaPath.apply(testEdit);

        //ASSERT
        assertEquals(42, sourceFromAnnaPath.getAnnaClasses().get(0).getLines().size());
    }

    @Test
    public void testSwapLineEdit(){
        //ARRANGE
        int firstLineIndex = 2;
        int secondLineIndex = 7;
        Edit testEdit = new SwapLineEdit(firstLineIndex, secondLineIndex, annaPath);

        List<String> lines = sourceFromAnnaPath.getAnnaClasses().get(0).getLines();
        String firstLine = lines.get(firstLineIndex);
        String secondLine = lines.get(secondLineIndex);

        //ACT
        sourceFromAnnaPath.apply(testEdit);

        //ASSERT
        int newFirstLineIndex = lines.indexOf(firstLine);
        int newSecondLineIndex = lines.indexOf(secondLine);

        assertEquals(43, lines.size());
       // assertEquals(firstLineIndex, newSecondLineIndex);
        assertEquals(secondLineIndex, newFirstLineIndex);
    }

    @Test
    public void testInsertLineEdit(){
        //ARRANGE
        int lineNumber = 1;
        String lineContents = "//Triangle Class";
        Edit testEdit = new InsertLineEdit(lineNumber, lineContents, annaPath);

        //ACT
        sourceFromAnnaPath.apply(testEdit);

        //ASSERT
        List<String> lines = sourceFromAnnaPath.getAnnaClasses().get(0).getLines();
        assertEquals(44, lines.size());
        assertEquals(lineContents, lines.get(lineNumber));
    }

    @Test
    public void testMoveLineEdit(){
        //Arrange
        int sourceLineIndex = 4;
        int destinationLineIndex = 7;
        Edit edit = new MoveLineEdit(sourceLineIndex, destinationLineIndex, annaPath);

        List<String> lines = sourceFromAnnaPath.getAnnaClasses().get(0).getLines();
        String sourceLine = lines.get(sourceLineIndex);

        //Act
        sourceFromAnnaPath.apply(edit);

        //Assert
        assertEquals(destinationLineIndex, lines.indexOf(sourceLine));
    }
}