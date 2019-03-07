package com.anniefraz.dissertation.algorithms.GAs.main.crossover;

import com.anniefraz.dissertation.gin.edit.Edit;
import com.anniefraz.dissertation.gin.edit.RandomEditFactory;
import com.anniefraz.dissertation.gin.patch.Offspring;
import com.anniefraz.dissertation.gin.patch.Patch;
import com.anniefraz.dissertation.gin.patch.PatchFactory;
import com.anniefraz.dissertation.gin.source.Source;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class CrossoverOneOffspring implements CrossoverMethod {

    private static final Logger LOG = LoggerFactory.getLogger(CrossoverOneOffspring.class);


    @Override
    public List<Offspring> crossover(List<Patch> parents, Source source) {

        Patch parent1 = parents.get(0);
        Patch parent2 = parents.get(1);

        List<Offspring>offspring = new LinkedList<>();
        List<Patch>highestFitnessParents = new LinkedList<>();


        List<Edit> offspringEdits1 = new LinkedList<>(parent1.getEdits());
        offspringEdits1.add(parent2.getEdits().get(new Random().nextInt(parent2.getEdits().size())));

        List<Edit> offspringEdits2 = new LinkedList<>(parent2.getEdits());
        offspringEdits2.add(parent1.getEdits().get(new Random().nextInt(parent1.getEdits().size())));

        List<Edit> offspringEdits3 = new LinkedList<>();
        offspringEdits3.addAll(parent1.getEdits());
        offspringEdits3.addAll(parent2.getEdits());

        Offspring offspring1 = new Offspring(source, offspringEdits1);
        LOG.info("Offpring 1:Patch edit list: " + offspring1.getEdits().size());
        LOG.debug("Offpring 1:Edits list size: " + offspringEdits1.size());
        offspring1.setOrigin("All of P2, bit of P1");
        //GA.patchData(offspring1);
       // offspring.add(0, offspring1);

        Offspring offspring2 = new Offspring(source, offspringEdits2);
        LOG.info("Offpring 2:Patch edit list: " + offspring2.getEdits().size());
        LOG.debug("Offpring 2:Edits list size: " + offspringEdits2.size());
        offspring2.setOrigin("All of P1, bit of P2");
        //patchData(offspring2);
       // offspring.add(1, offspring2);

        Offspring offspring3 = new Offspring(source, offspringEdits3);
        LOG.info("Offpring 3:Patch edit list: " + offspring3.getEdits().size());
        LOG.debug("Offpring 3:Edits list size: " + offspringEdits3.size());
        offspring3.setOrigin("All of P2, All of P1");
        //patchData(offspring3);
       // offspring.add(2, offspring3);

       //Add a fourth new offsring

        if (offspring1.getFitnessScore() < offspring2.getFitnessScore() || offspring1.getFitnessScore() < offspring3.getFitnessScore()) {
        offspring.add(0, offspring1);
        } else if (offspring2.getFitnessScore() < offspring3.getFitnessScore() || offspring2.getFitnessScore() < offspring1.getFitnessScore()){
            offspring.add(0, offspring2);
        } else if (offspring3.getFitnessScore() < offspring1.getFitnessScore() || offspring3.getFitnessScore() < offspring2.getFitnessScore()){
            offspring.add(0, offspring3);
        } else {
            offspring.add(0, offspring3);
        }
        return offspring;
    }
}
