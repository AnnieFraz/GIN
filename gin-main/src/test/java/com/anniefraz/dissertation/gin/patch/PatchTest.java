package com.anniefraz.dissertation.gin.patch;

import com.anniefraz.dissertation.gin.edit.Edit;
import com.anniefraz.dissertation.gin.edit.line.RemoveLineEdit;
import com.anniefraz.dissertation.gin.source.AnnaClass;
import com.anniefraz.dissertation.gin.source.AnnaPath;
import com.anniefraz.dissertation.gin.source.Source;
import com.anniefraz.dissertation.gin.source.SourceFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PatchTest {

    private Source source;
    private List<Edit> edits;
    private Patch patch;
    private SourceFactory sourceFactory;

    @BeforeEach
    void setUp() {
        AnnaPath annaPath = AnnaPath.getBuilder().addPackage("additional").setClassName("Another").build();

        String path = getClass().getResource("/testClasses/TestClass.java").getPath();
        Path folder = Paths.get(path).toAbsolutePath().getParent();
        sourceFactory = new SourceFactory(folder);

        source = sourceFactory.getSourceFromAnnaPath(annaPath);
        edits = Collections.singletonList(new RemoveLineEdit(0, annaPath));
        patch = new Patch(source, edits);
    }

    @Test
    void testGetSource() {
        Source testSource = patch.getSource();
        assertEquals(source, testSource);

    }

    @Test
    void testGetEdits() {
        List<Edit> testEdits = patch.getEdits();
        assertEquals(edits, testEdits);

    }

    @Test
    void testGetOutputFile() {
        Source originalSource = patch.getSource();
        AnnaClass originalAnnaClass = originalSource.getAnnaClasses().get(0);
        assertEquals(2, originalAnnaClass.getLines().size());
        Source source = patch.getOutputSource();
        AnnaClass annaClass = source.getAnnaClasses().get(0);
        assertEquals(1, annaClass.getLines().size());
        assertEquals("line 2", annaClass.getLines().get(0));
    }
}
