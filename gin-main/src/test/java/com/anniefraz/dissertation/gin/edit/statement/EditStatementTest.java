package com.anniefraz.dissertation.gin.edit.statement;

import com.anniefraz.dissertation.gin.edit.Edit;
import com.anniefraz.dissertation.gin.source.AnnaPath;
import com.anniefraz.dissertation.gin.source.Source;
import com.anniefraz.dissertation.gin.source.SourceFactory;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.stmt.Statement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EditStatementTest {

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
    public void testDeleteStatement(){
        //Arrange
        int lineNumber = 0;
        //Edit testDeleteEdit = new DeleteStatementEdit(lineNumber, annaPath);
        //Act
        //sourceFromAnnaPath.apply(testDeleteEdit);
        //Assert
       // CompilationUnit compilationUnit = sourceFromAnnaPath.getAnnaClasses().get(0).getCompilationUnit();
       // assertEquals(7, compilationUnit.findAll(Statement.class).size());

    }
}
