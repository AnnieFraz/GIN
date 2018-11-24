package com.anniefraz.dissertation.gin.patch;

import com.anniefraz.dissertation.gin.edit.Edit;
import com.anniefraz.dissertation.gin.edit.RandomEditFactory;
import com.anniefraz.dissertation.gin.source.AnnaPath;
import com.anniefraz.dissertation.gin.source.Source;
import com.anniefraz.dissertation.gin.source.SourceFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

public class PatchFactoryTest {

    private PatchFactory patchFactory;
    @Mock
    private RandomEditFactory editFactory;
    @Mock
    private Edit mockEdit;
    private SourceFactory sourceFactory;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        patchFactory = new SimplePatchFactory(editFactory, () -> 0.99999999);

        String path = getClass().getResource("/testClasses/TestClass.java").getPath();
        Path folder = Paths.get(path).toAbsolutePath().getParent();
        sourceFactory = new SourceFactory(folder);

        when(editFactory.getRandomEdit(any())).thenReturn(mockEdit);
    }

    @Test
    void testGetPatchForSource() {
        Source source = sourceFactory.getSourceFromAnnaPath(AnnaPath.getBuilder().addPackage("additional").setClassName("Another").build());
        Patch patch = patchFactory.getPatchForSourceWithEdits(source, 2);
        Source patchSource = patch.getSource();
        assertEquals(source, patchSource);
        List<Edit> edits = patch.getEdits();
        assertEquals(2, edits.size());
    }
}
