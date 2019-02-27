package com.anniefraz.dissertation.gin.patch;

public class Neighbour extends Patch {

    private final Patch parent;

    public Neighbour(Offspring offspring) {
        super(offspring);
        parent = offspring;
    }

    public Patch getParent() {
        return parent;
    }


    @Override
    public String toString() {
        return "Neighbour{" +
                "parent=" + parent.toString() +
                '}';
    }
}
