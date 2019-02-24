package com.anniefraz.dissertation.algorithms.GAs.main;

import com.anniefraz.dissertation.gin.patch.Neighbour;
import com.anniefraz.dissertation.gin.patch.Offspring;
import com.anniefraz.dissertation.gin.patch.Patch;
import com.anniefraz.dissertation.gin.patch.PatchFactory;
import com.anniefraz.dissertation.gin.source.AnnaClass;
import com.anniefraz.dissertation.gin.source.AnnaPath;
import com.anniefraz.dissertation.gin.source.Source;
import com.anniefraz.dissertation.main.input.UserInput;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.mockito.Mockito.when;

public class GATest {

    private static int testSeed = 1234;
    private Random testRandom = new Random(testSeed);
    @Mock
    private PatchFactory patchFactory;
    @Mock
    private Source source;
    @Mock
    private AnnaPath annaPath;
    @Mock
    private AnnaClass annaClass;
    @Mock
    private UserInput userInput;

    private List<Patch> patches;

    private GA ga;

    private List<Offspring> offsprings;
    private List<Neighbour> neighbours;

    @BeforeEach
    public void initialize(){
        MockitoAnnotations.initMocks(this);
        patchFactory.getPatchForSourceWithEdits(source, 1);
        when(source.getAnnaClasses()).thenReturn(Collections.singletonList(annaClass));
        when(source.getPaths()).thenReturn(Collections.singletonList(annaPath));
        when(annaClass.getPath()).thenReturn(annaPath);

        GA ga = new GA(userInput);
    }

    @Test
    public void testRandom(){
        Assert.assertEquals(8,testRandom.nextInt(10));
    }

    @Test
    public void testInitialisePopulation(){

      patches = ga.generateABunchOfPatches(patchFactory, source, 7);
       Assert.assertEquals(7, patches.size());
    }

    @Test
    public void testCrossover(){
        List<Patch> parents = ga.selection(patches);
        System.out.println(parents.size());
        offsprings =ga.crossover(parents, source);
         Assert.assertEquals(3, offsprings.size());

    }

    @Test
    public void testMutation(){
        for (int i = 0; i < offsprings.size(); i++) {
            neighbours.add(ga.mutation(offsprings.get(i)));
        }

        Assert.assertEquals(3, neighbours.size());

    }

}
