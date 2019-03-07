package toy;

import java.util.Random;

public class GA2 {
    Population population = new Population();
    Individual fittest;
    Individual secondFittest;

    static int seed = 10;

    int generationCount = 0;

    public static void main(String[] args) {

        Random rn = new Random(seed);

        GA2 ga = new GA2();

        /*
        PHASE 1
        Sets the size of the population
        One character in a binary string is a gene
        A Binary string e.g. 0111010 is a Chromosone
        A collection of binary strings is a population.
         */
        ga.population.initializePopulation(1);

        /*
        PHASE 2
        Finds out how fit an individual is
         */
        ga.population.calculateFitness();

        System.out.println("Generation: " + ga.generationCount + " Fittest: " + ga.population.fittest);

        while (ga.population.fittest < 5) {
            ++ga.generationCount;

            ga.selection();

            ga.crossover();

            //rn.nextDouble()<0.7;
            if (rn.nextInt()%7 < 5) {
                ga.mutation();
            }

            ga.addFittestOffspring();

            ga.population.calculateFitness();

            System.out.println("Generation: " + ga.generationCount + " Fittest: " + ga.population.fittest);

        }

        System.out.println("\n Solution found in generation " + ga.generationCount);
        System.out.println("Fitness: "+ga.population.getFittest().fitness);
        System.out.println("Genes: ");

        for (int i = 0; i < 5; i++) {
            System.out.println(""+ga.population.getFittest().genes[i]);
        }




    }
    /*
    PHASE 3
    Purpose: to select the best individual so they pass their genes on.
    Parents are selected on fitness scores
    higher Fitness higher Chance of being chosen
     */
    void selection() {

        fittest = population.getFittest();
        secondFittest = population.getSecondFittest();
    }

    /*
    PHASE 4
    Purpose: a random point of within the parents genes
    Children are made by exchanging parents genes until crossover point is reacher
     */

    void crossover() {
        Random rn = new Random();

        int crossOverPoint = rn.nextInt(population.individuals[0].genes.length);

        for (int i = 0; i < crossOverPoint; i++) {
            int temp = fittest.genes[i];
            fittest.genes[i] = secondFittest.genes[i];
            secondFittest.genes[i] = temp;

        }

    }

    /*
    PHASE 5
    Where the bits are flipped
     */
    void mutation() {
        Random rn = new Random();

        int mutationPoint = rn.nextInt(population.individuals[0].genes.length);

        if (fittest.genes[mutationPoint] == 0) {
            fittest.genes[mutationPoint] = 1;
        } else {
            fittest.genes[mutationPoint] = 0;
        }

        mutationPoint = rn.nextInt(population.individuals[0].genes.length);

        if (secondFittest.genes[mutationPoint] == 0) {
            secondFittest.genes[mutationPoint] = 1;
        } else {
            secondFittest.genes[mutationPoint] = 0;
        }
    }


    Individual getFittestOffspring() {
        if (fittest.fitness > secondFittest.fitness) {
            return fittest;
        }
        return secondFittest;
    }


    void addFittestOffspring() {

        fittest.calcFitness();
        secondFittest.calcFitness();

        int leastFittestIndex = population.getLeastFittestIndex();

        population.individuals[leastFittestIndex] = getFittestOffspring();
    }
}

 class Individual {
    int fitness = 0;
    int[] genes = new int[5];


    public Individual() {
        Random rn = new Random();

        //Set genes randomly for each individual
        for (int i = 0; i < genes.length; i++) {
            genes[i] = Math.abs(rn.nextInt() % 2);
        }

        fitness = 0;
    }

    //Calculate fitness
    public void calcFitness() {

        fitness = 0;
        for (int i = 0; i < 5; i++) {
            if (genes[i] == 1) {
                ++fitness;
            }
        }
    }
}

 class Population {
    int popSize = 10;
    Individual[] individuals = new Individual[10];
    int fittest = 0;

    //Initialize population
    public void initializePopulation(int size) {
        for (int i = 0; i < individuals.length; i++) {
            individuals[i] = new Individual();
        }
    }

    //Get the fittest individual
    public Individual getFittest() {
        int maxFit = Integer.MIN_VALUE;
        int maxFitIndex = 0;
        for (int i = 0; i < individuals.length; i++) {
            if (maxFit <= individuals[i].fitness) {
                maxFit = individuals[i].fitness;
                maxFitIndex = i;
            }
        }
        fittest = individuals[maxFitIndex].fitness;
        return individuals[maxFitIndex];
    }

    //Get the second most fittest individual
    public Individual getSecondFittest() {
        int maxFit1 = 0;
        int maxFit2 = 0;
        for (int i = 0; i < individuals.length; i++) {
            if (individuals[i].fitness > individuals[maxFit1].fitness) {
                maxFit2 = maxFit1;
                maxFit1 = i;
            } else if (individuals[i].fitness > individuals[maxFit2].fitness) {
                maxFit2 = i;
            }
        }
        return individuals[maxFit2];
    }

    //Get index of least fittest individual
    public int getLeastFittestIndex() {
        int minFitVal = Integer.MAX_VALUE;
        int minFitIndex = 0;
        for (int i = 0; i < individuals.length; i++) {
            if (minFitVal >= individuals[i].fitness) {
                minFitVal = individuals[i].fitness;
                minFitIndex = i;
            }
        }
        return minFitIndex;
    }

    //Calculate fitness of each individual
    public void calculateFitness() {

        for (int i = 0; i < individuals.length; i++) {
            individuals[i].calcFitness();
        }
        getFittest();
    }
}
