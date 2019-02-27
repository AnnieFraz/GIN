package com.anniefraz.dissertation.main.csvResults;

import com.anniefraz.dissertation.gin.patch.Neighbour;
import com.anniefraz.dissertation.gin.patch.Offspring;
import com.anniefraz.dissertation.gin.patch.Patch;

import java.util.List;

public class CSVResult {
    private int iteration;
    private int populationSize;
    //Patch in Population
    private List<Patch> population;
    //Offspring
    private List<Offspring> offspring;
    private Patch offspringParent;
    //Neighbours
    private List<Neighbour> neighbour;

    private static CSVResultBuilder csvResultBuilder;

    public CSVResult(int iteration, int populationSize, List<Patch> population, List<Offspring> offspring, List<Neighbour> neighbour) {
        this.iteration = iteration;
        this.populationSize = populationSize;
        this.population = population;
        this.offspring = offspring;
        this.neighbour = neighbour;
    }

    public void setIteration(int iteration) {
        this.iteration = iteration;
    }

    public void setPopulationSize(int populationSize) {
        this.populationSize = populationSize;
    }

    public void setPopulation(List<Patch> population) {
        this.population = population;
    }

    public void setOffspring(List<Offspring> offspring) {
        this.offspring = offspring;
    }

    public void setNeighbour(List<Neighbour> neighbour) {
        this.neighbour = neighbour;
    }

    public void setOffspringParent(Patch offspringParent) {
        this.offspringParent = offspringParent;
    }

    public int getIteration() {
        return iteration;
    }

    public int getPopulationSize() {
        return populationSize;
    }

    public List<Patch> getPopulation() {
        return population;
    }

    public List<Offspring> getOffspring() {
        return offspring;
    }

    public Patch getOffspringParent() {
        return offspringParent;
    }

    public List<Neighbour> getNeighbour() {
        return neighbour;
    }

    public static CSVResultBuilder getCsvResultBuilder() {
        return new CSVResultBuilder();
    }
}
