package com.anniefraz.dissertation.gin.edit.operators;

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

import static com.anniefraz.dissertation.gin.TestUtils.getSourceFactory;
import static org.junit.jupiter.api.Assertions.*;

class IfStatementEditTest {

    private AnnaPath annaPath;
    private Source sourceFromAnnaPath;

    int lineIndex = 10; // this is line: if (a > b) { in the Triangle.class
    String secondCondition = "";

    Edit edit;

    @BeforeEach
    void initialise(){

        SourceFactory sourceFactory = getSourceFactory(this);

        annaPath = new AnnaPath(Collections.singletonList("example"), "Triangle");
        sourceFromAnnaPath = sourceFactory.getSourceFromAnnaPath(annaPath);
    }


    @Test
    void testGetOperator() {

        IfStatementEdit edit = new IfStatementEdit(lineIndex, annaPath );
        List<String> lines = sourceFromAnnaPath.getAnnaClasses().get(0).getLines();
        String line = lines.get(10);

        String operator = edit.getOperator(line);

        assertEquals(">", operator);
    }

    @Test
    void testChangeOperator() {

        List<String> lines = sourceFromAnnaPath.getAnnaClasses().get(0).getLines();
        String line = lines.get(10);

        IfStatementEdit edit = new IfStatementEdit(lineIndex, annaPath );

        String operator = edit.getOperator(line);

        String line2 = edit.changeOperator(line, operator );

        //"        if (a < b) {"

        assertEquals("        if (a < b) {", line2);
    }


    @Test
    void testChangeLine(){
        List<String> lines = sourceFromAnnaPath.getAnnaClasses().get(0).getLines();
        String line = lines.get(10);

        IfStatementEdit edit = new IfStatementEdit(lineIndex, annaPath );

        String newLine = edit.changeLine(line, "");

        assertEquals("        if (b < a) {",newLine);
    }

    @Test
    void testApplyMethod() {

        Edit edit = new IfStatementEdit(lineIndex, annaPath );

        sourceFromAnnaPath.apply(edit);

        List<String> lines = sourceFromAnnaPath.getAnnaClasses().get(0).getLines();

        System.out.println(lines);

        assertEquals(47, lines.size());
    }
}