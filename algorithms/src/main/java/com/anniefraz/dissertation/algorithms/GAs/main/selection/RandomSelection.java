package com.anniefraz.dissertation.algorithms.GAs.main.selection;

import com.anniefraz.dissertation.algorithms.GAs.main.GA;
import com.anniefraz.dissertation.gin.patch.Patch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

//This version of  selection is called Tournament Selection
public class RandomSelection implements SelectionMethod{

    private static final Logger LOG = LoggerFactory.getLogger(RandomSelection.class);
    public static int seed = 1000;
    private Random random = new Random(seed);

    @Override
    public List<Patch> select(List<Patch> patches) {
        List<Patch> returnPatch = new LinkedList<>();
        LOG.info("Intial length of array:" + returnPatch.size());
        Patch patch1 = patches.get(random.nextInt(patches.size()));
        Patch patch2 = patches.get(random.nextInt(patches.size()));

        if (patch1.getFitnessScore() < patch2.getFitnessScore()) {
            returnPatch.add(0, patch1);
            LOG.info("Patch 1 fitness: {}", patch1.getFitnessScore());
        } else {
            returnPatch.add(0, patch2);
            LOG.info("Patch 2 fitness:{}", patch2.getFitnessScore());
        }
        Patch patch3 = patches.get(random.nextInt(patches.size()));
        Patch patch4 = patches.get(random.nextInt(patches.size()));

        if (patch3.getFitnessScore() < patch4.getFitnessScore()) {
            returnPatch.add(1,patch3);
            LOG.info("Patch 3 fitness: {}", patch3.getFitnessScore());
        } else {
            returnPatch.add(1,patch4);
            LOG.info("Patch 4 fitness: {}",  patch4.getFitnessScore());

        }
        return returnPatch;
    }
}
