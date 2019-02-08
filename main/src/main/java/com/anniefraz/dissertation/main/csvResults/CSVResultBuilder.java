package com.anniefraz.dissertation.main.csvResults;

import com.anniefraz.dissertation.gin.patch.Neighbour;
import com.anniefraz.dissertation.gin.patch.Offspring;
import com.anniefraz.dissertation.gin.patch.Patch;

import java.util.List;

public class CSVResultBuilder {

    private int iteration;
    private int populationSize;
    //Patch in Population
    private List<Patch> population;
    //Offspring
    private List<Offspring> offspring;
    private Patch offspringParent;
    //Neighbours
    private List<Neighbour> neighbour;

    public CSVResultBuilder() {
    }

    public CSVResultBuilder setIteration(int iteration) {
        this.iteration = iteration;
        return this;
    }

    public CSVResultBuilder setPopulationSize(int populationSize) {
        this.populationSize = populationSize;
        return this;
    }

    public CSVResultBuilder setPopulation(List<Patch> population) {
        this.population = population;
        return this;
    }

    public CSVResultBuilder setOffspring(List<Offspring> offspring) {
        this.offspring = offspring;
        return this;
    }

    public CSVResultBuilder setOffspringParent(Patch offspringParent) {
        this.offspringParent = offspringParent;
        return this;
    }

    public CSVResultBuilder setNeighbour(List<Neighbour> neighbour) {
        this.neighbour = neighbour;
        return this;
    }

    public CSVResult build(){
        return new CSVResult(iteration, populationSize, population, offspring, neighbour);
    }
}
