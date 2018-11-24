package com.anniefraz.dissertation.gin.edit;

import com.anniefraz.dissertation.gin.source.AnnaClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EditFactoryTest {


    @Mock
    private Edit mockEdit;
    private RandomEditFactory editFactory;
    @Mock
    private AnnaClass annaClass;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        editFactory = new ListBasedRandomEditFactory(Collections.singletonList(aClass -> mockEdit), () -> 0.0);

    }

    @Test
    void testGetRandomEdit() {
            Edit testEdit = editFactory.getRandomEdit(annaClass);
            assertEquals(mockEdit, testEdit);
    }

}
