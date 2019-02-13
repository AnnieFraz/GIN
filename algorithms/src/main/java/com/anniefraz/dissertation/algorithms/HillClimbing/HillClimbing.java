package com.anniefraz.dissertation.algorithms.HillClimbing;

import com.anniefraz.dissertation.algorithms.GAs.main.GA;
import com.anniefraz.dissertation.gin.patch.Patch;
import com.anniefraz.dissertation.gin.patch.PatchFactory;
import com.anniefraz.dissertation.gin.source.Source;

import java.util.ArrayList;
import java.util.LinkedList;

public class HillClimbing {

    //private GA ga = new GA();




    public LinkedList<Patch> generateRandomSolution(PatchFactory patchFactory, Source source){
        LinkedList<Patch> randomSolution = new LinkedList<>();
      //  randomSolution = ga.generateABunchOfPatches(patchFactory, source, 10);

        return randomSolution;
    }

    public double evaluate(LinkedList<Patch> patches) throws Exception {

        double totalFitness = 0.0;

        for (int i = 0; i < patches.size(); i++) {
            Patch patch = patches.get(i);
          //  ga.patchData(patch);
            double fitness = patch.getFitnessScore();
            totalFitness = totalFitness+ fitness;
        }


        return totalFitness;
    }



}
