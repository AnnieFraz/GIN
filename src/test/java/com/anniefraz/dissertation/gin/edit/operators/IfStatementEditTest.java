package com.anniefraz.dissertation.gin.edit.operators;

import com.anniefraz.dissertation.gin.edit.Edit;
import com.anniefraz.dissertation.gin.edit.line.MoveLineEdit;
import com.anniefraz.dissertation.gin.source.AnnaPath;
import com.anniefraz.dissertation.gin.source.Source;
import com.anniefraz.dissertation.gin.source.SourceFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IfStatementEditTest {

    private AnnaPath annaPath;
    private Source sourceFromAnnaPath;

    int lineIndex = 0; // this is line: if (a > b) { in the Triangle.class
    int operatorIndex = 0;
    String operator = ""; // e.g. >
    String firstCondition = "";
    String secondCondition = "";

    Edit edit;

    @BeforeEach
    void initialise(){

        String path = getClass().getResource("/testClasses/TestClass.java").getPath();
        Path folder = Paths.get(path).toAbsolutePath().getParent();
        SourceFactory sourceFactory = new SourceFactory(folder);
        annaPath = new AnnaPath(Collections.singletonList("example"), "Triangle");
        sourceFromAnnaPath = sourceFactory.getSourceFromAnnaPath(annaPath);
        this.lineIndex = 11;
        this.operator = "";
        this.operatorIndex =6;


    }

    @Test
    void testGetOperatorIndex() {

        assertEquals(6, operatorIndex);
    }

    @Test
    void testGetOperator() {

        Edit edit = new IfStatementEdit(lineIndex, operator, firstCondition, secondCondition,annaPath );
        List<String> lines = sourceFromAnnaPath.getAnnaClasses().get(0).getLines();
        String line = lines.get(10);

        ((IfStatementEdit) edit).getOperator(line);

        assertEquals(">", operator);
    }

    @Test
    void testChangeOperator() {

        List<String> lines = sourceFromAnnaPath.getAnnaClasses().get(0).getLines();
        String line = lines.get(10);

        Edit edit = new IfStatementEdit(lineIndex, operator, firstCondition, secondCondition,annaPath );

        operator = ((IfStatementEdit) edit).getOperator(line);

        String line2 = ((IfStatementEdit) edit).changeOperator(line, operator );


        assertEquals("        if (a < b) {", line2);
    }

    @Test
    void testApplyMethod() {

        List<String> lines = sourceFromAnnaPath.getAnnaClasses().get(0).getLines();


        Edit edit = new IfStatementEdit(lineIndex, operator, firstCondition, secondCondition,annaPath );

        sourceFromAnnaPath.apply(edit);

        assertEquals(43, lines.size());
    }
}