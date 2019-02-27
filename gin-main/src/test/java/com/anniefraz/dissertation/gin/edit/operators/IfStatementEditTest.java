package com.anniefraz.dissertation.gin.edit.operators;

import com.anniefraz.dissertation.gin.edit.Edit;
import com.anniefraz.dissertation.gin.source.AnnaClass;
import com.anniefraz.dissertation.gin.source.AnnaPath;
import com.anniefraz.dissertation.gin.source.Source;
import com.anniefraz.dissertation.gin.source.SourceFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.anniefraz.dissertation.gin.TestUtils.getSourceFactory;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class IfStatementEditTest {

    @Mock
    private AnnaPath annaPath;
    @Mock
    private Source source;
    @Mock
    private AnnaClass annaClass;

    private List<String> testLines;

    @BeforeEach
    void initialise(){
        MockitoAnnotations.initMocks(this);
        SourceFactory sourceFactory = getSourceFactory(this);
        testLines = new ArrayList<>(Arrays.asList("if (a > b) { ", "}", "if (c < b) {", "}"));
        when(source.getAnnaClasses()).thenReturn(Collections.singletonList(annaClass));
        when(source.getPaths()).thenReturn(Collections.singletonList(annaPath));
        when(annaClass.getPath()).thenReturn(annaPath);
    }


    @Test
    void testGetOperator() {
        IfStatementEdit edit = new IfStatementEdit(0, annaPath );
        when(annaClass.getLines()).thenReturn(testLines);

        edit.apply(source);
        String line = testLines.get(0);

        String operator = edit.getOperator(line);

        assertEquals(">", operator);
    }


    @Test
    void testChangeOperator() {
        IfStatementEdit edit = new IfStatementEdit(0, annaPath );
        when(annaClass.getLines()).thenReturn(testLines);
        edit.apply(source);
        String line = testLines.get(0);
        String operator = edit.getOperator(line);
        assertEquals(">", operator);
        String line2 = edit.changeOperator(line, operator );
        assertEquals("if (a < b) { ", line2);
    }


    @Test
    void testApplyMethod() {

        IfStatementEdit edit = new IfStatementEdit(0, annaPath );
        when(annaClass.getLines()).thenReturn(testLines);
        edit.apply(source);
        String line = testLines.get(0);
        String operator = edit.getOperator(line);
        assertEquals(">", operator);
        String line2 = edit.changeOperator(line, operator );
        assertEquals("if (a < b) { ", line2);


        assertEquals(4, testLines.size());
    }

}